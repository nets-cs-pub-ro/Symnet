package org.change.symbolicexec.blocks.click

import org.change.symbolicexec._
import org.change.symbolicexec.blocks.{NoopProcessingBlock, ProcessingBlock}
import org.change.utils.RepresentationConversion
import parser.generic.ConfigParameter

class IPRewriterBlock(id: String, params: List[ConfigParameter]) extends
//  Warning: We need to be a little bit more clever about this.
  NoopProcessingBlock(id, params.length, 1) {

  private val patterns = params.map(cf => IPRewriterBlock.parsePattern(cf.value))

  private val prefix = s"IPRW-$id-"

  override def process(p: Path): List[Path] = {
    val port = p.locationPort
    val state = IPRewriterBlock.suffixes.map(prefix + port + "-" + _)

//    If known flow
    List((if (! state.filter(p.memory.exists(_)).isEmpty) {
      p.modifyWith( m => {
        state.zip(IPRewriterBlock.suffixes).foldLeft(m) { (m, sp) =>
          m.resolveToCurrent(sp._1) match {
            case Some(v) => m.rewrite(sp._2, v).removeSymbol(sp._1)
            case None => m
          }
        }
      })
    } else {
      p.modifyWith( m => {
        val (sip, sport, dip, dport) = patterns(port)
        val m1 = sip match {
          case None => m
          case Some(v) => {
            val old = m.resolveToCurrent("IP-Src")
            val mm = m.newVal("IP-Src")
             .constrainCurrent("IP-Src", E(v))
            old match {
              case Some(prev) => mm.newVal(state(0), prev)
              case None => mm
            }
          }
        }

        val m2 = sport match {
          case None => m1
          case Some((v1,v2)) => {
            val old = m1.resolveToCurrent("Port-Src")
            val mm = m1.newVal("Port-Src")
              .constrainCurrent("Port-Src", Range(v1, v2))
            old match {
              case Some(prev) => mm.newVal(state(1), prev)
              case None => mm
            }
          }
        }

        val m3 = dip match {
          case None => m2
          case Some(v) => {
            val old = m2.resolveToCurrent("IP-Dst")
            val mm = m2.newVal("IP-Dst")
              .constrainCurrent("IP-Dst", E(v))
            old match {
              case Some(prev) => mm.newVal(state(2), prev)
              case None => mm
            }
          }
        }

        val m4 = dport match {
          case None => m3
          case Some((v1,v2)) => {
            val old = m3.resolveToCurrent("Port-Dst")
            val mm = m3.newVal("Port-Dst")
              .constrainCurrent("Port-Dst", Range(v1, v2))
            old match {
              case Some(prev) => mm.newVal(state(3), prev)
              case None => mm
            }
          }
        }

        m4
      })
    }).move(PathLocation(p.location.vmId, id, 0, Output)))
  }

}

object IPRewriterBlock {

  val tcpRange = """(\d+)\-(\d+)""".r

  def parsePattern(p: String): (Option[Long], Option[(Long, Long)], Option[Long], Option[(Long, Long)]) = {
    val groups = p.split("\\s+")
    (groups(1) match {
        case some if some.equals("-") => None
        case _ => Some(RepresentationConversion.ipToNumber(groups(1)))
      },
      groups(2) match {
        case some if some.equals("-") => None
        case tcpRange(a,b) => Some((java.lang.Long.parseLong(a), java.lang.Long.parseLong(b)))
        case _ => Some((java.lang.Long.parseLong(groups(2)), java.lang.Long.parseLong(groups(2))))
      },
      groups(3) match {
        case some if some.equals("-") => None
        case _ => Some(RepresentationConversion.ipToNumber(groups(3)))
      },
      groups(4) match {
        case some if some.equals("-") => None
        case tcpRange(a,b) => Some((java.lang.Long.parseLong(a), java.lang.Long.parseLong(b)))
        case _ => Some((java.lang.Long.parseLong(groups(4)), java.lang.Long.parseLong(groups(4))))
      })
  }

  val suffixes = List("IP-Src", "Port-Src", "IP-Dst", "Port-Dst")

}
