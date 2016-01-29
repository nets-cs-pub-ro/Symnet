package org.change.v2.executor.clickabstractnetwork

import java.io.{PrintStream, FileOutputStream, FilenameFilter, File}

import org.change.parser.clickfile.ClickToAbstractNetwork
import org.change.parser.interclicklinks.InterClickLinksParser
import org.change.parser.startpoints.StartPointParser
import org.change.symbolicexec.verification.RuleSetBuilder
import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.abstractnet.optimized.macswitch.OptimizedSwitch
import org.change.v2.abstractnet.optimized.router.OptimizedRouter
import org.change.v2.executor.clickabstractnetwork.executionlogging.JsonLogger

/**
 * A small gift from radu to symnetic.
 */
object AggregatedBuilder {

  type Link = (String, String, String, String, String, String)
  type Port = (String, String, String)
  type ConfigParser = File => NetworkConfig

  def parsers: Map[String, ConfigParser] = Map(
    "sw" -> OptimizedSwitch.optimizedSwitchNetworkConfig _,
    "click" -> {f => ClickToAbstractNetwork.buildConfig(f, prefixedElements = true)},
    "rt" -> OptimizedRouter.optimizedRouterNetworkConfig _
  )

  def buildModelsFromFolder(folder: File): Iterable[NetworkConfig] = {
    folder.list(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean =
        name.endsWith(".click") ||
        name.endsWith(".sw") ||
        name.endsWith(".rt")
    }).sorted.map(folder.getPath + File.separatorChar + _).map({ path =>
      val f = new File(path)
      val parser = parsers(path.split("\\.").last)
      parser(f)
    })
  }

  def buildLinksFromFolder(folder: File): Iterable[(String, String, String, String, String, String)] = {
    folder.list(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = name.endsWith(".links")
    }).sorted.map(folder.getPath + File.separatorChar + _).map({ lf =>
      InterClickLinksParser.parseLinks(lf)
    }).toIterable.flatten
  }

  def buildStartPointsFromFolder(folder: File): Iterable[Port] = {
    folder.list(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = name.endsWith(".start")
    }).sorted.map(folder.getPath + File.separatorChar + _).map({ ls =>
      StartPointParser.parseStarts(ls)
    }).toIterable.flatten
  }

  def parseInput(rootFolder: File): (Iterable[NetworkConfig], Iterable[Link], Iterable[Port]) = {
    val models = buildModelsFromFolder(rootFolder)
    val links = buildLinksFromFolder(rootFolder)
    val starts = buildStartPointsFromFolder(rootFolder)
    (models, links, starts)
  }

  def executorFromFolder(rootFolder: File): ClickExecutionContext = {
    val (models, links, starts) = parseInput(rootFolder)

    ClickExecutionContext.buildAggregated(
      models,
      links,
      verificationConditions = Nil,
      startElems = Some(starts)
    )
  }

  def main(args: Array[String]): Unit = {
    val exe = executorFromFolder(new File("src/main/resources/new_facultatea")).setLogger(JsonLogger)

    val start = System.currentTimeMillis()
    exe.untilDoneFrugally(true)
    println(System.currentTimeMillis() - start)
  }
}
