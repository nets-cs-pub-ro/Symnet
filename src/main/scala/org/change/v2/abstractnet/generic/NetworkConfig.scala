package org.change.v2.abstractnet.generic

import generated.click.ClickBaseListener
import generated.click.ClickParser._
import org.change.v2.abstractnet.click.sefl.{ToDevice, FromDevice}

import scala.collection.JavaConversions.collectionAsScalaIterable
import scala.collection.mutable.ArrayBuffer

class NetworkConfigBuilder(val configName: Option[String] = None) extends ClickBaseListener {

  /**
   * Start of line, just count it.
   **/
  override def enterLine(ctx: LineContext) {
    linesParsed += 1
  }

  /**
   * Building new element.
   *
   * When found new element definition init a new builder.
   **/
  override def enterNewElement(ctx: NewElementContext) {
    startElement(
      if (ctx.elementName() != null)
        Some(buildElementName(ctx.elementName().getText))
      else
        None,
      ctx.elementInstance().className().getText
    )
  }

  /**
   * Ask the builder to construct the element and add it to collection
   **/
  override def exitNewElement(ctx: NewElementContext) {
    endElement()
  }

  /**
   * When found a new config param send the info to the element builder.
   **/
  override def enterConfigParameter(ctx: ConfigParameterContext) {
    currentElementBuilder.handleConfigParameter(ctx.children.mkString(" "))
  }

  override def enterPath(ctx: PathContext) {
    pathBuilder = new ArrayBuffer()
  }

  override def enterElementName(ctx: ElementNameContext) {
    lastSeen = buildElementName(ctx.getText)
  }

  override def exitStartOfPath(ctx: StartOfPathContext) {
    addToPath(if (ctx.entryPort() != null) ctx.entryPort().port.portId.getText else null,
      if (ctx.exitPort() != null) ctx.exitPort().port.portId.getText else null)
  }

  override def exitInTheMiddle(ctx: InTheMiddleContext) {
    addToPath(if (ctx.entryPort() != null) ctx.entryPort().port.portId.getText else null,
      if (ctx.exitPort() != null) ctx.exitPort().port.portId.getText else null)
  }

  override def exitEndOfPath(ctx: EndOfPathContext) {
    addToPath(if (ctx.entryPort() != null) ctx.entryPort().port.portId.getText else null,
      if (ctx.exitPort() != null) ctx.exitPort().port.portId.getText else null)
    foundPaths += pathBuilder.toList
  }

  /**
   * Non parsing-specific behavior
   */
  private var pathBuilder: ArrayBuffer[PathComponent] = _
  private val foundPaths = ArrayBuffer[List[PathComponent]]()

  /**
   * Debug counter
   */
  private var linesParsed = 0

  /**
   * The currently discovered elements
   */
  private val elements = new ArrayBuffer[GenericElement]()

  /**
   * Last element parsed
   */
  private var lastSeen: String = _

  /**
   * Builder of the currently parsed element.
   */
  private var currentElementBuilder: ElementBuilder = _

  private def addElement(element: GenericElement) {
    elements += element
    lastSeen = element.name
  }

  /**
   * Build a config name with prefixed elements.
   * @return
   */
  def buildNetworkConfigWithPrefixes(): Option[NetworkConfig] = configName map { id =>
    new NetworkConfig(configName, elements.map(element => {
      val name = id +"-" + element.name
      element.name = name
      name -> element
    }).toMap,
      foundPaths.map(pcp => {
        pcp.map( cmp => {
          (id + "-" + cmp._1, cmp._2, cmp._3)
        })
      }).toList)
  }

  /**
   * Builds a network config once the parsing is finished
   * @return The network config corresponding to that particular config file.
   */
  def buildNetworkConfig() =
    new NetworkConfig(configName, elements.map(element => (element.name, element)).toMap, foundPaths.toList)

  private def buildElementName(elementName: String): String =
    elementName

  /**
   * Begin building the currently parsed element. Called by callback function.
   * @param name
   * @param elementType
   */
  private def startElement(name: Option[String] = None, elementType: String) {
    currentElementBuilder = name match {
      case Some(nameValue) => BuilderFactory.getBuilder(nameValue, elementType)
      case _ => BuilderFactory.getBuilder(elementType)
    }
  }

  /**
   * At the end of the parsing add the element to the collection
   */
  private def endElement() {
    addElement(currentElementBuilder.buildElement)
  }

  private def stringToPort(port: String): Int = port match {
    case null => 0
    case _:String => Integer.parseInt(port)
  }

  private def addToPath(inputPort: String = null, outputPort: String = null) {
    pathBuilder += ( (lastSeen, stringToPort(inputPort), stringToPort(outputPort)) )
  }
}

case class NetworkConfig(
            id: Option[String],
            elements: Map[String,GenericElement],
            paths: List[List[PathComponent]]) {

  def entryLocationId: String = getFirstSource.get.inputPortName(0)

  def addElement(e: GenericElement): NetworkConfig = NetworkConfig(id, elements + ((e.name, e)), paths)

  def addLink(elementA: String, portA: Int = 0, elementB: String, portB: Int = 0) = {
    elements.get(elementA) match {
      case Some(a) => elements.get(elementB) match {
        case Some(b) => NetworkConfig(id, elements, List((elementA, 0, portA), (elementB, portB, 0)) :: paths)
        case None => this
      }
      case None => this
    }
  }

  def addAfter(sourceName: String, element: GenericElement, sourcePort: Int = 0, destinationPort: Int = 0): NetworkConfig =
    addElement(element).addLink(sourceName, sourcePort, element.name, destinationPort)

  def getFirstSource: Option[GenericElement] = {
    elements.values.find(_ match {case _:FromDevice => true; case _ => false})
  }

  def getFirstDestination: Option[GenericElement] = {
    elements.values.find(_ match {case _:ToDevice => true; case _ => false})
  }

  def linkToSource(element: GenericElement, port: Int = 0): NetworkConfig = {
    getFirstSource match {
      case Some(source) => this.addElement(element).addLink(element.name, port, source.name, 0)
      case None => this
    }
  }
  }
