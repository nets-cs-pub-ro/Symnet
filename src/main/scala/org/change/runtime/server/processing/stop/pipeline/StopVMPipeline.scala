package org.change.runtime.server.processing.stop.pipeline

import org.change.runtime.server.processing.ParamProcessingPipeline
import org.change.runtime.server.processing.general.processors.NoOp
import org.change.runtime.server.processing.stop.processors.Stopper

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
class StopVMPipeline {
  lazy val pipeline = new ParamProcessingPipeline(
    List(
    NoOp // authorization
    , NoOp // resources
    , NoOp // unique
    , Stopper// requirements check
    , NoOp // store the file
    , NoOp // Start the VM
    )
  )
}
