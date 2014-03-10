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
object StopVMPipeline {
  lazy val pipeline = new ParamProcessingPipeline(
    List(
      Stopper// requirements check
    )
  )
}
