package org.change.symbolicexec.blocks.cisco

import org.change.symbolicexec.blocks.NoopProcessingBlock
import org.change.symbolicexec.blocks.cisco.Switch.{CAMTableEntry, PortName}
import org.change.utils.RepresentationConversion

/**
 * Created by radu on 08.01.2015.
 */
class Switch(id: String, val ports: List[PortName], val offPorts: List[String],
              val camEntries: List[CAMTableEntry])
  extends NoopProcessingBlock(id, ports.length, ports.length) {

}

object Switch {

  type CAMTableEntry = (Int, Long, String)
  type PortName = String

  def apply(id: String, camEntries: List[(String, String, String)], ports: List[String]): Switch = {

    new Switch(id, ports, Nil, camEntries.map(e =>
      (Integer.parseInt(e._1),
        RepresentationConversion.macToNumberCiscoFormat(e._2),
        e._3)))

  }


}
