package org.change.v2.abstractnet.neutron.elements


import org.change.v2.util.conversion.RepresentationConversion._
import scala.collection.JavaConversions._
import org.openstack4j.api.OSClient
import scala.collection.JavaConversions._
import org.openstack4j.model.network.ext.Firewall
import org.openstack4j.openstack.networking.domain.NeutronNetwork
import org.openstack4j.openstack.networking.domain.NeutronSubnet
import org.openstack4j.model.network.IPVersionType
import org.openstack4j.openstack.networking.domain.NeutronRouter
import org.openstack4j.model.network.ExternalGateway
import org.openstack4j.openstack.networking.domain.NeutronExternalGateway
import org.openstack4j.model.network.AttachInterfaceType
import org.openstack4j.model.network.Router
import java.net.InetAddress

class NetAddress(fullCidr : String) {
  def mask = {
    val sp = split
    Integer.parseInt(sp(1))
  }
  
  def addressRange = {
     ipAndMaskToInterval(split(0), split(1))
  }
  
  
  def addressRangeAsString = {
    val ar = addressRange
    val (start, end) = (NetAddress.unpack(ar._1.asInstanceOf[Int]), NetAddress.unpack(ar._2.asInstanceOf[Int]))
    var startStr = ""
    for (b <- start) {
      startStr += b.toString() + "."
    }
    startStr = startStr.substring(0, startStr.length() - 1)
    
    var endStr = ""
    for (bt <- end) {
      endStr += bt.toString() + "."
    }
    endStr = endStr.substring(0, endStr.length() - 1)
    
    (startStr, endStr)
  }
  
  override def toString = {
    addressRange.toString() + "/" + mask
  }
  
  private def split = {
    fullCidr.split("/")
  }
}

object NetAddress {
  def unpack(bytes : Int) = {
    List[Byte] (
      ((bytes >>> 24) & 0xff).asInstanceOf[Byte],
      ((bytes >>> 16) & 0xff).asInstanceOf[Byte],
      ((bytes >>>  8) & 0xff).asInstanceOf[Byte],
      ((bytes       ) & 0xff).asInstanceOf[Byte]
    )
  }
  
  
  def addressAsString(ar : (Long, Long)) = {
    val (start, end) = (unpack(ar._1.asInstanceOf[Int]), unpack(ar._2.asInstanceOf[Int]))
    var startStr = ""
    for (b <- start) {
      startStr += ((b & 0xFF).asInstanceOf[Int]).toString + "."
    }
    startStr = startStr.substring(0, startStr.length() - 1)
    
    var endStr = ""
    for (bt <- end) {
      endStr += ((bt & 0xFF).asInstanceOf[Int]).toString() + "."
    }
    endStr = endStr.substring(0, endStr.length() - 1)
    
    (startStr, endStr)
  }
  
  def apply(cidr : String) = {
    new NetAddress(cidr)
  }
}
