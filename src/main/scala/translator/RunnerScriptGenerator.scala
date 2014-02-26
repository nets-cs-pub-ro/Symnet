package translator

import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: radu
 * Date: 1/24/14
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
object RunnerScriptGenerator {

  val header = "#!/bin/bash\n"

  val vmRunner = "~vlad/pms/clickos/start_vm.py"
  val connectionScript = "~vlad/pms/ovs_redirect/ovs-redirect.sh"

  def buildInitScript(providerConfigFiles: List[File], clientConfigFiles: List[File],
    staticLinks: List[String], dynamicLinks: List[String]): String = {

    val provider = providerConfigFiles.map(_.getAbsolutePath()).map((path:String)=> vmRunner + " " + path).mkString("\n")
    val client = clientConfigFiles.map(_.getAbsolutePath()).map((path:String)=> vmRunner + " " + path).mkString("\n")

    header + "\n" + provider + "\n" + client
  }

}
