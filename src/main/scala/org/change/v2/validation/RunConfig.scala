package org.change.v2.validation

import org.change.v2.analysis.memory.{TagExp, State}
import org.change.v2.util.canonicalnames._
import org.change.v2.util.conversion.RepresentationConversion

/**
 * A small gift from radu to symnetic.
 */
object RunConfig {

  def exampleFor(t: TagExp, s: State): Int = {
    s.memory.exampleFor(s.memory.evalToObject(t(s).get).get.initialValue.get) match {
      case Some(i) => i
      case _ => -1
    }
  }

  def mateiInputFlowFromState(s: State): String =
    s"{type=tcp, SourceMAC=DEFAULT, SourceIP=${RepresentationConversion.numberToIP(exampleFor(IPSrc, s))} ," +
      s"SourcePort=${exampleFor(TcpSrc,s)} ,DestMAC=DEFAULT, " +
      s"DestIP=${RepresentationConversion.numberToIP(exampleFor(IPDst, s))} , DestPort=${exampleFor(TcpDst,s)} , SeqNo=0,AckNo=0,Flags=16}"

  def getMateiOutput(mateiInput: String): Option[Map[TagExp, Long]] = {
    val cmd = Seq("./script.sh",  mateiInput)
//      "ssh radu@gaina.cs.pub.ro \"./run_matei.sh " +
//      "'" + mateiInput + "'\""

    println("Issuing: " + cmd)
    import scala.sys.process._
    val cmdOutput = cmd.!!.trim
    println("Got:\n" + cmdOutput)

    if (cmdOutput.matches("\\{.+\\}"))
      Some(mateiOutputToValidationCase(cmdOutput))
    else
      None
  }

  def mateiOutputToValidationCase(output: String): Map[TagExp, Long] = {
    output.stripPrefix("{").stripSuffix("}").split(",").map(_.trim).map({pair =>
      val split = pair.split("=")
      val key = split(0)
      val value = split(1)
      (key, value)
    }).filter({kv => conversion.contains(kv._1)}).map({kv =>
      (conversion(kv._1), if (kv._1.contains("IP")) RepresentationConversion.ipToNumber(kv._2) else kv._2.toLong)
    }).toMap
  }

  private val conversion = Map(
    "SourceIP" -> IPSrc,
    "DestIP" -> IPDst,
    "SourcePort" -> TcpSrc,
    "DestPort" -> TcpDst
  )

  import java.util.Date

  object ShellJob {
    def run(cmd: String): (String, Date, Date) = {
      val start = new Date ()

      import scala.sys.process._
      val output = cmd.!!

      val stop = new Date ()
      (output, start, stop)
    }
  }
}
