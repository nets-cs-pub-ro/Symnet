package org.change.v2.executor

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
trait ExecutionContextBuilder[N] {

  def buildExecutionContext(networkModel: N): ExecutionContext
  
}
