package org.change.utils

import java.io.{File, FileInputStream}
import generated.reachlang.{ReachLangParser, ReachLangLexer}
import org.antlr.v4.runtime.tree.{ParseTreeVisitor, ParseTree, ParseTreeWalker}
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import generated.reachlang.ReachLangParser
import org.change.parser.abstractnet.ClickToAbstractNetwork
import org.change.parser.platformconnection.PlatformSetupParser
import org.change.parser.verification.{TestsParser}
import org.change.symbolicexec.executors.DirectedExecutor
import org.change.symbolicexec.verifiablemodel.{Platform, AnalysisContext, NetworkNode}
import org.change.symbolicexec.{Input, PathLocation, Path}
import org.change.symbolicexec.executorhooks._
import org.change.symbolicexec.executors._

object Runner {
    def main(args: Array[String]) {

      if (args.length < 2) {
        println("Usage: <tests file> <click file>")
      }

      val input = new FileInputStream(args(0))

      val parserInput = new ANTLRInputStream(input)
      val lexer: ReachLangLexer = new ReachLangLexer(parserInput)
      val tokens: CommonTokenStream = new CommonTokenStream(lexer)
      val parser: ReachLangParser = new ReachLangParser(tokens)

      val tree: ParseTree = parser.requirements

      val tests = tree.accept(TestsParser)

      val inputFile = new File(args(1))

      val ct = new AnalysisContext(PlatformSetupParser.platfroms(args(2)), PlatformSetupParser.platfromConnections(args(3)))

      val networkAbstract = ClickToAbstractNetwork.buildConfig(inputFile)

      ct.tryClientConfig(networkAbstract, inputFile.getName, tests)

    }
}