package org.change.v2.util

import org.apache.commons.io.filefilter.FalseFileFilter

/**
 * Created by radu on 3/27/15.
 */
package object codeabstractions {

  def mutateAndReturn[T](mutable: T)(mutator: T => Unit): T = {
    mutator(mutable)
    mutable
  }

  def mutateInSeqAndReturn[T](mutable: T)(mutators: List[(T => Unit)]): T =
    mutators.foldLeft(mutable)(mutateAndReturn(_)(_))

  def replaceHead[T](l: List[T], newHead: T): List[T] = l match {
    case Nil => Nil
    case h :: rest => newHead :: rest
  }

  def addToMapping[K,V](map: Map[K, List[V]], k: K, v: V): Map[K, List[V]] = map.get(k) match {
    case None => map + (k -> List(v))
    case Some(l) => map + (k -> (v :: l))
  }
}
