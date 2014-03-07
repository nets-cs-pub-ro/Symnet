package org.netvisor.runtime.server.processing.start.processors

import org.netvisor.runtime.server.request.{StringField, FileField, Field}
import org.apache.commons.io.{FileUtils, IOUtils}
import java.io._
import org.netvisor.parser.abstractnet.ClickToAbstractNetwork
import org.netvisor.runtime.server.processing.ParamPipelineElement
import parser.generic.TestCaseBuilder
import scala.collection.JavaConversions._
import org.netvisor.runtime.server.ServiceBoot
import org.netvisor.runtime.server.request.FileField
import org.netvisor.runtime.server.request.StringField
import scala.Some

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

                val newVmName = id+name
                val newVmFile = new File(ServiceBoot.vmFolder + File.separator + newVmName)

                val contentsAsString = IOUtils.readLines(contents).mkString("\n")
                val ip = ServiceBoot.ip
                val port = ServiceBoot.newPort(newVmName)

                val replaceTcpWildcard = contentsAsString.replace("DSTPORT", port.toString)
                val replaceIPWildcard = replaceTcpWildcard.replace("DSTIP", ip)

                FileUtils.writeStringToFile(newVmFile, replaceIPWildcard)

                val abstractNet = ClickToAbstractNetwork.buildConfig(new FileInputStream(newVmFile), id + name)

                ServiceBoot.logger.info("Parsed: \n" + abstractNet)

                ServiceBoot.logger.info("As haskell: \n" + abstractNet.asHaskellWithRuleNumber())

                val haskellCode = TestCaseBuilder.generateHaskellTestSourceToDest(abstractNet, id, name)
                ServiceBoot.logger.info("End to end test case: \n\n", haskellCode)

                val testOutput = TestCaseRunner.runHaskellCode(haskellCode)
                ServiceBoot.logger.info("Test output:\n" + testOutput)

                MachineStarter.startMachine(newVmName, ip, port, newVmFile)

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

object MachineStarter {

  def startMachine(vmName: String, ip: String, port: Int, vmFile: File): String = {

    val root = new File(File.separator)

    //    Start the machine
    var pb = new ProcessBuilder("bash", "getVmId.sh", vmName)
    pb.directory(root)
    var p = pb.start()
    var status = p.waitFor()

    if (status != 0)
      return "Fail starting the VM"

    pb = new ProcessBuilder("xl list | grep "+ vmName +" | tr -s ' ' |cut -d ' ' -f 2")
    p = pb.start()
    var processOutput = p.getInputStream
    status = p.waitFor()

    var systemId: String = "none"

    if (status != 0)
      return "Fail starting the VM"
    else
      systemId = IOUtils.readLines(processOutput)(0)

    ServiceBoot.logger.info("Started vm with system id: " + systemId)

    return systemId
  }

}
