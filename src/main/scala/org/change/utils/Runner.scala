package org.change.utils

import java.io.FileInputStream
import generated.reach.{ReachLangParser, ReachLangLexer, ReachLangBaseListener}
import org.antlr.v4.runtime.tree.{ParseTree, ParseTreeWalker}
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import generated.reach.ReachLangParser

object Runner {
    def main(args: Array[String]) {

        val input = new FileInputStream(args(0))

        val parserInput = new ANTLRInputStream(input)
        val lexer: ReachLangLexer = new ReachLangLexer(parserInput)
        val tokens: CommonTokenStream = new CommonTokenStream(lexer)
        val parser: ReachLangParser = new ReachLangParser(tokens)

        val tree: ParseTree = parser.requirements

        val walker = new ParseTreeWalker
        walker.walk(new ReachLangBaseListener , tree)
    }
}