package org.change.v3.sefl.expression.abst

import org.change.v2.analysis.memory.State

/**
 * Created by radu on 3/24/15.
 *
 * A floating expression uses references instead of concrete values.
 *
 * The bindings to concrete values are made according to a state.
 */
trait FloatingExpression {
  /**
   * A floating expression may include unbounded references (e.g. symbol ids)
   *
   * Given a context (the state) it can produce an evaluable expression.
   * @param s
   * @return
   */
  def instantiate(s: State): Either[Expression, String]
}
