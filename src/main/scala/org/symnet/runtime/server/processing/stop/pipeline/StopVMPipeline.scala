package org.symnet.runtime.server.processing.stop.pipeline

import org.symnet.runtime.server.processing.ParamProcessingPipeline
import org.symnet.runtime.server.processing.general.processors.NoOp

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
class StopVMPipeline extends ParamProcessingPipeline(
  List(
    NoOp // authorization
    , NoOp // name found
    , NoOp // stop the VM
  )) {

}
