package org.change.symbolicexec.verification

import java.io.{File, FileInputStream}

import generated.click.{ClickParser, ClickLexer}
import generated.reachlang.{ReachLangParser, ReachLangLexer}
import org.antlr.v4.runtime.tree.{ParseTreeWalker, ParseTree}
import org.antlr.v4.runtime.{CommonTokenStream, ANTLRInputStream}
import org.change.parser.verification.TestsParser
import org.change.v2.abstractnet.generic.NetworkConfigBuilder

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object RuleSetBuilder {

  def buildRuleSetFromFile(path: String): List[List[Rule]] = {
    val input = new FileInputStream(new File(path))
    val parserInput = new ANTLRInputStream(input)
    val lexer: ReachLangLexer = new ReachLangLexer(parserInput)
    val tokens: CommonTokenStream = new CommonTokenStream(lexer)
    val parser: ReachLangParser = new ReachLangParser(tokens)

    TestsParser.visitRequirements(parser.requirements())
  }

}
