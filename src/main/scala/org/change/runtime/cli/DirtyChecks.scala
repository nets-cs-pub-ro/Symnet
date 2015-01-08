package org.change.runtime.cli

/**
 * Created by radu on 08.01.2015.
 */
object DirtyChecks {

  def main (args: Array[String]) {
    import org.change.parser.cisco.MacFile
    import org.change.symbolicexec.blocks.cisco.Switch
    import org.change.symbolicexec.{PathLocation, Input, Path}

    val parse = MacFile.parse("/home/radu/0/projects/internal/diacritic/cisco_files/switch/mac/Mac-SW-C2.txt")
    val sw = Switch("a", parse, List("Fa0/24", "Fa0/13"))

    val p = Path.cleanWithCanonical(PathLocation("0", "a", 0, Input))


    println(sw.process(p))
  }

}
