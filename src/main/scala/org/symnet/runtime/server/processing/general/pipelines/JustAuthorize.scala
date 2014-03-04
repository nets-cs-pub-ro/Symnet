package org.symnet.runtime.server.processing.general.pipelines

import org.symnet.runtime.server.processing.Pipeline
import org.symnet.runtime.server.processing.general.processors.AlwaysAuthorize
import org.symnet.runtime.server.request.Field

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
object JustAuthorize extends Pipeline[Map[String,Field]] (List(AlwaysAuthorize))
