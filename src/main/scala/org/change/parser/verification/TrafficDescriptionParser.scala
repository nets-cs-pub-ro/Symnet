package org.change.parser.verification

import scala.collection.JavaConverters._
import generated.reachlang.ReachLangBaseVisitor
import generated.reachlang.ReachLangParser._
import org.change.symbolicexec.{E, Range, Constraint, Memory, MemoryState, Symbol}
import org.change.utils.{NumberFor, RepresentationConversion}

object TrafficDescriptionParser extends ReachLangBaseVisitor[MemoryState] {
  override def visitTrafficdesc(ctx: TrafficdescContext): MemoryState = {
    val m = new Memory()

    val ms = ctx.constraint().asScala.foldLeft(m) ((m,c) => {
      val (s, cns) = ConstraintVisitor.visitConstraint(c)
      m.constrain(s, cns)
    }).snapshotOfAll

    ms
  }
}

object ConstraintVisitor extends ReachLangBaseVisitor[(Symbol, Constraint)] {

  override def visitConstraint(ctx: ConstraintContext): (Symbol, Constraint) = {
//    TODO: This looks silly
    if (ctx.ipconstraint() != null) visitIpconstraint(ctx.ipconstraint())
    else if (ctx.l4constraint() != null) visitL4constraint(ctx.l4constraint())
    else visitProtoconstraint(ctx.protoconstraint())
  }

  override def visitIpconstraint(ctx: IpconstraintContext): (Symbol, Constraint) =
    (
      ctx.ipfield().getText match {
        case "src" => "IP-Src"
        case "dst" => "IP-Dst"
      },
      if (ctx.ipv4() != null) {
        E(RepresentationConversion.ipToNumber(ctx.ipv4().getText))
      } else {
        Range(RepresentationConversion.ipAndMaskToInterval(ctx.mask.ipv4().getText, ctx.mask.NUMBER().getText))
      }
    )

  override def visitL4constraint(ctx: L4constraintContext): (Symbol, Constraint) =
    (
      ctx.l4field().getText match {
        case "src port" => "Port-Src"
        case "dst port" => "Port-Dst"
      },
      if (ctx.range() != null) {
        Range(java.lang.Long.parseLong(ctx.range().NUMBER(0).getText), java.lang.Long.parseLong(ctx.range().NUMBER(1).getText))
      } else {
        E(java.lang.Long.parseLong(ctx.NUMBER().getText))
      }
    )

  override def visitProtoconstraint(ctx: ProtoconstraintContext): (Symbol, Constraint) = ("Proto", E(NumberFor(ctx.getText)))
}
