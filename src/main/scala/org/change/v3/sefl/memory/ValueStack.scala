package org.change.v3.sefl.memory

import org.change.v2.analysis.constraint.Constraint
import org.change.v2.util.codeabstractions._
import spray.json._

/**
 * Created by radu on 3/24/15.
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 *
 * For every symbol, multiple definitions may exist, forming a stack of values.
 */
case class ValueStack(val vs: List[Value] = Nil) {
  def jsonString = this.toJson.prettyPrint

  override def toString = jsonString

  def value: Option[Value] = vs.headOption

  def currentValueOnly: Value = vs.head

  def initialValue: Option[Value] = vs.lastOption

  def constrain(c: Constraint): ValueStack = vs match {
    case v :: otherVs => ValueStack(Value(v.e, v.eType, c :: v.cts) :: otherVs)
    case _ => this
  }

  def replaceLatestValue(v: Value): ValueStack = ValueStack(replaceHead(vs,v))

  def constrain(cs: List[Constraint]): ValueStack = cs.foldLeft(this) ( _ constrain _ )

  def addDefinition(v: Value): ValueStack = ValueStack( v :: vs)

}

object ValueStack {
  def empty: ValueStack = ValueStack()
}