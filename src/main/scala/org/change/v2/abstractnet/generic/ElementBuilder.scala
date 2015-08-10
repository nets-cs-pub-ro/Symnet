package org.change.v2.abstractnet.generic

import scala.collection.mutable.ArrayBuffer

/**
 * Generic element builder implementing common behavior for every
 * Click network element.
 *
 * @param name An unique element ID within a network config
 * @param elementType String representation of the Click element class
 */
abstract class ElementBuilder(name: String,
                              elementType: String) {

  private val inputPorts = new ArrayBuffer[Port]()
  private val outputPorts = new ArrayBuffer[Port]()
  private val configPrams = new ArrayBuffer[ConfigParameter]()

  def addInputPort(port: Port): ElementBuilder = {
    inputPorts += port; this
  }

  def addOutputPort(port: Port): ElementBuilder = {
    outputPorts += port; this
  }

  def addConfigParameter(param: ConfigParameter): ElementBuilder = {
    configPrams += param; this
  }

  def handleConfigParameter(paramString: String): ElementBuilder = {
    val param = ConfigParameter(paramString)
    addConfigParameter(param)
  }

  def getInputPorts: List[Port] = inputPorts.toList

  def getOutputPorts: List[Port] = outputPorts.toList

  def getConfigParameters: List[ConfigParameter] = configPrams.toList

  def buildElement: GenericElement = ???
}
