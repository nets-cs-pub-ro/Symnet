package org.change.v2.analysis.memory

import org.change.v2.analysis.types.NumericType
import org.change.v2.analysis.expression.Expression

/**
 * Created by radu on 3/24/15.
 */
class MemorySymbol(
  val name: String,
  val symbolType: NumericType,
  var expressionStack: List[Expression]) {

}
