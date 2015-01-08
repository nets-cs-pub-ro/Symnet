package org.change.symbolicexec.blocks.cisco

import org.change.symbolicexec._
import org.change.symbolicexec.blocks.NoopProcessingBlock
import org.change.symbolicexec.blocks.cisco.Switch.{CAMTableEntry}
import org.change.utils.RepresentationConversion

import scala.collection.mutable.ListBuffer

/**
 * Created by radu on 08.01.2015.
 */
class Switch(id: String, val ports: List[String], val offPorts: List[String],
              val camEntries: List[CAMTableEntry])
  extends NoopProcessingBlock(id, ports.length, ports.length) {

  private val portNameToNumber: Map[String, Int] = ports.zipWithIndex.toMap

  private var forUnmatched: Map[Int, List[OR]] = Map()

  private val constraintCache: Map[String, Map[E, OR]] = camEntries.groupBy(_._3).map { case (port, entries) => {
      (port, entries.groupBy(_._1).map{ case (vlan, rEntries) =>
        val macConstraint = OR(rEntries.map(re => E(re._2)))
        forUnmatched = forUnmatched.+((vlan, macConstraint :: forUnmatched.getOrElse(vlan, Nil) ))
        (E(vlan), macConstraint)
      })
    }
  }

  val floodConstraints: Map[Long, NOT] = forUnmatched.map { vcts =>
      (vcts._1: Long, NOT(OR(vcts._2)))
    }.toMap

  override def process(p: Path): List[Path] = {
    val entryPort = p.locationPort

    ((for {
      (port, entries) <- constraintCache
      if (! port.equals(port(entryPort)))
      (vlan, macConstraint) <- entries
    } yield {
      p.modifyAndMove( mem => {
        mem.constrain("VLAN", vlan).constrain("MAC", macConstraint)
      }, PathLocation("0", id, portNameToNumber(port), Output))
    }) ++ (for {
      (port, entries) <- constraintCache
      if (! port.equals(port(entryPort)))
      (vlan, macConstraint) <- entries
    } yield {
      p.modifyAndMove( mem => {
        mem.constrain("VLAN", vlan).constrain("MAC", floodConstraints(vlan.v))
      }, PathLocation("0", id, portNameToNumber(port), Output))
    })).toList
  }
}

object Switch {

  type CAMTableEntry = (Int, Long, String)

  def apply(id: String, camEntries: List[(String, String, String)], ports: List[String]): Switch = {

    new Switch(id, ports, Nil, camEntries.map(e =>
      (Integer.parseInt(e._1),
        RepresentationConversion.macToNumberCiscoFormat(e._2),
        e._3)))

  }

}
