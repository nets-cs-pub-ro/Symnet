package org.change.runtime.server.processing.start.processors

import org.change.runtime.server.request.{StringField, FileField, Field}
import org.apache.commons.io.{FileUtils, IOUtils}
import java.io._
import org.change.parser.abstractnet.ClickToAbstractNetwork
import org.change.runtime.server.processing.ParamPipelineElement
import parser.generic.TestCaseBuilder
import scala.collection.JavaConversions._
import org.change.runtime.server.ServiceBoot
import org.change.runtime.server.request.FileField
import org.change.runtime.server.request.StringField
import scala.Some

/**
 * radu
 * 3/5/14
 */
object ParseAndCheck extends ParamPipelineElement {

  //val EndToEnd =
  val ToElemReach = "reach\\s+(\\w+)-(\\d+)(?:\\s*:\\s*(when.+))?\\s*".r
  val EndToEndReach = "reach\\s+(\\w+)-(\\d+)\\s+client(?:\\s*:\\s*(when[^>]+)?\\s*(?:->\\s*(should.+))?)?\\s*".r
  val Invariant = "inv\\s+(\\w+)-(\\d+)\\s+(\\w+)-(\\d+)\\s*:\\s*([^&]+(?:\\s*&&\\s*[^&]+)*)".r
  val EmptyLine = "(\\s+|^$)".r
  val CommentLine = "\\s*#.*".r

