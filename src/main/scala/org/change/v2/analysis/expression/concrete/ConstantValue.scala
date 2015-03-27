package org.change.v2.analysis.expression.concrete

import org.change.v2.analysis.expression.Expression

/**
 * Created by radu on 3/24/15.
 */
class ConstantValue(val value: Long, id: Long = Expression.randomId) extends Expression(id)
