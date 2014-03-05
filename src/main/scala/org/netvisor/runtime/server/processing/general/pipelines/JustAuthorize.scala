package org.netvisor.runtime.server.processing.general.pipelines

import org.netvisor.runtime.server.processing.Pipeline
import org.netvisor.runtime.server.processing.general.processors.NoOp
import org.netvisor.runtime.server.request.Field

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
object JustAuthorize extends Pipeline[Map[String,Field]] (List(NoOp))
