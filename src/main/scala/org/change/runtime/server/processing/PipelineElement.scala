package org.change.runtime.server.processing

import org.change.runtime.server.request.Field

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
trait PipelineElement[A] extends Function1[A, Boolean]

trait ParamPipelineElement extends PipelineElement[Map[String,Field]]
