package org.change.v2.abstractnet.neutron.elements

import org.change.v2.abstractnet.neutron.NeutronWrapper
import org.change.v2.analysis.processingmodels.instructions.NoOp
import org.change.v2.analysis.processingmodels.Instruction

class NetInstance(wrapper : NeutronWrapper) extends BaseNetElement(wrapper) {
  def symnetCode() : Instruction = {
    NoOp
  }
}