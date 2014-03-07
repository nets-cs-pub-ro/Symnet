package org.netvisor.runtime.server

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.event.{LoggingAdapter, Logging}
import scala.collection.mutable.Map

object ServiceBoot{

  var logger:LoggingAdapter = _

  def getLogger = logger

  lazy val portToId: Map[Int, String] = Map()
  lazy val vms: Map[String, Int] = Map()

  var nextPort = 30000

  def ip = "141.85.241.247"

  def vmFolder = "vms"

  def newPort(vm: String): Int = {
    while(portToId.contains(nextPort)) {
      nextPort += 1
      if (nextPort > 35000)
        nextPort = 30000
    }

    portToId += ((nextPort, vm))
    vms += ((vm, nextPort))

    nextPort += 1

    nextPort - 1
  }

  def main(args: Array[String]) {
    // we need an ActorSystem to host our application in
    implicit val system = ActorSystem("on-spray-can")


    // create and start our service actor
    val service = system.actorOf(Props[ServerServiceActor], "server-service")

    logger = Logging.getLogger(system, this)

    def getLogger = logger
    // start a new HTTP server on port 8080 with our service actor as the handler
    IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
  }
}