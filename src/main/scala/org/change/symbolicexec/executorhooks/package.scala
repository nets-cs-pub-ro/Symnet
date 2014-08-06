package org.change.symbolicexec

package object executorhooks {

  def printHook: HookFunction = (current: List[Path], next: List[Path], stuck: List[Path]) => {
    println("=======================================================================================================")
    println(s"Exploring:\n${current.mkString("\n")}")
    println("=======================================================================================================")
    println(s"Stuck:\n${stuck.mkString("\n")}")
    println("=======================================================================================================")
  }

  def noopHook: HookFunction = (current: List[Path], next: List[Path], stuck: List[Path]) => { }

}
