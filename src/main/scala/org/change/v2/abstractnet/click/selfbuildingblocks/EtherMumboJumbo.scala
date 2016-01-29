package org.change.v2.abstractnet.click.selfbuildingblocks

import org.change.v2.analysis.expression.concrete.{SymbolicValue, ConstantValue}
import org.change.v2.analysis.expression.concrete.nonprimitive.:@
import org.change.v2.analysis.memory.Tag
import org.change.v2.analysis.processingmodels.Instruction
import org.change.v2.analysis.processingmodels.instructions._
import org.change.v2.util.canonicalnames._

/**
 * A small gift from radu to symnetic.
 */
object EtherMumboJumbo {

  lazy val stripAllEther = If(Constrain(EtherType, :==:(ConstantValue(EtherProtoVLAN))),
      InstructionBlock(
        Deallocate(EtherSrc, 48),
        Deallocate(EtherDst, 48),
        Deallocate(EtherType, 16),
        Deallocate(PCP,3),
        Deallocate(DEI,1),
        Deallocate(VLANTag,12),
        CreateTag("L2",Tag("L2")+32),
        Deallocate(EtherType,16),
        CreateTag("L3",Tag("L2") + 112),
        DestroyTag("L2")
    ),
    InstructionBlock(
      Constrain(Tag("L2")+EtherTypeOffset,:==:(ConstantValue(EtherProtoIP))),
      //set eth addr anno
      CreateTag("L3",Tag("L2") + 112),
      Deallocate(Tag("L2")+EtherSrcOffset, 48),
      Deallocate(Tag("L2")+EtherDstOffset, 48),
      Deallocate(Tag("L2")+EtherTypeOffset,16),
      DestroyTag("L2")
    )
  )

  lazy val symbolicEtherEncap = InstructionBlock(
    CreateTag("L2",Tag("L3")-112),
    Allocate(Tag("L2")+EtherSrcOffset,48),
    Assign(Tag("L2")+EtherSrcOffset,SymbolicValue()),
    Allocate(Tag("L2")+EtherDstOffset,48),
    Assign(Tag("L2")+EtherDstOffset,SymbolicValue()),
    Allocate(Tag("L2")+EtherTypeOffset,16),
    Assign(Tag("L2")+EtherTypeOffset,ConstantValue(EtherProtoIP))
  )

  lazy val symbolicVlanEncap = InstructionBlock(
    CreateTag("L2",Tag("L3")-144),
    Allocate(Tag("L2")+EtherSrcOffset,48),
    Assign(Tag("L2")+EtherSrcOffset,SymbolicValue()),
    Allocate(Tag("L2")+EtherDstOffset,48),
    Assign(Tag("L2")+EtherDstOffset,SymbolicValue()),
    Allocate(Tag("L2")+EtherTypeOffset,16),
    Assign(Tag("L2")+EtherTypeOffset,ConstantValue(EtherProtoVLAN)),
    Allocate(PCP,3),
    Assign(PCP,ConstantValue(0)),
    Allocate(DEI,1),
    Assign(DEI,ConstantValue(0)),
    Allocate(VLANTag,12),
    Assign(VLANTag,SymbolicValue()),
    Allocate(Tag("L2")+EtherTypeOffset + 32,16),
    Assign(Tag("L2")+EtherTypeOffset + 32,ConstantValue(EtherProtoIP))

  )

  def constantVlanEncap(vlanTag: Int) = InstructionBlock(
    CreateTag("L2",Tag("L3")-144),
    Allocate(Tag("L2")+EtherSrcOffset,48),
    Assign(Tag("L2")+EtherSrcOffset,SymbolicValue()),
    Allocate(Tag("L2")+EtherDstOffset,48),
    Assign(Tag("L2")+EtherDstOffset,SymbolicValue()),
    Allocate(Tag("L2")+EtherTypeOffset,16),
    Assign(Tag("L2")+EtherTypeOffset,ConstantValue(EtherProtoVLAN)),
    Allocate(PCP,3),
    Assign(PCP,ConstantValue(0)),
    Allocate(DEI,1),
    Assign(DEI,ConstantValue(0)),
    Allocate(VLANTag,12),
    Assign(VLANTag,ConstantValue(vlanTag)),
    Allocate(Tag("L2")+EtherTypeOffset + 32,16),
    Assign(Tag("L2")+EtherTypeOffset + 32,ConstantValue(EtherProtoIP))
  )
}
