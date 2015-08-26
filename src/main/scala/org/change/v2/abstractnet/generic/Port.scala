package org.change.v2.abstractnet.generic

case class Port(configString: Option[String] = None, var to: Option[Port] = None) {

  def linkPortTo(other: Port) {
    to = Some(other)
  }

  def unlink {
    to = None
  }

}
