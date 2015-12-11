package org.change.v2.analysis.memory.jsonformatters

import org.change.v2.executor.clickabstractnetwork.ClickExecutionContext
import spray.json._
import DefaultJsonProtocol._
import org.change.v2.analysis.memory.jsonformatters.StateToJson._

/**
 * A small gift from radu to symnetic.
 */
object ExecutionContextToJson extends DefaultJsonProtocol {
  implicit object ExecutoFormat extends RootJsonFormat[ClickExecutionContext] {
    override def read(json: JsValue): ClickExecutionContext = deserializationError("One way protocol")

    override def write(obj: ClickExecutionContext): JsValue = JsObject(
      "total_count" -> JsNumber(obj.stuckStates.length + obj.failedStates.length + obj.okStates.length),
      "explorable" -> JsObject(
        "count" -> JsNumber(obj.okStates.length),
        "states" -> JsArray(obj.okStates.map(_.toJson))
      ),
      "finalized" -> JsObject(
        "finalized_count" -> JsNumber(obj.stuckStates.length + obj.failedStates.length),
        "ok" -> JsObject(
          "count" -> JsNumber(obj.stuckStates.length),
          "states" -> JsArray(obj.stuckStates.map(_.toJson))
        ),
        "fail" -> JsObject(
          "count" -> JsNumber(obj.failedStates.length),
          "states" -> JsArray(obj.failedStates.map(_.toJson))
        )
      )
    )
  }
}
