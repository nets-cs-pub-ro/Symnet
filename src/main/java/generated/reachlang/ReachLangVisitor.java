package generated.reachlang;// Generated from ReachLang.g by ANTLR 4.3
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ReachLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ReachLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ReachLangParser#port}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPort(@NotNull ReachLangParser.PortContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#ipv4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIpv4(@NotNull ReachLangParser.Ipv4Context ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#range}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange(@NotNull ReachLangParser.RangeContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest(@NotNull ReachLangParser.TestContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(@NotNull ReachLangParser.ConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#l4constraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitL4constraint(@NotNull ReachLangParser.L4constraintContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#mask}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMask(@NotNull ReachLangParser.MaskContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#constraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraint(@NotNull ReachLangParser.ConstraintContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#requirements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequirements(@NotNull ReachLangParser.RequirementsContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#middle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMiddle(@NotNull ReachLangParser.MiddleContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#entrance}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntrance(@NotNull ReachLangParser.EntranceContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#exit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExit(@NotNull ReachLangParser.ExitContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(@NotNull ReachLangParser.FieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#nport}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNport(@NotNull ReachLangParser.NportContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#invariant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvariant(@NotNull ReachLangParser.InvariantContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#trafficdesc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrafficdesc(@NotNull ReachLangParser.TrafficdescContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#ipconstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIpconstraint(@NotNull ReachLangParser.IpconstraintContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#l4field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitL4field(@NotNull ReachLangParser.L4fieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#protoconstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProtoconstraint(@NotNull ReachLangParser.ProtoconstraintContext ctx);

	/**
	 * Visit a parse tree produced by {@link ReachLangParser#ipfield}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIpfield(@NotNull ReachLangParser.IpfieldContext ctx);
}