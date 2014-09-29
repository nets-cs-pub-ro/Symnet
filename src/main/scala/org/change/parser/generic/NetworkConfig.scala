package parser.generic

import generated.click.{ClickParser, ClickBaseListener}

import collection.mutable.{ListBuffer, ArrayBuffer}
import ClickParser._
import parser.specific.{ToDevice, FromDevice}
import scala.collection.JavaConversions.collectionAsScalaIterable
import parser.haskellgeneration.HasHaskellRepresentation

class NetworkConfigBuilder(val configName: String) extends ClickBaseListener {

  /**
   * Build the config and output the results.
   **/
  override def exitConfigFile(ctx: ConfigFileContext) {

  }

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
   * Builds a network config once the parsing is finished
   * @return The network config corresponding to that particular config file.
   */
  def buildNetworkConfig() =
    new NetworkConfig(elements.map(element => (element.name, element)).toMap, foundPaths.toList)

  private def buildElementName(elementName: String, configName: String = this.configName): String =
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
            elements: Map[String,GenericElement],
            paths: List[List[PathComponent]])
  extends HasHaskellRepresentation {

  def addElement(e: GenericElement): NetworkConfig = NetworkConfig(elements + ((e.name, e)), paths)

  def addLink(elementA: String, portA: Int = 0, elementB: String, portB: Int = 0) = {
    elements.get(elementA) match {
      case Some(a) => elements.get(elementB) match {
        case Some(b) => NetworkConfig(elements, List((elementA, 0, portA), (elementB, portB, 0)) :: paths)
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

  override def asHaskellWithRuleNumber(startRule: Int = 1) = {

    val links = ListBuffer[String]()
    var currentRule = startRule

    /**
     * Build the link rules between the elements.
     */
    for {
      path <- paths
      link <- path.sliding(2)
      source = link(0)
      destination = link(1)
    } {
      links += s"r$currentRule = " + " wire_rule (\"" + elements(source._1).outputPortName(source._3) + "\", \"out\") (\"" +
        elements(destination._1).inputPortName(destination._2) + "\", \"in\")"
      links += s"l$currentRule = r$currentRule : l${currentRule - 1}"
      currentRule += 1
    }

    var elementsRepr = ListBuffer[String]()

    for { element <- elements.values } {
      val repr = element.asHaskell(currentRule)
      elementsRepr += repr._1
      currentRule += repr._2
    }

    (links.mkString("\n") + "\n" + elementsRepr.mkString("\n"), currentRule - startRule)
  }

}
