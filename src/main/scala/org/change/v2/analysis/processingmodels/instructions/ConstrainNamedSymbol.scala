package org.change.v2.analysis.processingmodels.instructions

import org.change.v2.analysis.constraint._
import org.change.v2.analysis.expression.abst.FloatingExpression
import org.change.v2.analysis.processingmodels.{State, Instruction}
import org.change.v2.analysis.memory.{TagExp, Intable}

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 *
 * TODO: When an already instantiated constrain is provided, one should be able not to provide the
 * floating one (This is also going to affect how the toString method works)
 */
case class ConstrainNamedSymbol (id: String, dc: FloatingConstraint, c: Option[Constraint] = None) extends Instruction {
  override def apply(s: State, v: Boolean): (List[State], List[State]) = c match {
    case None => dc instantiate s match {
      case Left(c) => optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Symbol $id cannot $dc") (s => {
        s.memory.Constrain(id, c)
      })
      case Right(err) => Fail(err)(s, v)
    }
    case Some(c) => optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Symbol $id cannot $dc") (s => {
      s.memory.Constrain(id, c)
    })
  }
}

case class ConstrainRaw (a: Intable, dc: FloatingConstraint, c: Option[Constraint] = None) extends Instruction {
  override def apply(s: State, v: Boolean): (List[State], List[State]) = a(s) match {
    case Some(int) => c match {
        case None => dc instantiate s match {
          case Left(c) => optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Memory object @ $a cannot $dc") (s => {
            s.memory.Constrain(int, c)
          })
          case Right(err) => Fail(err)(s, v)
        }
        case Some(c) => optionToStatePair(if (v) s.addInstructionToHistory(this) else s, s"Memory object @ $a cannot $dc") (s => {
          s.memory.Constrain(int, c)
        })
      }
    case None => Fail(TagExp.brokenTagExpErrorMessage)(s,v)
  }
}

object Constrain {
  def apply(id: String, dc: FloatingConstraint): Instruction =
    ConstrainNamedSymbol(id, dc, None)
  def apply (a: Intable, dc: FloatingConstraint): Instruction =
    ConstrainRaw(a, dc, None)
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
