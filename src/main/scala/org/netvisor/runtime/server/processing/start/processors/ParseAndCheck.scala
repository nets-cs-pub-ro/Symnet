package org.netvisor.runtime.server.processing.start.processors

import org.netvisor.runtime.server.request.{StringField, FileField, Field}
import org.apache.commons.io.IOUtils
import java.io.{InputStreamReader, InputStream, File, FileOutputStream}
import org.netvisor.parser.abstractnet.ClickToAbstractNetwork
import org.netvisor.runtime.server.processing.ParamPipelineElement
import parser.generic.TestCaseBuilder
import scala.collection.JavaConversions._
import org.netvisor.runtime.server.ServiceBoot

/**
 * radu
 * 3/5/14
 */
object ParseAndCheck extends ParamPipelineElement {

  def apply(v1: Map[String, Field]): Boolean = {
    v1.get("click_file") match {
      case Some(FileField(_, _, contents)) => {
        v1.get("id") match {
          case Some(FileField(_, _,identity)) => {
            val id = IOUtils.toString(identity)

            v1.get("name") match {
              case Some(StringField(_, name)) => {
                ServiceBoot.logger.info(contents.available().toString)

                val abstractNet = ClickToAbstractNetwork.buildConfig(contents, id + name)

                ServiceBoot.logger.info("Parsed: \n" + abstractNet)

                ServiceBoot.logger.info("As haskell: \n" + abstractNet.asHaskellWithRuleNumber())

                val haskellCode = TestCaseBuilder.generateHaskellTestSourceToDest(abstractNet, id, name)
                ServiceBoot.logger.info("End to end test case: \n\n", haskellCode)

                val testOutput = TestCaseRunner.runHaskellCode(haskellCode)
                ServiceBoot.logger.info("Test output:\n" + testOutput)

                true
              }
              case None => false
            }

          }

          case None => false
        }
      }

      case None => false
    }
  }
}

object TestCaseRunner {

//  Where symnet sits
  val symnetDir = new File("symnet")
  val testFile = new File(symnetDir,"Main.hs")

  def runHaskellCode(code: String): String = {
//    Write test case
    val testFileStream = new FileOutputStream(testFile)
    IOUtils.write(code, testFileStream)
    testFileStream.close()
//    Build and run process
    val pb = new ProcessBuilder("bash", "testRunner.sh")
    pb.directory(symnetDir)
    val p = pb.start()
    val processOutput = p.getInputStream
    val status = p.waitFor()

    if (status == 0)
      IOUtils.readLines(processOutput).toList.mkString("\n")
    else
      "Error occurred"

  }

}
