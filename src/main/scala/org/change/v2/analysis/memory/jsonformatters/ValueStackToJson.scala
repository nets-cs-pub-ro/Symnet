package org.change.v2.analysis.memory.jsonformatters

import org.change.v2.analysis.memory.ValueStack
import org.change.v2.analysis.memory.jsonformatters.ValueToJson._
import spray.json._

/**
 * A small gift from radu to symnetic.
 */
object ValueStackToJson extends DefaultJsonProtocol {
  implicit object ValueStackFormat extends RootJsonFormat[ValueStack] {
    override def read(json: JsValue): ValueStack = deserializationError("One way protocol")

    override def write(obj: ValueStack): JsValue = obj.value match {
      case Some(v) => v.toJson
      case _ => JsString("NoValue")
    }
  }
}
