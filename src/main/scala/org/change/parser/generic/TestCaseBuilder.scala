package parser.generic

import java.io.{File, FileInputStream}
import org.antlr.v4.runtime.{CommonTokenStream, ANTLRInputStream}
import generated.{ClickParser, ClickLexer}
import org.antlr.v4.runtime.tree.{ParseTreeWalker, ParseTree}
import collection.mutable.ListBuffer
import io.Source
import parser.specific.{ToDevice, FromDevice}

/**
 * Build a SYMNET runnable test case.
 *
 */
object TestCaseBuilder {

  private val testFilePrefix = """module Main where
                         |import Utils
                         |import Flow
                         |import CompactFlow
                         |import ChangeHeaderspace
                         |import Rules
                         |import Applications
                         |import Data.List
                         |import NetworkAppliances
                         |import HighLevelShortcuts
                         |import IPFilter
                         |
                         |l0 = []
                         |
                         |""".stripMargin


  private val testFileSuffixFormat = """|
                                        |input = generalFlow
                                        |
                                        |result = reach ("%s", "in") ("%s", "out") 1 input l%d
                                        |
                                        |main = do
                                        |  putStrLn $ show result""".stripMargin

  /**
   * TODO: DEP
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
   * TODO: DEP
   * @param files
   * @return List of Abstract Networks.
   */
  def buildConfigs(files: List[File]): List[NetworkConfig] = {
    for (file <- files)
      yield buildConfig(file)
  }

  /**
   * Builds the global abstract network infrastructure out of all the individual network files.
   * TODO: DEP
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

  /**
   * TODO: DEP
   * @param globalConfig
   * @param staticConnects
   * @return
   */
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

  /**
   * TODO: DEP
   * @param provider
   * @param client
   * @param staticConnects
   * @param dynamicConnects
   * @return
   */
  def buildGlobalConfig(provider: List[File], client: List[File], staticConnects: List[String],
                        dynamicConnects: List[String]): NetworkConfig = {

    val providerConfigWithoutStatics = unifyConfigs(buildConfigs(provider))
    val providerConfigWithStatics = addStaticInterConfigLinks(providerConfigWithoutStatics, staticConnects)

    val clientConfig = unifyConfigs(buildConfigs(client))

    unifyConfigs(List(providerConfigWithStatics, clientConfig))
  }

  def generateHaskellTest(net: NetworkConfig, id: String, vmName: String, source: String, sourcePort: Int,
                           destination: String, destinationPort: Int): String = {
    val (repr, rulesCount) = net.asHaskellWithRuleNumber()

    val elementNamePrefix = id+vmName

    val sourceElement = net.elements.get(elementNamePrefix+"-"+source).get
    val sourcePortString = sourceElement.inputPortName(sourcePort)

    val destElement = net.elements.get(elementNamePrefix+"-"+destination).get
    val destPortString = destElement.outputPortName(destinationPort)

    val suffix = String.format(testFileSuffixFormat, sourcePortString, destPortString, rulesCount: Integer)

    testFilePrefix + repr + suffix
  }

  def generateHaskellTestSourceToDest(net: NetworkConfig, id: String, vmName: String): String = {
    val sourcePort = net.elements.find { pair =>
      val (_, e) = pair
      e match {
        case _ : FromDevice => true
        case _ => false
      }
    }.head._2.inputPortName()

    val destPort = net.elements.find { pair =>
      val (_, e) = pair
      e match {
        case _ : ToDevice => true
        case _ => false
      }
    }.head._2.outputPortName()

    val (repr, rulesCount) = net.asHaskellWithRuleNumber()

    val suffix = String.format(testFileSuffixFormat, sourcePort, destPort, rulesCount: Integer)

    testFilePrefix + repr + suffix
  }
}
