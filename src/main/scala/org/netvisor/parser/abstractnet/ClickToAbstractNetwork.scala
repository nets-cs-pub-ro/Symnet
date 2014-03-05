package org.netvisor.parser.abstractnet

import java.io.{FileInputStream, File}
import parser.generic.{NetworkConfigBuilder, NetworkConfig}
import org.antlr.v4.runtime.{CommonTokenStream, ANTLRInputStream}
import generated.{ClickParser, ClickLexer}
import org.antlr.v4.runtime.tree.{ParseTreeWalker, ParseTree}

/**
 * radu
 * 3/5/14
 */
object ClickToAbstractNetwork {

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

}
