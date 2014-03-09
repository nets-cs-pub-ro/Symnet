package org.change.runtime.server.processing.start.pipeline

import org.change.runtime.server.processing.{ParamProcessingPipeline, PipelineElement}
import org.change.runtime.server.request.Field
import org.change.runtime.server.processing.general.processors.NoOp
import org.change.runtime.server.processing.start.processors.ParseAndCheck

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
object StartVMPipeline {

  lazy val pipeline = new ParamProcessingPipeline(
    List(
      NoOp // authorization
      , NoOp // resources
      , NoOp // unique
      , ParseAndCheck // requirements check
      , NoOp // store the file
      , NoOp // Start the VM
    ))

}