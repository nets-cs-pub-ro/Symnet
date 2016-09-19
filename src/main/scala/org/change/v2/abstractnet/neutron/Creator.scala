package org.change.v2.abstractnet.neutron

import scala.collection.JavaConversions._

import org.openstack4j.api.OSClient
import org.openstack4j.model.network.Router
import org.openstack4j.openstack.networking.domain.NeutronNetwork
import org.openstack4j.openstack.networking.domain.NeutronRouter
import org.openstack4j.openstack.networking.domain.NeutronSubnet


class Creator(osClient : OSClient) {
  
  def routerService = osClient.networking.router
  def portService = osClient.networking.port
  def subnetService = osClient.networking().subnet()
  def networkService = osClient.networking().network()
  def firewallService  = osClient.networking().firewalls()
  
  def netBuilder = NeutronNetwork.builder
  def subnetBuilder = NeutronSubnet.builder
  def routerBuilder = NeutronRouter.builder
  
  def routers = osClient.networking().router().list
  def ports   = osClient.networking().port().list
  def subnets = osClient.networking().subnet().list()
  def nets = osClient.networking.network.list
  def firewalls = osClient.networking().firewalls().firewall().list()
  def policies = osClient.networking.firewalls.firewallpolicy().list
  def rules = osClient.networking().firewalls().firewallrule()

  def routerByName(name : String) = {
    val routers = osClient.networking().router().list().filter { x => x.getName == name }
    if (routers.length == 0)
      None.asInstanceOf[Router]
    else
      routers.get(0)
  }
  
  def routerById(id : String) = {
    osClient.networking().router().get(id)
  }
  
  def getOsClient = osClient
  
  def subnet(name : String) = {
    subnets.filter { x => x.getName == name }
  }
  
  def network (name : String) = {
    nets.filter { x => x.getName == name }.get(0)
  }
  
}


object Creator {
  def apply(host : String, userName :String, password : String, project : String) : Creator = {
    new Creator(org.openstack4j.openstack.OSFactory.builder()
                .endpoint(host)
                .credentials(userName, password)
                .tenantName(project)
                .authenticate())
  }
}