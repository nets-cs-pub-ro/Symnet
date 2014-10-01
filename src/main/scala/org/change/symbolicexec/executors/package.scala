package org.change.symbolicexec

import org.change.symbolicexec.verifiablemodel.NetworkNode
import parser.generic.NetworkConfig

package object executors {

  type ExecutableModel = scala.collection.mutable.Map[(String, String), NetworkNode]

}
