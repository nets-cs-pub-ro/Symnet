package org.netvisor.runtime.server.processing.start.pipeline

import org.netvisor.runtime.server.processing.{PipelineElement, ParamProcessingPipeline}
import org.netvisor.runtime.server.request.Field
import org.netvisor.runtime.server.processing.general.processors.NoOp

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
class StartVMPipeline(elements: List[PipelineElement[Map[String,Field]]])
  extends ParamProcessingPipeline(
    List(
      NoOp // authorization
    , NoOp // resources
    , NoOp // unique
    , NoOp // requirements check
    , NoOp // Start the VM
    )) {

}
