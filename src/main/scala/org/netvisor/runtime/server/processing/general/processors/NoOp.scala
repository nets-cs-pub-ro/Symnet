package org.netvisor.runtime.server.processing.general.processors

import org.netvisor.runtime.server.processing.PipelineElement
import org.netvisor.runtime.server.request.Field

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
object NoOp extends PipelineElement[Map[String,Field]] {
  def apply(v1: Map[String, Field]): Boolean = true
}
