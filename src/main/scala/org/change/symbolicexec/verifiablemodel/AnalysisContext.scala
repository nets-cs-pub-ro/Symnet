package org.change.symbolicexec.verifiablemodel

import java.io.{File, FileInputStream}

import generated.reachlang.{ReachLangParser, ReachLangLexer}
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.{CommonTokenStream, ANTLRInputStream}
import org.change.parser.abstractnet.ClickToAbstractNetwork
import org.change.parser.platformconnection.PlatformSetupParser
import org.change.parser.verification.TestsParser
import org.change.symbolicexec.executorhooks._
import org.change.symbolicexec.verification._
import org.change.symbolicexec.{Output, Input, PathLocation, Path}
import org.change.symbolicexec.executors.DirectedExecutor
import parser.generic.NetworkConfig

import scala.collection.mutable

sealed trait PlatformType

object Mass extends PlatformType
object Op extends PlatformType

sealed trait PlatformPlace

object Client extends PlatformPlace
object Internet extends PlatformPlace
object Middle extends PlatformPlace

class AnalysisContext(
  platforms: List[(String, PlatformType, Int, PlatformPlace)],
  links: List[(String, String)]
) {

  var clientFacingVms : List[String] = platforms.filter(_._4 match {case Client => true; case _ => false}).map(_._1)
  var internetFacingVms : List[String] = platforms.filter(_._4 match {case Internet => true; case _ => false}).map(_._1)

  val (mass, op) = platforms.partition(_._2 match {case Op => false; case _ => true})

  var massPlatforms: Map[String,Platform] = mass.map( p => (p._1, new Platform(p._1))).toMap
  var operatorExclusivePlatforms: Map[String, Platform] = op.map( p => (p._1, new Platform(p._1))).toMap
  val allPls = massPlatforms ++ operatorExclusivePlatforms

  val linkCount = (for {
    l <- links
    source = massPlatforms.get(l._1).orElse(operatorExclusivePlatforms.get(l._1))
    destination = massPlatforms.get(l._2).orElse(operatorExclusivePlatforms.get(l._2))
  } yield {
    source match {
      case Some(s) => destination match {
        case Some(d) => {
          val p = if (s.platformLeafNode.eLinks.isEmpty) 0 else s.exit.add
          s.platformLeafNode.eLinks += ((p, (0, d.entryId)))
          1
        }
        case _ => 0
      }
      case _ => 0
    }
  })

  var executor: DirectedExecutor = new DirectedExecutor()
  var allTests: List[ReachabilityTestGroup] = List()

  for {
    p <- operatorExclusivePlatforms.values
  } {
    executor.model ++= (p.spanners)
  }

  for {
    p <- massPlatforms.values
  } {
    executor.model ++= (p.spanners)
  }

  loadInitialVms()

  def tryClientConfig(config: NetworkConfig, vmId: String, tests: ReachabilityTestGroup): Boolean = {
    val cfModel = DirectedExecutor.elementsToExecutableModel(config, vmId)
    val sourceNode = cfModel.find(_._2.elementType equals "FromDevice")
    val destinationNode = cfModel.find(_._2.elementType equals "ToDevice")
    val internet = allPls(internetFacingVms.head)
    val client = allPls(clientFacingVms.head)

    val toTest = (tests :: allTests).map(g => g.map { t =>
      t ++ List(Rule(PathLocation(internet.entryId._1, internet.entryId._2, 0, Output)))
    })


    (sourceNode, destinationNode) match {
      case (Some(s), Some(d)) => {
        massPlatforms.values.find( p => {
          p.insertFromRoot(s._1._1, s._1._2)
          d._2.eLinks += ((0, (0, p.exitId)))

          val tentativeExecutor = new DirectedExecutor(executor.model ++ cfModel)

          val path0 = Path.cleanWithCanonical(PathLocation(client.entryId._1, client.entryId._2, 0, Input), toTest)

          val exploredPaths = tentativeExecutor.execute(noopHook)(List(path0))
          println(exploredPaths)

          println("\nResult digest\n")

          var count = 0

          for {
            p <- exploredPaths
            if p.valid
            if p.tests.forall(_.isEmpty)
          } {
            count += 1
            println(s"\nReachability test groups ware successfully verified by path:\n $p")
          }

          if (count > 0)
            println(s"\n\nA total of $count path${if (count > 1) "s" else ""} satisf${if (count > 1) "y" else "ies"} the verified properties.")
          else
            println("No possible path satisfies the imposed properties.")

          count > 0
        }) match {
          case Some(p) => {
            println(s"VM $vmId can be installed at platfrom ${p.id}")
            true
          }
          case None => false
        }
      }
      case _ => false
    }
  }

  private def loadInitialVms(): Unit = {
    startOpVms((for {
      l <- scala.io.Source.fromFile("initialVms").getLines()
      splited = l.split("\\W+")
      p = splited(0).trim
      vm = splited(1).trim
      file = splited(2).trim
    } yield (p, vm, file)).toList)
  }

  private def startOpVms(vms: List[(String, String, String)]) = {
    val all = massPlatforms ++ operatorExclusivePlatforms
    for {
      (p, vmid, vm) <- vms.map(pair => (pair._1, pair._2, "opVms/"+pair._3))
    } {
      insertVmToModel(p, vm, vmid, all, executor) match {
        case Some(newExec) => {
          executor = newExec
        }
        case None =>
      }
    }
  }

  private def insertVmToModel(platform: String, vmFile: String, vmId: String, availablePlatforms: Map[String, Platform], baseExecutor: DirectedExecutor): Option[DirectedExecutor] = {
    val p = availablePlatforms(platform)
    val networkConfig = ClickToAbstractNetwork.buildConfig(new File(vmFile))
    insertVmToModel(p, networkConfig, vmId, baseExecutor)
  }

  private def insertVmToModel(platform: Platform,
                              vm: NetworkConfig,
                              vmId: String,
                              baseExecutor: DirectedExecutor): Option[DirectedExecutor] = {
    val cfModel = DirectedExecutor.elementsToExecutableModel(vm, vmId)
    val sourceNode = cfModel.find(_._2.elementType equals "FromDevice")
    val destinationNode = cfModel.find(_._2.elementType equals "ToDevice")

    (sourceNode, destinationNode) match {
      case (Some(s), Some(d)) => {
        platform.insertFromRoot(s._1._1, s._1._2)
        d._2.eLinks += ((0, (0, platform.exitId)))

        Some(new DirectedExecutor(baseExecutor.model ++ cfModel))
      }
      case _ => None
    }
  }
}
