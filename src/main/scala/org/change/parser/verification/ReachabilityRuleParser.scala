package org.change.parser.verification

import generated.reachlang.ReachLangBaseVisitor
import generated.reachlang.ReachLangParser.ConditionContext
import org.change.symbolicexec.{PathLocation, MemoryState}

//REMINDER
import scala.collection.JavaConverters._

class ReachabilityRuleParser extends ReachLangBaseVisitor[Unit] {

  override def visitCondition(ctx: ConditionContext): Unit = {
    if (ctx.trafficdesc() != null) ctx.trafficdesc().accept(TrafficDescriptionParser)
  }
}
