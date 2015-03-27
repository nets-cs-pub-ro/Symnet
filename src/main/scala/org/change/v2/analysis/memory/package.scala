package org.change.v2.analysis

import org.change.v2.analysis.constraint.Constraint
import org.change.v2.analysis.expression.Expression

/**
 * Created by radu on 3/27/15.
 */
package object memory {

  type Value = (Expression, List[Constraint])

}
