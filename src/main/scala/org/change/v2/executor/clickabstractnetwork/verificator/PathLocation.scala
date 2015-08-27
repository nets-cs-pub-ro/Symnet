package org.change.v2.executor.clickabstractnetwork.verificator

case class PathLocation(vm: String, element: String, port: Int, accessPointType: AccessPointType) {
  override def toString = s"< $vm :: $element : $accessPointType [$port] >"
}

class AccessPointType
object Input extends AccessPointType {
  override def toString = "INPUT"
}
object Output extends AccessPointType {
  override def toString = "OUTPUT"
}