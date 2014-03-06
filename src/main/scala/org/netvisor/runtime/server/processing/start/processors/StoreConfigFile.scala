package org.netvisor.runtime.server.processing.start.processors

import org.netvisor.runtime.server.processing.ParamPipelineElement
import org.netvisor.runtime.server.request.{StringField, FileField, Field}
import org.apache.commons.io.IOUtils
import java.io.FileOutputStream
import java.io.File

/**
 * radu
 * 3/5/14
 */
object StoreConfigFile extends ParamPipelineElement {
  val storeLocation = "tmpClickConfigs"

  def apply(v1: Map[String, Field]): Boolean = {
    v1.get("click_file") match {
      case Some(FileField(_, _, contents)) => {
        v1.get("id") match {
          case Some(FileField(_, _,identity)) => {
            val id = IOUtils.toString(identity)

            v1.get("vmName") match {
              case Some(StringField(_, name)) => {
                IOUtils.copy(contents, new FileOutputStream(new File(storeLocation + File.separator + id + name + ".click")))
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
