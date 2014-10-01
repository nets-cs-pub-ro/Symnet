package org.change.symbolicexec.verifiablemodel

import org.change.symbolicexec.executorhooks._
import org.change.symbolicexec.verification._
import org.change.symbolicexec.{Input, PathLocation, Path}
import org.change.symbolicexec.executors.DirectedExecutor
import parser.generic.NetworkConfig

import scala.collection.mutable

sealed trait PlatformType

object Mass extends PlatformType
object Op extends PlatformType

class AnalysisContext(
  platforms: List[(String, PlatformType)],
  links: List[(String, String)]
) {

  val (mass, op) = platforms.partition(_._2 match {case Op => false; case _ => true})

  var massPlatforms: Map[String,Platform] = mass.map( p => (p._1, new Platform(p._1))).toMap
  var operatorExclusivePlatforms: Map[String, Platform] = op.map( p => (p._1, new Platform(p._1))).toMap

  val linkCount = (for {
    l <- links
    source = massPlatforms.get(l._1).orElse(operatorExclusivePlatforms.get(l._1))
    destination = massPlatforms.get(l._2).orElse(operatorExclusivePlatforms.get(l._2))
  } yield {
    source match {
      case Some(s) => destination match {
        case Some(d) => {
          val p = if (s.exit.activeOutputPorts.size == 1) 0 else s.exit.add
          s.platformLeafNode.eLinks += ((p, (0, d.entryId)))
          1
        }
        case _ => 0
      }
      case _ => 0
    }
  })

  println(linkCount)

  val executor: DirectedExecutor = new DirectedExecutor()
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

  def tryClientConfig(config: NetworkConfig, vmId: String, tests: ReachabilityTestGroup): Boolean = {
    val cfModel = DirectedExecutor.elementsToExecutableModel(config, vmId)
    val sourceNode = cfModel.find(_._2.elementType equals "FromDevice")
    val destinationNode = cfModel.find(_._2.elementType equals "ToDevice")

    (sourceNode, destinationNode) match {
      case (Some(s), Some(d)) => {
        massPlatforms.values.find( p => {
          p.insertFromRoot(s._1._1, s._1._2)
          d._2.eLinks += ((0, (0, p.exitId)))

          val tentativeExecutor = new DirectedExecutor(executor.model ++ cfModel)

          val path0 = Path.cleanWithCanonical(PathLocation(Platform.vmName, p.entryId._2, 0, Input), tests :: allTests)

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
}
