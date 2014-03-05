package org.netvisor.runtime.server.processing

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 3/4/14
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
trait PipelineElement[A] extends Function1[A, Boolean]
