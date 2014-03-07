package org.netvisor.runtime.server

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.event.{LoggingAdapter, Logging}

object ServiceBoot{

  var logger:LoggingAdapter = _

  def getLogger = logger

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