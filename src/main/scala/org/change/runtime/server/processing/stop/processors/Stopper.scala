package org.change.runtime.server.processing.stop.processors

import org.change.runtime.server.processing.ParamPipelineElement
import org.change.runtime.server.ServiceBoot
import org.change.runtime.server.request.{StringField, FileField, Field}
import org.apache.commons.io.IOUtils
import java.io.File
import org.change.runtime.server.processing.start.processors.MachineStarter

/**
 * radu
 * 3/10/14
 */
object Stopper extends ParamPipelineElement {

  def apply(v1: Map[String, Field]): Boolean = {
        v1.get("id") match {
          case Some(FileField(_, _,identity)) => {
            val id = IOUtils.toString(identity)

            v1.get("name") match {
              case Some(StringField(_, name)) => {

                val oldVmName = id+name

                if (ServiceBoot.vms.contains(oldVmName)) {
                  val systemId = MachineStarter.stopMachine(oldVmName)
                  ServiceBoot.logger.info("VM stopping status\n" + (systemId match {
                    case Right(message) => "Stopped the machine with id: " + message
                    case Left(message) => {
                      "Failed to stop the VM due to: " + message
                    }
                  }))
                  true
                } else {
                  false
                }
              }
            }
          }
        }
      }
}
