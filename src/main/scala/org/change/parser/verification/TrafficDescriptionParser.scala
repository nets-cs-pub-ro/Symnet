package org.change.parser.verification

import org.change.v2.analysis.constraint._
import org.change.v2.analysis.expression.concrete.ConstantValue
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.util.canonicalnames._

import scala.collection.JavaConverters._
import generated.reachlang.ReachLangBaseVisitor
import generated.reachlang.ReachLangParser._
import org.change.symbolicexec.{E, Range, Constraint, Symbol}
import org.change.utils.{NumberFor, RepresentationConversion}

object TrafficDescriptionParser extends ReachLangBaseVisitor[List[ConstrainNamedSymbol]] {
  override def visitTrafficdesc(ctx: TrafficdescContext): List[ConstrainNamedSymbol] =
    ctx.constraint().asScala.map(ConstraintVisitor.visitConstraint(_)).toList
}

object ConstraintVisitor extends ReachLangBaseVisitor[ConstrainNamedSymbol] {

  override def visitConstraint(ctx: ConstraintContext): ConstrainNamedSymbol = {
    if (ctx.ipconstraint() != null) visitIpconstraint(ctx.ipconstraint())
    else if (ctx.l4constraint() != null) visitL4constraint(ctx.l4constraint())
    else visitProtoconstraint(ctx.protoconstraint())
  }

  override def visitIpconstraint(ctx: IpconstraintContext): ConstrainNamedSymbol = {
    val symb = ctx.ipfield().getText match {
      case "src" => IPSrc
      case "dst" => IPDst
    }

    val (floatingConstraint, instatiatedConstraint) = if (ctx.ipv4() != null) {
      val whatValue = ConstantValue(RepresentationConversion.ipToNumber(ctx.ipv4().getText))
      (:==:(whatValue), EQ_E(whatValue))
    } else {
      val (lower, upper) = RepresentationConversion.ipAndMaskToInterval(ctx.mask.ipv4().getText, ctx.mask.NUMBER().getText)
      val lowerValue = ConstantValue(lower)
      val upperValue = ConstantValue(upper)
      (:&:(:>=:(lowerValue), :<=:(upperValue)), AND(List(GTE_E(lowerValue), LTE_E(upperValue))))
    }

    ConstrainNamedSymbol(symb, floatingConstraint, Some(instatiatedConstraint))
  }

  override def visitL4constraint(ctx: L4constraintContext): ConstrainNamedSymbol = {
    val symb = ctx.l4field().getText match {
      case "src port" => PortSrc
      case "dst port" => PortDst
    }

    val (floatingConstraint, instatiatedConstraint) = if (ctx.range() != null) {
      val lowerValue = ConstantValue(java.lang.Long.parseLong(ctx.range().NUMBER(0).getText))
      val upperValue = ConstantValue(java.lang.Long.parseLong(ctx.range().NUMBER(1).getText))
      (:&:(:>=:(lowerValue), :<=:(upperValue)), AND(List(GTE_E(lowerValue), LTE_E(upperValue))))
    } else {
      val whatValue = ConstantValue(java.lang.Long.parseLong(ctx.NUMBER().getText))
      (:==:(whatValue), EQ_E(whatValue))
    }

    ConstrainNamedSymbol(symb, floatingConstraint, Some(instatiatedConstraint))
  }

  override def visitProtoconstraint(ctx: ProtoconstraintContext): ConstrainNamedSymbol = {
    val whatValue = ConstantValue(NumberFor(ctx.getText))
    val (floatingConstraint, instatiatedConstraint) = (:==:(whatValue), EQ_E(whatValue))
    ConstrainNamedSymbol(L4Proto, floatingConstraint, Some(instatiatedConstraint))
  }
}
