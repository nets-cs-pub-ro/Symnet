package org.change.v2.analysis.memory.jsonformatters

import org.change.v2.analysis.memory.State
import spray.json._
import DefaultJsonProtocol._
import org.change.v2.analysis.memory.jsonformatters.MemorySpaceToJson._

/**
 * A small gift from radu to symnetic.
 */
object StateToJson extends DefaultJsonProtocol {
  implicit object StateFormat extends RootJsonFormat[State] {
    override def read(json: JsValue): State = deserializationError("One way protocol")

    override def write(obj: State): JsValue = JsObject(
      "status" -> JsString(obj.status),
      "port_trace" -> JsArray(obj.history.reverse.zipWithIndex.map(ip =>
        JsObject( ip._2.toString -> JsString(ip._1)))),
      "instruction_trace" -> JsArray(obj.instructionHistory.reverse.zipWithIndex.map(ip =>
          JsObject( ip._2.toString -> JsString(ip._1.toString)))),
      "memory" -> obj.memory.toJson
    )
  }
}
