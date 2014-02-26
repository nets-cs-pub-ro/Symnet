package translator.generic

import java.io.{File, FileInputStream}
import org.antlr.v4.runtime.{CommonTokenStream, ANTLRInputStream}
import generated.{ClickParser, ClickLexer}
import org.antlr.v4.runtime.tree.{ParseTreeWalker, ParseTree}
import collection.mutable.ListBuffer
import io.Source

/**
 * Build a SYMNET runnable test case.
 */
object TestCaseBuilder {

  def testFilePrefix: String = """|module Main where
                                 |import Utils
                                 |import Flow
                                 |import CompactFlow
                                 |import ChangeHeaderspace
                                 |import Rules
                                 |import Applications
                                 |import Data.List
                                 |import NetworkAppliances
                                 |
                                 |l0 = []
                                 |
                                 |""".stripMargin


  def testFileSuffix: String = ""

  /**
   * Builds an abstract network representation out of a configuration file.
   * @param file Click configuration file.
   * @return Resulting Abstract Network.
   */
  def buildConfig(file: File): NetworkConfig = {
    val source = new FileInputStream(file)
    val input = new ANTLRInputStream(source)
    val lexer: ClickLexer = new ClickLexer(input)
    val tokens: CommonTokenStream = new CommonTokenStream(lexer)
    val parser: ClickParser = new ClickParser(tokens)

    val tree: ParseTree = parser.configFile

    val walker = new ParseTreeWalker
    val newConfig = new NetworkConfigBuilder(file.getName)
    walker.walk(newConfig, tree)
    newConfig.buildNetworkConfig()
  }

  /**
   * List of config files.
   * @param files
   * @return List of Abstract Networks.
   */
  def buildConfigs(files: List[File]): List[NetworkConfig] = {
    for (file <- files)
      yield buildConfig(file)
  }

  /**
   * Builds the global abstract network infrastructure out of all the individual network files.
   * @param configs Abstract Network configurations.
   * @return
   */
  def unifyConfigs(configs: List[NetworkConfig]): NetworkConfig = {
    var allElements: Map[String, GenericElement] = Map()
    var allPaths: List[List[PathComponent]] = List()

    for (network <- configs) {
      allElements = allElements ++ network.elements
      allPaths = allPaths ++ network.paths
    }

    NetworkConfig(allElements, allPaths)
  }

  def addStaticInterConfigLinks(globalConfig: NetworkConfig, staticConnects: List[String]): NetworkConfig = {
    var paths = globalConfig.paths
    val elms = globalConfig.elements

    for {
      connect <- staticConnects
      pair = connect.split(" ")
      src = pair(0) if elms.contains(src)
      dst = pair(1) if elms.contains(dst)
    } {
      paths = List((src, 0 , 0), (dst, 0,0)) :: paths
    }

    NetworkConfig(globalConfig.elements, paths)
  }

  def buildGlobalConfig(provider: List[File], client: List[File], staticConnects: List[String],
                        dynamicConnects: List[String]): NetworkConfig = {

    val providerConfigWithoutStatics = unifyConfigs(buildConfigs(provider))
    val providerConfigWithStatics = addStaticInterConfigLinks(providerConfigWithoutStatics, staticConnects)

    val clientConfig = unifyConfigs(buildConfigs(client))

    unifyConfigs(List(providerConfigWithStatics, clientConfig))
  }

  def generateCases() = None
}
