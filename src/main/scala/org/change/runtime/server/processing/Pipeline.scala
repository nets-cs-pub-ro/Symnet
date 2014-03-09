package org.change.runtime.server.processing

import org.change.runtime.server.request.Field

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 12:52 PM
 */
class Pipeline[A](elements: List[PipelineElement[A]]) extends Function1[A, Either[String, String]] {

  /**
   * While the previous pipeline element returned true, apply the next one.
   * If false was detected, stop running any forthcoming logic
   * @param data
   * @return
   */
  def apply(data: A): Either[String, String] = elements.foldLeft[Either[String, String]](Right("Pipeline empty")) { (acc, e) =>
    acc match {
      case Right(_) => try {
        if (e(data)) Right("Ok")
        else Left("Error running:" + e.getClass.getName)
      } catch {
        case e => Left("Exception: " + e)
      }
      case cause@Left(_) => cause
    }
  }
}

class ParamProcessingPipeline(elements: List[PipelineElement[Map[String,Field]]])
  extends Pipeline[Map[String,Field]](elements)
