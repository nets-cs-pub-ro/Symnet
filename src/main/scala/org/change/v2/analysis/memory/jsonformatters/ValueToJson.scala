package org.change.v2.analysis.memory.jsonformatters

import org.change.v2.analysis.memory.Value
import spray.json._
import DefaultJsonProtocol._

/**
 * A small gift from radu to symnetic.
 */
object ValueToJson extends DefaultJsonProtocol {
  implicit object ValueJsonFormat extends RootJsonFormat[Value] {
    override def read(json: JsValue): Value = deserializationError("One way protocol")

    override def write(obj: Value): JsValue = JsObject(
      "expression" -> JsString(obj.e.toString),
      "constraints" -> JsArray(obj.cts.map(c => JsString(c.toString)))
    )
  }
}