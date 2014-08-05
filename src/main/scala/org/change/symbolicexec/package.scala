package org.change

package object symbolicexec {

  type Interval = (Long, Long)
  type ValueSet = List[Interval]

  def doIntersect(a: Interval, b: Interval) =
    (a._1 <= b._1 && b._1 <= a._2) || (a._1 <= b._1 && b._2 <= a._2)

  def intersection(a: Interval, b: Interval) =
    if (a._1 <= b._1 && b._1 <= a._2) Some((b._1, Math.min(b._2, a._2)))
    else if (a._1 <= b._2 && b._1 <= a._2) Some((Math.max(b._1, a._1), b._2))
    else None

  def unionOfIntersecting(a: Interval, b: Interval) =
    if (doIntersect(a,b)) Some((Math.min(a._1, b._1), Math.max(a._2, b._2)))
    else None

  def normalize(set: List[Interval]): List[Interval] = {
    normalizeSorted(set.sortBy(_._1))
  }

  def normalizeSorted(s: ValueSet): ValueSet = s match {
    case i1 :: i2 :: rest => unionOfIntersecting(i1, i2) match {
      case Some(union) => normalizeSorted(union :: rest)
      case None => i1 :: normalizeSorted(i2 :: rest)
    }
    case _ => s
  }

  def union(all: List[ValueSet]): ValueSet = normalize(all.flatten)

}
