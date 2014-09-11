package org.change.utils

import java.io.FileInputStream
import generated.reachlang.{ReachLangParser, ReachLangLexer}
import org.antlr.v4.runtime.tree.{ParseTreeVisitor, ParseTree, ParseTreeWalker}
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import generated.reachlang.ReachLangParser
import org.change.parser.verification.ReachabilityRuleParser

object Runner {
    def main(args: Array[String]) {

        val input = new FileInputStream(args(0))

        val parserInput = new ANTLRInputStream(input)
        val lexer: ReachLangLexer = new ReachLangLexer(parserInput)
        val tokens: CommonTokenStream = new CommonTokenStream(lexer)
        val parser: ReachLangParser = new ReachLangParser(tokens)

        val tree: ParseTree = parser.requirements

        tree.accept(new ReachabilityRuleParser)
    }
}