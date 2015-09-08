package org.change.symbolicexec.verification

import org.change.v2.analysis.processingmodels.instructions.ConstrainNamedSymbol
import org.change.v2.executor.clickabstractnetwork.verificator.PathLocation

/**
 * A rule defines a set of conditions imposed on a flow in a point of the network..
 * @param where the output port where the rule is checked
 * @param whatTraffic the constraints on traffic
 * @param whatInvariants the invariants
 */
case class Rule(where: PathLocation,
                whatTraffic: List[ConstrainNamedSymbol] = Nil,
                whatInvariants: Option[List[String]] = None)