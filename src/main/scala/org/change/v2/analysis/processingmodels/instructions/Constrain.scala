package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.constraint._
import org.change.v2.analysis.expression.abst.FloatingExpression
import org.change.v2.analysis.processingmodels.{State, Instruction}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class Constrain (id: String, dc: FloatingConstraint) extends Instruction {
  override def apply(s: State): (List[State], List[State]) = {
    dc instantiate s match {
      case Left(c) => optionToStatePair(s, s"Error during 'constrain' for symbol $id") {
        _.memory.Constrain(id, c)
      }
      case Right(err) => Fail(err)(s)
    }
  }
}

trait FloatingConstraint {
  def instantiate(s: State): Either[Constraint, String]
}

case class :|:(a: FloatingConstraint, b: FloatingConstraint) extends FloatingConstraint {
  override def instantiate(s: State): Either[Constraint, String] = a instantiate s match {
    case Left(ac) => b instantiate s match {
      case Left(bc) => Left(OR(List(ac, bc)))
      case err @ Right(_) => err
    }
    case err @ Right(_) => err
  }
}

case class :&:(a: FloatingConstraint, b: FloatingConstraint) extends FloatingConstraint {
  override def instantiate(s: State): Either[Constraint, String] = a instantiate s match {
    case Left(ac) => b instantiate s match {
      case Left(bc) => Left(AND(List(ac, bc)))
      case err @ Right(_) => err
    }
    case err @ Right(_) => err
  }
}

case class :~:(c: FloatingConstraint) extends FloatingConstraint {
  override def instantiate(s: State): Either[Constraint, String] = c instantiate s match {
    case Left(c) => Left(NOT(c))
    case err @ Right(_) => err
  }
}

case class :==:(exp: FloatingExpression) extends FloatingConstraint {
  override def instantiate(s: State): Either[Constraint, String] = exp instantiate s match {
    case Left(e) => Left(EQ_E(e))
    case Right(err) => Right(err)
  }
}

case class :<:(exp: FloatingExpression) extends FloatingConstraint {
  override def instantiate(s: State): Either[Constraint, String] = exp instantiate s match {
    case Left(e) => Left(LT_E(e))
    case Right(err) => Right(err)
  }
}

case class :<=:(exp: FloatingExpression) extends FloatingConstraint {
  override def instantiate(s: State): Either[Constraint, String] = exp instantiate s match {
    case Left(e) => Left(LTE_E(e))
    case Right(err) => Right(err)
  }
}

case class :>:(exp: FloatingExpression) extends FloatingConstraint {
  override def instantiate(s: State): Either[Constraint, String] = exp instantiate s match {
    case Left(e) => Left(GT_E(e))
    case Right(err) => Right(err)
  }
}

case class :>=:(exp: FloatingExpression) extends FloatingConstraint {
  override def instantiate(s: State): Either[Constraint, String] = exp instantiate s match {
    case Left(e) => Left(GTE_E(e))
    case Right(err) => Right(err)
  }
}
