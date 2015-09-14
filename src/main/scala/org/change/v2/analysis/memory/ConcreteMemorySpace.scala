package org.change.v2.analysis.memory

import scala.util.Random

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
class ConcreteMemorySpace(
                         val symbols: Map[String, (Option[Int], Option[Int])],
                         val rawObjects: Map[Int, (Option[Int], Option[Int])]
                           ) {

  override def toString =
    symbols.map(kv => kv._1 -> (kv._2._1.getOrElse(ConcreteMemorySpace.generator.nextInt()),
      kv._2._2.getOrElse(ConcreteMemorySpace.generator.nextInt()))).mkString("\n") +
    rawObjects.map(kv => kv._1 -> (kv._2._1.getOrElse(ConcreteMemorySpace.generator.nextInt()),
      kv._2._2.getOrElse(ConcreteMemorySpace.generator.nextInt()))).mkString("\n")

}

object ConcreteMemorySpace {

  lazy val generator = new Random()

  def fromSymbolic(ms: MemorySpace): ConcreteMemorySpace = {
    new ConcreteMemorySpace(ms.symbols.map(kv => kv._1 ->
      (kv._2.value.flatMap(ms.exampleFor(_)),
        kv._2.initialValue.flatMap(ms.exampleFor(_)))),
    ms.rawObjects.map(kv => kv._1 ->
      (kv._2.value.flatMap(ms.exampleFor(_)),
        kv._2.initialValue.flatMap(ms.exampleFor(_)))))
  }

}
