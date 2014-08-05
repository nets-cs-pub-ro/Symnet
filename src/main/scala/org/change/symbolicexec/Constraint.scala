package org.change.symbolicexec

trait Constraint

case class LT(v: Long) extends Constraint
case class LTE(v: Long) extends Constraint
case class GT(v: Long) extends Constraint
case class GTE(v: Long) extends Constraint
case class E(v: Long) extends Constraint
case class Range(v1: Long, v2: Long) extends Constraint
case class RangeSeries(ranges: List[Range]) extends Constraint