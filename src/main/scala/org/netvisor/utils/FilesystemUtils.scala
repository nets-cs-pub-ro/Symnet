package utils

import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 1/24/14
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
object FilesystemUtils {
  /**
   * Returns a list of all the files found in the provided dirs.
   *
   * It goes one level deep only.
   *
   * @param dirList
   * @return
   */
  def allFilesFromDirs(dirList: List[String]): List[File] = dirList.flatMap(dirPath => {
    val dir = new File(dirPath)
    if (dir.isDirectory)
      dir.listFiles().toList
    else
      Nil
  })
}
