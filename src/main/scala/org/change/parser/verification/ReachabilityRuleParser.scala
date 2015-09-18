package org.change.parser.verification

import generated.reachlang.ReachLangBaseVisitor
import generated.reachlang.ReachLangParser._
import org.change.symbolicexec.verification.{ReachabilityTest, ReachabilityTestGroup, Rule}
import org.change.v2.analysis.processingmodels.instructions.ConstrainRaw
import org.change.v2.executor.clickabstractnetwork.verificator.{Output, PathLocation}

//REMINDER
import scala.collection.JavaConverters._

object TestsParser extends ReachLangBaseVisitor[ReachabilityTestGroup] {
  override def visitRequirements(ctx: RequirementsContext): ReachabilityTestGroup =
    ctx.test().asScala.map(_.accept(TestParser)).toList
}

object TestParser extends ReachLangBaseVisitor[ReachabilityTest] {
  override def visitTest(ctx: TestContext): ReachabilityTest = ctx.middle().asScala.map(_.accept(RuleVisitor)).toList
}

object ConditionVisitor extends ReachLangBaseVisitor[List[ConstrainRaw]] {
  override def visitCondition(ctx: ConditionContext): List[ConstrainRaw] =
    if (ctx.trafficdesc() != null)
      ctx.trafficdesc().accept(TrafficDescriptionParser)
    else
      Nil
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
    val conditions = ctx.condition().accept(ConditionVisitor)
    val port = ctx.nport().accept(PathLocationVisitor)
    val invariants = if (ctx.condition() != null && ctx.condition().invariant() != null)
      ctx.condition().invariant().accept(InvariantListParser)
    else
      Nil
    Rule(port, conditions, if (invariants.nonEmpty) Some(invariants) else None)
  }
}
