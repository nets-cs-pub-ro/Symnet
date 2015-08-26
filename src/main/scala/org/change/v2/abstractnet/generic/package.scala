package org.change.v2.abstractnet

package object generic {
  type Name = String
  type PortId = Int
  type InputPort = PortId
  type OutputPort = PortId
  type PathComponent = (Name, InputPort, OutputPort)
}
