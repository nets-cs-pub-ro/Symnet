package org.change.parser.verification

import generated.reachlang.ReachLangBaseVisitor
import generated.reachlang.ReachLangParser._
import org.change.symbolicexec.verification.{ReachabilityTest, ReachabilityTestGroup, Rule}
import org.change.symbolicexec.{Output, PathLocation, MemoryState}

//REMINDER
import scala.collection.JavaConverters._

object TestsParser extends ReachLangBaseVisitor[ReachabilityTestGroup] {
  override def visitRequirements(ctx: RequirementsContext): ReachabilityTestGroup =
    ctx.test().asScala.map(_.accept(TestParser)).toList
}

object TestParser extends ReachLangBaseVisitor[ReachabilityTest] {
  override def visitTest(ctx: TestContext): ReachabilityTest = ctx.middle().asScala.map(_.accept(RuleVisitor)).toList
}

object ConditionVisitor extends ReachLangBaseVisitor[Option[MemoryState]] {
  override def visitCondition(ctx: ConditionContext): Option[MemoryState] =
    if (ctx.trafficdesc() != null) Some(ctx.trafficdesc().accept(TrafficDescriptionParser))
    else None
}

object PathLocationVisitor extends ReachLangBaseVisitor[PathLocation] {
  override def visitNport(ctx: NportContext): PathLocation = {
    val vm = ctx.ID(0).getText
    val elm = ctx.ID(1).getText
    val port = if (ctx.NUMBER() != null) java.lang.Integer.parseInt(ctx.NUMBER().getText)
      else 0

    PathLocation(vm, elm, port, Output)
  }
}

object RuleVisitor extends ReachLangBaseVisitor[Rule] {
  override def visitMiddle(ctx: MiddleContext): Rule = {
    val memState = ctx.condition().accept(ConditionVisitor)
    val port = ctx.nport().accept(PathLocationVisitor)
    val invariants = if (ctx.condition() != null && ctx.condition().invariant() != null)
      ctx.condition().invariant().accept(InvariantListParser)
    else
      Nil
    Rule(port, memState, if (invariants.nonEmpty) Some(invariants) else None)
  }
}
