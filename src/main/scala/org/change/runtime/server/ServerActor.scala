package org.change.runtime.server

import akka.actor.{ Props, Actor }
import spray.http._
import spray.http.MediaTypes._
import spray.routing._
import spray.http.BodyPart
import java.io.{ ByteArrayInputStream, InputStream, OutputStream }
import spray.routing.HttpService
import org.change.runtime.server.request.{Field, UnknownField, FileField, StringField}
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets
import org.change.runtime.server.processing.general.pipelines.JustAuthorize
import org.change.runtime.server.processing.start.pipeline.StartVMPipeline
import akka.event.Logging

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class ServerServiceActor extends Actor with ServerService  {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(demoRoute)
}

// this trait defines our service behavior independently from the service actor
trait ServerService extends HttpService {

  // we use the enclosing ActorContext's or ActorSystem's dispatcher for our Futures and Scheduler
  implicit def executionContext = actorRefFactory.dispatcher

  val demoRoute: Route = {
    get {
      path("") {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete(index)
        }
      }
    } ~
      path("start") {
        post {
          respondWithMediaType(`application/json`) {
            entity(as[MultipartFormData]) { formData =>
                complete {

                  val fields = fieldMap(formData, Nil)

                  StartVMPipeline.pipeline(fields) match {
                    case Left(e) => {
                        ServiceBoot.logger.warning("Failed starting the machine, cause: " + e)
                        "VM could not be started."
                    }
                    case Right(e) => {
                        ServiceBoot.logger.info("Succes")
                        "Success, your machine can be found at:" + ServiceBoot.ip+":"+(ServiceBoot.nextPort-1)
                      }
                  }
                }
            }
          }
        }
      } ~
      path("stop") {
        post {
          respondWithMediaType(`application/json`) {
            entity(as[MultipartFormData]) { formData =>
              complete {
                val fields = fieldMap(formData, Nil)

                println(JustAuthorize(fields))

                s"""{"status": "Processed POST request, details=$fields" }"""
              }
            }
          }
        }
      }
  }

  lazy val index =
    <html>
      <body>
        <h1>Spray file upload example.</h1>
      </body>
    </html>


  private def saveAttachment(fileName: String, content: Array[Byte]): Boolean = {
    saveAttachment[Array[Byte]](fileName, content, {(is, os) => os.write(is)})
    true
  }

  private def saveAttachment(fileName: String, content: InputStream): Boolean = {
    saveAttachment[InputStream](fileName, content,
    { (is, os) =>
      val buffer = new Array[Byte](16384)
      Iterator
        .continually (is.read(buffer))
        .takeWhile (-1 !=)
        .foreach (read=>os.write(buffer,0,read))
    }
    )
  }

  private def saveAttachment[T](fileName: String, content: T, writeFile: (T, OutputStream) => Unit): Boolean = {
    try {
      val fos = new java.io.FileOutputStream(fileName)
      writeFile(content, fos)
      fos.close()
      true
    } catch {
      case _ => false
    }
  }

  private def extractRequestFields(formData: MultipartFormData): Seq[Field] = {
    formData.fields.map {
      case BodyPart(entity, headers) =>

        //Determine which field is a file
        val contentType = headers.find(h => h.is("content-type")) match {
          case Some(httpHeader) => Some(httpHeader.value)
          case None => None
        }

        val name = headers.find(h => h.is("content-disposition")).get.value.split(" ").find(_.startsWith("name=")).
          getOrElse("name=unk").split("=").last.split(";").head

        contentType match {
          //                          File received
          case Some(_) => {
            val contents = new ByteArrayInputStream(entity.data.toByteArray)

            val fileName = headers.find(h => h.is("content-disposition")).get.value.split("filename=").last
            FileField(name, fileName, contents)
          }
          //                          String field received, it must be the vm name
          case None => StringField(name, entity.asString)
        }

      case _ => UnknownField
    }
  }

  private def addFieldNames(fields: Seq[Field], names: List[String]): Seq[Field] = {
    val (named, unnamed) = fields.partition( _.name != "unk")
    val renamed = unnamed.zip(names).map{ fn => {
        val (f, n) = fn
        f match {
          case StringField(_, value) => StringField(n, value)
          case _ => f
        }
      }
    }

    named ++ renamed
  }

  private def extractWithNames(formData: MultipartFormData, names: List[String]): Seq[Field]  =
    addFieldNames(extractRequestFields(formData), names)

  private def fieldMap(formData: MultipartFormData, names: List[String]): Map[String, Field] =
    extractWithNames(formData, names).map( f => (f.name, f)).toMap
}
