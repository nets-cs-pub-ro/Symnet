package org.change.symbolicexec

import scala.collection.mutable.MutableList

case class Variable(name: String) {

  private val values: MutableList[Value] = MutableList()

  def assignCount = values.length
}
