package org.change.v2.analysis.memory.jsonformatters

import org.change.v2.analysis.memory.MemorySpace
import spray.json._
import DefaultJsonProtocol._

/**
 * A small gift from radu to symnetic.
 */
object MemorySpaceToJson extends DefaultJsonProtocol {

  implicit object MemoryStateFormat extends RootJsonFormat[MemorySpace] {
    override def read(json: JsValue): MemorySpace = deserializationError("One way protocol")

    import org.change.v2.analysis.memory.jsonformatters.MemoryObjectToJson._
    override def write(obj: MemorySpace): JsValue = JsObject(
      "tags" -> JsArray(obj.memTags.map(tv => JsObject(tv._1 -> JsNumber(tv._2))).toList),
      "meta_symbols" -> JsArray(obj.symbols.map(tv => JsObject(tv._1 -> tv._2.toJson)).toList),
      "header_fields" -> JsArray(obj.rawObjects.map(tv => JsObject(tv._1.toString -> tv._2.toJson)).toList)
    )
  }

}
