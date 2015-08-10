package org.change.v2.executor.clickabstractnetwork

import org.change.v2.abstractnet.generic.NetworkConfig
import org.change.v2.executor.{ExecutionContext, ExecutionContextBuilder}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
object ClickExecutionContextBuilder extends ExecutionContextBuilder[NetworkConfig] {
  override def buildExecutionContext(networkModel: NetworkConfig): ExecutionContext = ???
}
