/**
 * Created by radu on 29.01.2015.
 */

import org.change.improvedse.Memory
import org.change.symbolicexec.{LT, E}
import org.scalatest._
import org.change.symbolicexec.types.NumericType

class TestSymbolicValueMemoryOps extends FlatSpec with Matchers {

  "Memory" should "retain new symbols" in {
    val mem = new Memory().newVal("A")

    mem.exists("A") should be (true)
    mem.exists("B") should be (false)

    mem.symbolVersion("A") should be (Some(1))
  }

  "Unconstrained symbolic value" should "have an unconstrained admissible interval" in {
    val mem = new Memory().newVal("A")

    mem.evalSymbolToPossibleValues("A") match {
      case Some(vals) => vals should be (NumericType().admissibleSet)
      case _ => fail
    }
  }

  "Constraining a symbolic value" should "constrain the admissible set" in {
    val a = 100
    val b = 5
    val mem = new Memory().newVal("A").constrain("A", E(a))

    mem.evalSymbolToPossibleValues("A") match {
      case Some(vals) => vals should be (List((a,a)))
      case _ => fail
    }

    mem.constrain("A", LT(b)).evalSymbolToPossibleValues("A") match {
      case Some(vals) => vals should be (Nil)
      case _ => fail
    }
  }

}

class TestLiteralValueMemoryOps extends FlatSpec with Matchers {
  "Memory" should "retain new literals" in {
    val mem = new Memory().newLiteral("A", 10)

    mem.exists("A") should be (true)
    mem.exists("B") should be (false)

    mem.symbolVersion("A") should be (Some(1))
  }

  "Literals" should "represent their admissible set" in {
    val a = 10
    val b = 10
    val mem = new Memory().newLiteral("A", a)

    mem.symbolVersion("A") should be (Some(1))

    mem.evalSymbolToPossibleValues("A") match {
      case Some(vals) => vals should be (List((a,a)))
      case _ => fail
    }

    mem.constrain("A", LT(b)).evalSymbolToPossibleValues("A") match {
      case Some(vals) => vals should be (Nil)
      case _ => fail
    }
  }
}

class TestSymbolicExprsMemoryOps extends FlatSpec with Matchers {
  "Refs" should "evaluate to their targets" in {
    val memo = new Memory().newLiteral("A", 10).newRef("ARef", "A")

    memo match {
      case Some(mem) => (mem.evalSymbolToPossibleValues("A"), mem.evalSymbolToPossibleValues("ARef")) match {
          case (Some(admat), Some(admareft)) => admat should be(admareft)
          case _ => fail("Both should evaluate to an admissible set")
        }
      case _ => fail("A exists, ARef should not fail")
    }

  }
}