package org.symnet.runtime

import translator.generic.{TestCaseBuilder, NetworkConfigBuilder}
import java.io.{File, FileInputStream}
import generated.{ClickParser, ClickLexer}
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import org.antlr.v4.runtime.tree.{ParseTreeWalker, ParseTree}
import io.Source
import translator.RunnerScriptGenerator
import utils.FilesystemUtils

object TranslatorRunner {

  def mainer(args: Array[String]) {

    if (args.length < 1)
      throw new IllegalArgumentException("Usage: test_case_setup_file (see docs for more info)")

//Start parsing the query file.
    val lines = Source.fromFile(new File(args(0))).getLines().toList
//Config setup
    var providerConfigDirs = List[String]()
    var clientConfigDirs = List[String]()
    var staticConnections = List[String]()
    var dynamicConnections = List[String]()

    var separators = 0

    def processInputParam(param: String) = param.matches("\\s*") match {
      case true => separators += 1
      case false if (! param.matches("-.*")) => separators match {
        case 0 => providerConfigDirs = param :: providerConfigDirs
        case 1 => clientConfigDirs = param :: clientConfigDirs
        case 2 => staticConnections = param :: staticConnections
        case 3 => dynamicConnections = param :: dynamicConnections
        case _ =>
      }
      //Ignore comments.
      case _ =>
    }

//For each line decide what config param group it belongs to
    for (line <- lines) {
      processInputParam(line)
    }

    println(staticConnections)
    println(dynamicConnections)

//The list of configs that are going to be ran
    val providerConfigFiles = FilesystemUtils.allFilesFromDirs(providerConfigDirs)
    val clientConfigFiles = FilesystemUtils.allFilesFromDirs(clientConfigDirs)

//Generate the global configuration of the network
    val global = TestCaseBuilder.buildGlobalConfig(providerConfigFiles, clientConfigFiles, staticConnections,
      dynamicConnections)

    println(global.asHaskellWithRuleNumber())
    println(RunnerScriptGenerator.buildInitScript(providerConfigFiles, clientConfigFiles, List(), List()))
  }

}
