package org.change.parser.verification

import generated.reachlang.ReachLangBaseVisitor
import generated.reachlang.ReachLangParser.InvariantContext
import scala.collection.JavaConverters._
import org.change.symbolicexec.types._

object InvariantListParser extends ReachLangBaseVisitor[List[String]] {
  override def visitInvariant(ctx: InvariantContext): List[String] =
    (for {
      f <- ctx.field().asScala
      if TcpDumpFields.contains(f.getText)
    } yield tcpDumpToCanonical(f.getText)).toList
}
