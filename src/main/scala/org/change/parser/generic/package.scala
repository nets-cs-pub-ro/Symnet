package parser

package object generic {
  type Name = String
  type PortId = Int
  type InputPort = PortId
  type OutputPort = PortId
  type PathComponent = (Name, InputPort, OutputPort)
}
