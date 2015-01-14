package org.change.runtime.cli

/**
 * Created by radu on 08.01.2015.
 */
object DirtyChecks {

  def main (args: Array[String]) {
    import org.change.parser.cisco.MacFile
    import org.change.symbolicexec.blocks.cisco.Switch
    import org.change.symbolicexec.{PathLocation, Input, Path}

    val startOfDay = System.currentTimeMillis()

    val parse = MacFile.parse("/home/radu/0/projects/internal/Diactitic/cisco_files/switch/mac/sw-Aut-CAM.txt")
    val sw = Switch("a", parse, None)

    val start = System.currentTimeMillis()

    val p = Path.cleanWithCanonical(PathLocation("0", "a", 0, Input))
    println(sw.process(p).filter(_.valid).length)
    val stop = System.currentTimeMillis()

    println((stop - start) + " " + (stop - startOfDay))
  }

}
