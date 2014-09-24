package org.change.symbolicexec.networkgraph

import parser.generic._
import parser.specific.FromDevice

/**
 * Every node has a processing block, referred by elementId and
 * a set of transitions exiting it. These transitions are defined by
 *  1. The source port
 *  2. The destination node
 *  3, The destination port
 *
 * For every source port, there should be only one transition. (i.e no
 * source port should link two  different nodes - ambiguity risk)
 * @param elementId
 * @param eLinks
 */
case class NetworkNode(var elementId: String, var eLinks: scala.collection.mutable.Map[Int, (NetworkNode, Int)] = scala.collection.mutable.Map()) {

  def findFirstOccurrence(id: String): Option[NetworkNode] =
    if (elementId equals id) Some(this)
    else eLinks.values.map(_._1.findFirstOccurrence(id)).find(_ match {case Some(n) => true}).getOrElse(None)

  def linkNode(n : NetworkNode): Boolean = findFirstOccurrence(n.elementId) match {
    case Some(nn) => {
//      Merge links
      nn.eLinks = nn.eLinks ++ n.eLinks
      true
    }
    case None => false
  }

  override def toString = elementId + eLinks.map( kv => s"[${kv._1}] -> [${kv._2._2}] ${kv._2._1.toString}").mkString("\n")
}

object NetworkNode {

  type Path = List[PathComponent]
  def buildFromParsedModel(parsedNetworkModel: NetworkConfig, whatRoot: (NetworkNode) => Boolean): NetworkNode = {

    val paths = parsedNetworkModel.paths.map(buildFromPath(_))

    import org.change.utils.collection.extractFirst
    val (root, others) = extractFirst(paths)(whatRoot)

    others.map(root.linkNode(_))

    root
  }

  def treeRootedAtSource = { model: NetworkConfig =>
    buildFromParsedModel(model, ( e =>
        model.elements(e.elementId) match {
          case _:FromDevice => true
          case _ => false
        }
      )
    )
  }

  def treeRootedAt(rootElementId: String) = { model: NetworkConfig =>
    buildFromParsedModel(model, (e => e.elementId == rootElementId))
  }

  private def buildFromPath(path: List[PathComponent]): NetworkNode = {
    val nodes = path.map(c => NetworkNode(c._1))
    for {
      (link, nodes) <- path.sliding(2).toIterable.zip(nodes.sliding(2).toIterable)
      sourceNode = nodes.head
      destNode = nodes.last
      sourcePort = link.head._3
      destinationPort = link.last._2
    } {
      sourceNode.eLinks += ((sourcePort, (destNode, destinationPort)))
    }
    nodes.head
  }
}


