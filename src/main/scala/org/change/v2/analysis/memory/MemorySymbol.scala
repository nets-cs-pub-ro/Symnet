package org.change.v2.analysis.memory

import org.change.v2.analysis.constraint.Constraint
import org.change.v2.analysis.expression.Expression
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.types.{LongType, NumericType}
import org.change.v2.interval.ValueSet
import org.change.v2.util.codeabstractions._

/**
 * Created by radu on 3/24/15.
 */
abstract class MemorySymbol(
  val name: String,
  val symbolType: NumericType = LongType,
  var valueStack: List[Value] = Nil,
  var hidden: Boolean = false) {

  private var cacheAge = -1

  private val selfMutate = mutateAndReturn(this)_

  private def rewrite(exp: Expression, constraints: List[Constraint]): MemorySymbol =
    mutateAndReturn(this){ arg =>
      arg.valueStack = (exp, constraints) :: arg.valueStack
      hidden = false
    }

  /**
   * If possible, computes the admissible value set for the current symbol.
   * @return
   */
  def admissibleValueSet: Option[ValueSet]

  def rewriteToNewSymbolic(constraints: List[Constraint] = Nil): MemorySymbol =
    rewrite(new SymbolicValue(), constraints)

  def rewriteToConstant(constant: Long, constraints: List[Constraint] = Nil): MemorySymbol =
    rewrite(new ConstantValue(constant), constraints)

  def currentValue: (Value, Int) = (valueStack.head, valueStack.length)

  def currentValueOnly: Value = valueStack.head

  def hide: MemorySymbol = selfMutate { _.hidden = true }

  def show: MemorySymbol = selfMutate { _.hidden = true }

  def constrain(c: Constraint): MemorySymbol =
    mutateAndReturn(this){ arg =>
      val (exp, cts) = arg.valueStack.head
      arg.valueStack = (exp, c :: cts) :: arg.valueStack.tail
    }

  def constrain(cs: List[Constraint]): MemorySymbol = cs.foldLeft(this) ( _ constrain _ )

}
