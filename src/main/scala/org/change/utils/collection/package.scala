package org.change.utils

package object collection {

  def extractFirst[A](collection: List[A])(p: A => Boolean): (A, List[A]) = {
    val occurrence = collection.indexWhere(p)
    val (a,b) = collection.splitAt(occurrence)
    (b.head, a ++ b.tail)
  }

}
