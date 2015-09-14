package org.change.v2.analysis.memory

/**
 * Author: Radu Stoenescu
 * Don't be a stranger,  symnetic.7.radustoe@spamgourmet.com
 */
case class MemoryObject(valueStack: List[ValueStack] = List(ValueStack.empty),
                        size: Int = 0) {

  def value: Option[Value] = valueStack.headOption.flatMap(_.value)
  def initialValue: Option[Value] = valueStack.lastOption.flatMap(_.initialValue)

  def addValue(v: Value): MemoryObject = if (isVoid)
      MemoryObject(List(ValueStack.empty.addDefinition(v)), size)
    else
      MemoryObject(valueStack.head.addDefinition(v) :: valueStack.tail, size)

  def replaceValue(v: Value): Option[MemoryObject] = if (isVoid)
    None
  else
    Some(MemoryObject(valueStack.head.replaceLatestValue(v) :: valueStack.tail, size))

  def allocateNewStack: MemoryObject = MemoryObject(ValueStack.empty :: valueStack, size)

  def deallocateStack: Option[MemoryObject] = if (isVoid)
    None
  else
    Some(MemoryObject(valueStack.tail, size))

  def isVoid: Boolean = valueStack.isEmpty
}