package org.change.parser.abstractnet

import java.io.{FileInputStream, File, InputStream}
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
   * Builds an abstract network representation out of a Click.
   * @param input Click
   * @return Resulting Abstract Network.
   */
  def buildConfig(input: InputStream, configId: String): NetworkConfig = {
    val parserInput = new ANTLRInputStream(input)
    val lexer: ClickLexer = new ClickLexer(parserInput)
    val tokens: CommonTokenStream = new CommonTokenStream(lexer)
    val parser: ClickParser = new ClickParser(tokens)

    val tree: ParseTree = parser.configFile

    val walker = new ParseTreeWalker
    val newConfig = new NetworkConfigBuilder(configId)
    walker.walk(newConfig, tree)
    newConfig.buildNetworkConfig()
  }

  def buildConfig(fileInput: File): NetworkConfig = buildConfig(new FileInputStream(fileInput), fileInput.getName)

}
