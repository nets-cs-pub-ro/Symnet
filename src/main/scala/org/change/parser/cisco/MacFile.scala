package org.change.parser.cisco

/**
 * Created by radu on 08.01.2015.
 */
object MacFile {

  def parse(file: String): List[(String, String, String)] =
    scala.io.Source.fromFile(file).getLines().map(_.trim.split("\\s+")).filter(_.length > 3).map{ l =>
        (l(0), l(1), l(3))
      }.toList

}
