package generated.reach;// Generated from ReachLang.g by ANTLR 4.3
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ReachLangParser}.
 */
public interface ReachLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ReachLangParser#port}.
	 * @param ctx the parse tree
	 */
	void enterPort(@NotNull ReachLangParser.PortContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#port}.
	 * @param ctx the parse tree
	 */
	void exitPort(@NotNull ReachLangParser.PortContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#ipv4}.
	 * @param ctx the parse tree
	 */
	void enterIpv4(@NotNull ReachLangParser.Ipv4Context ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#ipv4}.
	 * @param ctx the parse tree
	 */
	void exitIpv4(@NotNull ReachLangParser.Ipv4Context ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#range}.
	 * @param ctx the parse tree
	 */
	void enterRange(@NotNull ReachLangParser.RangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#range}.
	 * @param ctx the parse tree
	 */
	void exitRange(@NotNull ReachLangParser.RangeContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#test}.
	 * @param ctx the parse tree
	 */
	void enterTest(@NotNull ReachLangParser.TestContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#test}.
	 * @param ctx the parse tree
	 */
	void exitTest(@NotNull ReachLangParser.TestContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(@NotNull ReachLangParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(@NotNull ReachLangParser.ConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#l4constraint}.
	 * @param ctx the parse tree
	 */
	void enterL4constraint(@NotNull ReachLangParser.L4constraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#l4constraint}.
	 * @param ctx the parse tree
	 */
	void exitL4constraint(@NotNull ReachLangParser.L4constraintContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#mask}.
	 * @param ctx the parse tree
	 */
	void enterMask(@NotNull ReachLangParser.MaskContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#mask}.
	 * @param ctx the parse tree
	 */
	void exitMask(@NotNull ReachLangParser.MaskContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterConstraint(@NotNull ReachLangParser.ConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitConstraint(@NotNull ReachLangParser.ConstraintContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#requirements}.
	 * @param ctx the parse tree
	 */
	void enterRequirements(@NotNull ReachLangParser.RequirementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#requirements}.
	 * @param ctx the parse tree
	 */
	void exitRequirements(@NotNull ReachLangParser.RequirementsContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#middle}.
	 * @param ctx the parse tree
	 */
	void enterMiddle(@NotNull ReachLangParser.MiddleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#middle}.
	 * @param ctx the parse tree
	 */
	void exitMiddle(@NotNull ReachLangParser.MiddleContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#entrance}.
	 * @param ctx the parse tree
	 */
	void enterEntrance(@NotNull ReachLangParser.EntranceContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#entrance}.
	 * @param ctx the parse tree
	 */
	void exitEntrance(@NotNull ReachLangParser.EntranceContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#exit}.
	 * @param ctx the parse tree
	 */
	void enterExit(@NotNull ReachLangParser.ExitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#exit}.
	 * @param ctx the parse tree
	 */
	void exitExit(@NotNull ReachLangParser.ExitContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(@NotNull ReachLangParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(@NotNull ReachLangParser.FieldContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#nport}.
	 * @param ctx the parse tree
	 */
	void enterNport(@NotNull ReachLangParser.NportContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#nport}.
	 * @param ctx the parse tree
	 */
	void exitNport(@NotNull ReachLangParser.NportContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#invariant}.
	 * @param ctx the parse tree
	 */
	void enterInvariant(@NotNull ReachLangParser.InvariantContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#invariant}.
	 * @param ctx the parse tree
	 */
	void exitInvariant(@NotNull ReachLangParser.InvariantContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#trafficdesc}.
	 * @param ctx the parse tree
	 */
	void enterTrafficdesc(@NotNull ReachLangParser.TrafficdescContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#trafficdesc}.
	 * @param ctx the parse tree
	 */
	void exitTrafficdesc(@NotNull ReachLangParser.TrafficdescContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#ipconstraint}.
	 * @param ctx the parse tree
	 */
	void enterIpconstraint(@NotNull ReachLangParser.IpconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#ipconstraint}.
	 * @param ctx the parse tree
	 */
	void exitIpconstraint(@NotNull ReachLangParser.IpconstraintContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#l4field}.
	 * @param ctx the parse tree
	 */
	void enterL4field(@NotNull ReachLangParser.L4fieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#l4field}.
	 * @param ctx the parse tree
	 */
	void exitL4field(@NotNull ReachLangParser.L4fieldContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#protoconstraint}.
	 * @param ctx the parse tree
	 */
	void enterProtoconstraint(@NotNull ReachLangParser.ProtoconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#protoconstraint}.
	 * @param ctx the parse tree
	 */
	void exitProtoconstraint(@NotNull ReachLangParser.ProtoconstraintContext ctx);

	/**
	 * Enter a parse tree produced by {@link ReachLangParser#ipfield}.
	 * @param ctx the parse tree
	 */
	void enterIpfield(@NotNull ReachLangParser.IpfieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReachLangParser#ipfield}.
	 * @param ctx the parse tree
	 */
	void exitIpfield(@NotNull ReachLangParser.IpfieldContext ctx);
}