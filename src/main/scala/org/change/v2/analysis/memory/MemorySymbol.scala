package org.change.v2.analysis.memory

import org.change.v2.analysis.constraint.Constraint
import org.change.v2.analysis.expression.abst.Expression
import org.change.v2.analysis.expression.concrete.{ConstantValue, SymbolicValue}
import org.change.v2.analysis.types.{LongType, NumericType}
import org.change.v2.util.codeabstractions._

/**
 * Created by radu on 3/24/15.
 */
class MemorySymbol(
  val symbolType: NumericType = LongType,
  var valueStack: Seq[Value] = Nil,
  var hidden: Boolean = true) {

  override def toString = valueStack.toString()

  /*
      PUBLIC API
  */

  /*
    This should definitively not belong here.
   */

  /**
   * If possible, computes the admissible value set for the current symbol.
   * @return
   */
  //def admissibleValueSet: Option[ValueSet]

  def rewriteToNewSymbolic(constraints: List[Constraint] = Nil): MemorySymbol =
    rewrite(new SymbolicValue(), constraints)

  def rewriteToConstant(constant: Long, constraints: List[Constraint] = Nil): MemorySymbol =
    rewrite(new ConstantValue(constant), constraints)

  def currentValue: (Value, Int) = (valueStack.head, valueStack.length)

  def currentValueOnly: Value = valueStack.head

  def hide: MemorySymbol = selfUpdate { _.hidden = true }

  def show: MemorySymbol = selfUpdate { _.hidden = false }

  def constrain(c: Constraint): MemorySymbol =
    selfMutate { arg =>
      val Value(exp, cts) = arg.valueStack.head
      arg.valueStack = Value(exp, c :: cts) +: arg.valueStack.tail
    }

  def constrain(cs: List[Constraint]): MemorySymbol = cs.foldLeft(this) ( _ constrain _ )

  /*
      PRIVATE INNER WORKINGS
   */

  /**
   * Each mutation increases the age of the current symbol.
   *
   * I the cache age is equal to symbol's age then the cache is valid, otherwise it's not.
   */
  private var cacheAge = -1
  private var age = 0

  private def selfUpdate(f: (MemorySymbol => Unit)): MemorySymbol = {
    mutateAndReturn(this)(f)
  }

  private def selfMutate(f: (MemorySymbol => Unit)): MemorySymbol = {
    age += 1
    // A mutation automatically "uncovers" the symbol
    hidden = false
    selfUpdate(f)
  }

  /**
   * Assigns a new expression, updating the SSA stack.
   * @param exp
   * @param constraints
   * @return
   */
  def rewrite(exp: Expression, constraints: List[Constraint] = Nil): MemorySymbol =
    rewrite(Value(exp, constraints))

  def rewrite(v: Value): MemorySymbol =
    mutateAndReturn(this){ arg =>
      arg.valueStack = v +: arg.valueStack
      hidden = false
    }
}
