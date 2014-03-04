package org.symnet.runtime.server.processing

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
      case Right(_) => if (e(data)) Right("Ok")
                       else Left("Error running:" + e.getClass.getName)
      case cause@Left(_) => cause
    }
  }
}
