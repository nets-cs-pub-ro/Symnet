package org.netvisor.runtime.server.request

import java.io.ByteArrayInputStream

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/3/14
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
trait Field {
  def name: String
}

case class StringField(name: String, value: String) extends Field

case class FileField(name: String, fileName: String, contents: ByteArrayInputStream) extends Field

object UnknownField extends Field {
  override def name = throw new NoSuchFieldException
}