  def apply(v1: Map[String, Field]): Boolean = {
    v1.get("click_file") match {
      case Some(FileField(_, _, contents)) => {
        v1.get("id") match {
          case Some(FileField(_, _,identity)) => {
            val id = IOUtils.toString(identity)

            v1.get("name") match {
              case Some(StringField(_, name)) => {
                /**
                 * New request
                 */
                ServiceBoot.logger.debug(contents.available().toString)
                /**
                 * Allocate resources: new VM id within the system and file for server strage
                 */
                val newVmName = id+name
                val newVmFile = new File(ServiceBoot.vmFolder + File.separator + newVmName)
                /**
                 * Read the Click file and allocate an IP and a port for the new VM
                 */
                val contentsAsString = IOUtils.readLines(contents).mkString("\n")
                val ip = ServiceBoot.ip
                val port = ServiceBoot.newPort(newVmName)
                /**
                 * Replace DSTIP and DSTPORT macros
                 */
                val replaceTcpWildcard = contentsAsString.replace("DSTPORT", port.toString)
                val replaceIPWildcard = replaceTcpWildcard.replace("DSTIP", ip)
                /**
                 * Store new vm data.
                 */
                FileUtils.writeStringToFile(newVmFile, replaceIPWildcard)
                /**
                 * Parse to abstract
                 */
                val abstractNet = ClickToAbstractNetwork.buildConfig(new FileInputStream(newVmFile), id + name)
                ServiceBoot.logger.debug("Parsed: \n" + abstractNet)
                ServiceBoot.logger.debug("As haskell: \n" + abstractNet.asHaskellWithRuleNumber())

                var ok = true

                v1.get("require") match {
                  case Some(FileField(_, _,requirementsFile)) => {
                    val requirements = IOUtils.readLines(requirementsFile).toList
                    for (r <- requirements) {
                      r match {
                        case ToElemReach(destination, port, config) => {
                          val fullDestName = newVmName + "-" + destination
                          val haskellCode = if (config == null) {
                            TestCaseBuilder.toElemReachability(abstractNet, fullDestName, Integer.parseInt(port), None)
                          } else {
                            TestCaseBuilder.toElemReachability(abstractNet, fullDestName, Integer.parseInt(port), Some(config))
                          }

                          val testOutput = TestCaseRunner.runHaskellCode(haskellCode)
                          ServiceBoot.logger.info("Analysis output:\n" + testOutput)
                        }

                        case EndToEndReach(cross, port, cfg1, cfg2) => {
                          val fullDestName = newVmName + "-" + cross

                          val reqa = if (cfg1 == null) None else Some(cfg1)
                          val reqb = if (cfg2 == null) None else Some(cfg2)

                          val haskellCode = TestCaseBuilder.endToEndReachability(abstractNet, fullDestName, Integer.parseInt(port), reqa, reqb)
                          val testOutput = TestCaseRunner.runHaskellCode(haskellCode)
                          ServiceBoot.logger.info("Analysis output:\n" + testOutput)
                        }

                        case Invariant(a, pa, b, pb, headers) => {
                          val parsedHeaders = headers.split("&&").toList.map(_.trim)

                          val haskellCode = TestCaseBuilder.invariantCheck(abstractNet, newVmName+"-"+a, pa, newVmName+"-"+b, pb, parsedHeaders)
                          val testOutput = TestCaseRunner.runHaskellCode(haskellCode)
                          ServiceBoot.logger.info("Invariant analysis output:\n" + testOutput)
                        }

                        case CommentLine =>
                        case _ =>
                      }
                    }
                  }
                }

//                val systemId = MachineStarter.startMachine(newVmName, ip, port, newVmFile)
//                ServiceBoot.logger.info("System id:\n" + (systemId match {
//                  case Right(message) => "Started vm with id: " + message
//                  case Left(message) => "Failed due to: " + message
//                }))

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

  def startMachine(vmName: String, ip: String, port: Int, vmFile: File): Either[String, String] = {

    val root = new File(File.separator)

    //    Start the machine
    ServiceBoot.getLogger.info(vmFile.getAbsolutePath)
    var pb = new ProcessBuilder("/home/vlad/pms/clickos/start_vm.py", vmName, vmFile.getAbsolutePath)
    pb.directory(root)
    var p = pb.start()
    var status = p.waitFor()
    var processOutput = p.getInputStream
    ServiceBoot.getLogger.info(IOUtils.readLines(processOutput).toList.mkString("\n"))

    if (status != 0)
      return Left("Fail starting the VM")

    pb = new ProcessBuilder("bash", "getVmId.sh", vmName)
    p = pb.start()
    status = p.waitFor()
    p = pb.start()
    processOutput = p.getInputStream
    status = p.waitFor()

    var systemId: String = "none"

    if (status != 0)
      return Left("Fail getting the VM id")
    else
      systemId = IOUtils.readLines(processOutput)(0)

    ServiceBoot.logger.info("Started vm with system id: " + systemId + " start redirecting traffic.")

    Thread.sleep(500)

    pb = new ProcessBuilder("bash", "redirect.sh", "start", "eth1", "vif"+systemId+".0", port.toString)
    p = pb.start()
    status = p.waitFor()
    p = pb.start()
    processOutput = p.getInputStream
    status = p.waitFor()
    ServiceBoot.getLogger.info(IOUtils.readLines(processOutput).toList.mkString("\n"))

    if (status != 0)
      return Left("Fail redirecting traffic to machine.")

    pb = new ProcessBuilder("bash", "redirect_back.sh", "start", "vif"+systemId+".0", "eth1")
    p = pb.start()
    status = p.waitFor()
    p = pb.start()
    processOutput = p.getInputStream
    status = p.waitFor()
    ServiceBoot.getLogger.info(IOUtils.readLines(processOutput).toList.mkString("\n"))

    if (status != 0)
      return Left("Fail redirecting traffic back.")


    return Right(systemId)
  }

  def stopMachine(vmName: String): Either[String, String] = {
    val root = new File(File.separator)

    //    Start the machine
    var pb = new ProcessBuilder("bash", "getVmId.sh", vmName)
    var p = pb.start()
    var status = p.waitFor()
    p = pb.start()
    var processOutput = p.getInputStream
    status = p.waitFor()

    var systemId: String = "none"

    if (status != 0)
      return Left("Fail getting the VM id")
    else
      systemId = IOUtils.readLines(processOutput)(0)

    ServiceBoot.logger.info("Stopping vm with system id: " + systemId + " start redirecting traffic.")

    pb = new ProcessBuilder("bash", "redirect.sh", "stop", "eth1", "vif"+systemId+".0")
    p = pb.start()
    status = p.waitFor()
    p = pb.start()
    processOutput = p.getInputStream
    status = p.waitFor()
    ServiceBoot.getLogger.info(IOUtils.readLines(processOutput).toList.mkString("\n"))

    if (status != 0)
      return Left("Fail stopping redirection of traffic to machine.")

    pb = new ProcessBuilder("bash", "redirect.sh", "stop", "vif"+systemId+".0", "eth1")
    p = pb.start()
    status = p.waitFor()
    p = pb.start()
    processOutput = p.getInputStream
    status = p.waitFor()
    ServiceBoot.getLogger.info(IOUtils.readLines(processOutput).toList.mkString("\n"))

    if (status != 0)
      return Left("Fail stoppting redirection from machine back.")

    pb = new ProcessBuilder("bash", "stop.sh", vmName)
    p = pb.start()
    status = p.waitFor()
    p = pb.start()
    status = p.waitFor()

    if (status != 0)
      return Left("Fail stopping the VM.")

    return Right(systemId)
  }

}
