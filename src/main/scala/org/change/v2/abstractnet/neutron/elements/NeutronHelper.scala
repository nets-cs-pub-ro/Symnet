package org.change.v2.abstractnet.neutron.elements

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.FileInputStream
import java.io.BufferedInputStream
import org.change.v2.abstractnet.neutron.NeutronWrapper

object NeutronHelper {
    def readCredentials(file : String = "credentials.txt") = {
      val breader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))))
      val apiAddr = breader.readLine()
      val userName = breader.readLine()
      val password = breader.readLine()
      val project = breader.readLine()
      breader.close()
      (apiAddr, userName, password, project)
    }
    
    def neutronWrapperFromFile(file : String = "credentials.txt") = {
       val (apiAddr, userName, password, project) = NeutronHelper.readCredentials(file)
    
      new NeutronWrapper(apiAddr,
          userName,
          password,
          project)
    }
  
}