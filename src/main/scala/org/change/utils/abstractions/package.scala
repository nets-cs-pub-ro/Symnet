package org.change.utils

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
package object abstractions {

  def transform[A](option: Option[A])(someTransform: A => Some[A], noneTransform: () => Some[A]) = option
    match  {
    case Some(x) => someTransform(x)
    case _ => noneTransform
    }

}
