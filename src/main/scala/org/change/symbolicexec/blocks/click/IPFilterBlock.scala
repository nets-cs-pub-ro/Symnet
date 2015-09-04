package org.change.symbolicexec.blocks.click

import org.change.symbolicexec._
import org.change.utils.{NumberFor, RepresentationConversion}
import org.change.v2.abstractnet.generic.ConfigParameter

class IPFilterBlock(id: String, params: List[ConfigParameter]) {

//  Extract filtering rules
  private var rules = params.map(IPFilterBlock.configParamToConstraints(_))

  if (rules.last.isEmpty) {
//    If dash found, then do sth with it
//    TODO: Extend this to other params.
    rules = rules.init ++ List(rules.init.map( cs => {
      cs.map( c => {
        (c._1, NOT(c._2))
      })
    }).flatten)
  }
}

object IPFilterBlock {
  private val tcp = "tcp|TCP".r
  private val udp = "udp|UDP".r
  private val port1 = """(dst|src)\W+port\W+(\d+)""".r
  private val ip1 = """(dst|src)\W+(\d+\.\d+\.\d+\.\d+)""".r
  private val port2 = """(dst|src)\W+port\W+(\d+)-(\d+)""".r
  private val ip2 = """(dst|src)\W+(\d+\.\d+\.\d+\.\d+)/(\d+)""".r

  private def wherePort(s: String) = s match {
    case "src" => "Port-Src"
    case "dst" => "Port-Dst"
    case _ => s
  }

  private def whereIP(s: String) = s match {
    case "src" => "IP-Src"
    case "dst" => "IP-Dst"
    case _ => s
  }

  private def tcpdumpToConstraint(s: String): (Symbol, Constraint) = s match {
    case udp() => ("Proto", E(NumberFor("udp")))
    case tcp() => ("Proto", E(NumberFor("tcp")))
    case port1(where, what) => (wherePort(where), E(java.lang.Long.parseLong(what)))
    case ip1(where, what) => (whereIP(where), E(RepresentationConversion.ipToNumber(what)))
    case ip2(where, whatIp, whatMask) => {
      val range = RepresentationConversion.ipAndMaskToInterval(whatIp, whatMask)
      (whereIP(where), Range(range._1, range._2))
    }
    case port2(where, what1, what2) => (wherePort(where), Range(java.lang.Long.parseLong(what1),
      java.lang.Long.parseLong(what2)))
  }

  private def configParamToConstraints(cp: ConfigParameter): List[(Symbol, Constraint)] = {
//    The dash is a really shitty case we should be aware.
    if (cp.value.trim.matches("-")) Nil
    else {
      val groups = cp.value.split("\\W+", 2)
      val ruleType = groups(0)
      val cs = groups(1).split("&&").map(_.trim).map(tcpdumpToConstraint(_)).toList
      ruleType.trim.toLowerCase match {
        case "deny" => cs.map(p => (p._1, NOT(p._2)))
        case _ => cs
      }
    }
  }
}
