package org.change.v2.analysis.memory

import org.change.v2.analysis.processingmodels.{Instruction, State}

/**
  * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Tag(val name: String) extends Intable {

  def +(other: TagExp): TagExp = TagExp(this :: other.plusTags, other.minusTags , other.rest)
  def +(other: Int): TagExp = TagExp(List(this), Nil , other)

  def -(other: TagExp): TagExp = TagExp(other.plusTags, this :: other.minusTags , other.rest)
  def -(other: Int): TagExp = TagExp(List(this), Nil , -other)

  override def apply(v1: State): Option[Int] = v1.memory.memTags.get(name)

  override def toString = name
}

case class TagExp(plusTags: List[Tag], minusTags: List[Tag], rest: Int) extends Intable {
  def +(other: TagExp): TagExp = TagExp(this.plusTags ++ other.plusTags, this.minusTags ++ other.minusTags , this.rest + other.rest)
  def +(other: Tag): TagExp = TagExp(other :: this.plusTags, this.minusTags, rest)
  def +(other: Int): TagExp = TagExp(plusTags, minusTags , other + rest)

  def -(other: TagExp): TagExp = TagExp(this.plusTags ++ other.minusTags, this.minusTags ++ other.plusTags , this.rest - other.rest)
  def -(other: Tag): TagExp = TagExp(this.plusTags, other :: this.minusTags, rest)
  def -(other: Int): TagExp = TagExp(plusTags, minusTags , rest-other)

  override def apply(v1: State): Option[Int] = if ((plusTags ++ minusTags).forall(t => v1.memory.memTags.contains(t.name)))
    Some(minusTags.foldLeft(plusTags.foldLeft(rest)((acc, t) => acc + v1.memory.memTags(t.name))) ((acc, t) => acc - v1.memory.memTags(t.name)))
  else
    None

  override def toString = plusTags.mkString("+") + (if (minusTags.nonEmpty) "-" + minusTags.mkString("-") else "") + TagExp.IntImprovements(rest)
}

object TagExp {

  val brokenTagExpErrorMessage = "Cannot resolve expression to an int address"

  implicit class IntImprovements(val value: Int) extends Intable {
    override def apply(v1: State): Option[Int] = Some(value)
    override def toString = if (value >= 0) s"+$value" else s"-$value"
  }

}