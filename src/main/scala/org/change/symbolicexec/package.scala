package org.change

import org.change.symbolicexec.types.NumericType

package object symbolicexec {

  type Interval = (Long, Long)
  type ValueSet = List[Interval]
  type Symbol = String
//  The memory consists of a set of symbols to which values are assigned
//  Observation: because the type is an attribute of the value, not of the symbol,
//  a symbol may refer to values of different types at different times.

  /**
   * Check if two intervals intersect.
   * @param a
   * @param b
   * @return
   */
  def doIntersect(a: Interval, b: Interval) =
    (a._1 <= b._1 && b._1 <= a._2) || (a._1 <= b._1 && b._2 <= a._2)

  def intersect(a: Interval, b: Interval): Option[Interval] =
    if (a._1 <= b._1 && b._1 <= a._2) Some((b._1, Math.min(b._2, a._2)))
    else if (a._1 <= b._2 && b._2 <= a._2) Some((Math.max(b._1, a._1), b._2))
    else if (a._1 <= b._1 && a._2 >= b._2) Some(b._1, b._2)
    else if (a._1 >= b._1 && a._2 <= b._2) Some(a._1, a._2)
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

  def complement(s: ValueSet, t: NumericType = NumericType()): ValueSet = s match {
    case Nil => List((t.min, t.max))
    case List((a, b)) if a > t.min && b < t.max => normalize(List((t.min, a-1), (b+1, t.max)))
    case List((_,_)) => Nil
    case _ => {
      val aux = (for {
        w <- s.sliding(2)
        fst = w.head
        snd = w.last
      } yield (fst._2+1, snd._1-1)).toList
      normalize(
        (if (s.head._1 > t.min) List((t.min, s.head._1-1)) else Nil) ++
        (if (s.last._2 < t.max) List((s.last._2+1, t.max)) else Nil) ++
        aux)
    }
  }

  def intersect(all: List[ValueSet]): ValueSet = {
    def looper(a: ValueSet, b: ValueSet): ValueSet = a match {
      case Nil => Nil
      case i :: rest => {
        val stripNeedless = b.dropWhile(_._2 < i._1)
        stripNeedless
          .takeWhile(i._2 >= _._1)
          .map(that =>
            intersect(i, that))
          .filter(_ match {
            case Some(_) => true
            case _ => false})
          .map(_ match {
            case Some(r) => r}) ++ looper(rest, stripNeedless)
      }
    }
    val nAll = all.map(normalize)
    val res = nAll.reduceLeft(looper)
    res
  }

  def applyConstraint(s: ValueSet, c: Constraint,
                      t: NumericType = NumericType()): ValueSet =
    intersect(List(s, c.asSet(t)))

  def applyConstraints(s: ValueSet, cts: List[Constraint],
                      t: NumericType = NumericType()): ValueSet =
    intersect(s :: cts.map(_.asSet()))
}
