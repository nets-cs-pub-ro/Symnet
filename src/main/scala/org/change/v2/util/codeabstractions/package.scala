package org.change.v2.util

/**
 * Created by radu on 3/27/15.
 */
package object codeabstractions {

  def mutateAndReturn[T](mutable: T)(mutator: T => Unit): T = {
    mutator(mutable)
    mutable
  }

}
