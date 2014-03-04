package org.symnet.runtime.server.processing.general.processors

import org.symnet.runtime.server.processing.PipelineElement
import org.symnet.runtime.server.request.Field

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
object AlwaysAuthorize extends PipelineElement[Map[String,Field]] {
  def apply(v1: Map[String, Field]): Boolean = true
}
