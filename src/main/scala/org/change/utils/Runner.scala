package org.change.utils

import java.io.{File, FileInputStream}
import generated.reachlang.{ReachLangParser, ReachLangLexer}
import org.antlr.v4.runtime.tree.{ParseTreeVisitor, ParseTree, ParseTreeWalker}
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import generated.reachlang.ReachLangParser
import org.change.parser.abstractnet.ClickToAbstractNetwork
import org.change.parser.verification.{TestsParser}
import org.change.symbolicexec.executors.{DirectedExecutor, SymbolicExecutor}
import org.change.symbolicexec.networkgraph.NetworkNode
import org.change.symbolicexec.{Input, PathLocation, Path}
import org.change.symbolicexec.executorhooks._

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

      val tests = List(tree.accept(TestsParser))

      val inputFile = new File(args(1))

      val networkAbstract = ClickToAbstractNetwork.buildConfig(inputFile)

      val root = NetworkNode.treeRootedAtSource(networkAbstract)

      //val executor = SymbolicExecutor(networkAbstract, inputFile.getName)
      val executor = DirectedExecutor(networkAbstract)

      val path0 = (
        Path.cleanWithCanonical(PathLocation(inputFile.getName, "source", 0, Input), tests),
        root
      )

      val exploredPaths = executor.execute(noopHook)(List(path0))
      println(exploredPaths)

      println("\nResult digest\n")

      var count = 0

      for {
        p <- exploredPaths.unzip._1
        (group, i) <- p.tests.zipWithIndex
        if group.isEmpty
      } {
        count += 1
        println(s"\nReachability test group $i was successfully verified by path:\n $p")
      }

      if (count > 0)
        println(s"\n\nA total of $count path${if (count > 1) "s" else ""} satisf${if (count > 1) "y" else "ies"} the verified properties.")
      else
        println("No possible path satisfies the imposed properties.")

    }
}