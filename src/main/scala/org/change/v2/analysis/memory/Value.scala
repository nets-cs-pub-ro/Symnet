package org.change.v2.analysis.memory

import org.change.v2.analysis.constraint.Constraint
import org.change.v2.analysis.expression.Expression

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Value(e: Expression, cts: List[Constraint] = Nil)
