package generated.click;// Generated from Click.g by ANTLR 4.1
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ClickParser}.
 */
public interface ClickListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ClickParser#port}.
	 * @param ctx the parse tree
	 */
	void enterPort(@NotNull ClickParser.PortContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#port}.
	 * @param ctx the parse tree
	 */
	void exitPort(@NotNull ClickParser.PortContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#newElement}.
	 * @param ctx the parse tree
	 */
	void enterNewElement(@NotNull ClickParser.NewElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#newElement}.
	 * @param ctx the parse tree
	 */
	void exitNewElement(@NotNull ClickParser.NewElementContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#elementName}.
	 * @param ctx the parse tree
	 */
	void enterElementName(@NotNull ClickParser.ElementNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#elementName}.
	 * @param ctx the parse tree
	 */
	void exitElementName(@NotNull ClickParser.ElementNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#exitPort}.
	 * @param ctx the parse tree
	 */
	void enterExitPort(@NotNull ClickParser.ExitPortContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#exitPort}.
	 * @param ctx the parse tree
	 */
	void exitExitPort(@NotNull ClickParser.ExitPortContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(@NotNull ClickParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(@NotNull ClickParser.LineContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#elementInstance}.
	 * @param ctx the parse tree
	 */
	void enterElementInstance(@NotNull ClickParser.ElementInstanceContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#elementInstance}.
	 * @param ctx the parse tree
	 */
	void exitElementInstance(@NotNull ClickParser.ElementInstanceContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#configParameter}.
	 * @param ctx the parse tree
	 */
	void enterConfigParameter(@NotNull ClickParser.ConfigParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#configParameter}.
	 * @param ctx the parse tree
	 */
	void exitConfigParameter(@NotNull ClickParser.ConfigParameterContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#configFile}.
	 * @param ctx the parse tree
	 */
	void enterConfigFile(@NotNull ClickParser.ConfigFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#configFile}.
	 * @param ctx the parse tree
	 */
	void exitConfigFile(@NotNull ClickParser.ConfigFileContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#inTheMiddle}.
	 * @param ctx the parse tree
	 */
	void enterInTheMiddle(@NotNull ClickParser.InTheMiddleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#inTheMiddle}.
	 * @param ctx the parse tree
	 */
	void exitInTheMiddle(@NotNull ClickParser.InTheMiddleContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#endOfPath}.
	 * @param ctx the parse tree
	 */
	void enterEndOfPath(@NotNull ClickParser.EndOfPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#endOfPath}.
	 * @param ctx the parse tree
	 */
	void exitEndOfPath(@NotNull ClickParser.EndOfPathContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#component}.
	 * @param ctx the parse tree
	 */
	void enterComponent(@NotNull ClickParser.ComponentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#component}.
	 * @param ctx the parse tree
	 */
	void exitComponent(@NotNull ClickParser.ComponentContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(@NotNull ClickParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(@NotNull ClickParser.PathContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#config}.
	 * @param ctx the parse tree
	 */
	void enterConfig(@NotNull ClickParser.ConfigContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#config}.
	 * @param ctx the parse tree
	 */
	void exitConfig(@NotNull ClickParser.ConfigContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#pathElement}.
	 * @param ctx the parse tree
	 */
	void enterPathElement(@NotNull ClickParser.PathElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#pathElement}.
	 * @param ctx the parse tree
	 */
	void exitPathElement(@NotNull ClickParser.PathElementContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(@NotNull ClickParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(@NotNull ClickParser.ClassNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#startOfPath}.
	 * @param ctx the parse tree
	 */
	void enterStartOfPath(@NotNull ClickParser.StartOfPathContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#startOfPath}.
	 * @param ctx the parse tree
	 */
	void exitStartOfPath(@NotNull ClickParser.StartOfPathContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#portId}.
	 * @param ctx the parse tree
	 */
	void enterPortId(@NotNull ClickParser.PortIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#portId}.
	 * @param ctx the parse tree
	 */
	void exitPortId(@NotNull ClickParser.PortIdContext ctx);

	/**
	 * Enter a parse tree produced by {@link ClickParser#entryPort}.
	 * @param ctx the parse tree
	 */
	void enterEntryPort(@NotNull ClickParser.EntryPortContext ctx);
	/**
	 * Exit a parse tree produced by {@link ClickParser#entryPort}.
	 * @param ctx the parse tree
	 */
	void exitEntryPort(@NotNull ClickParser.EntryPortContext ctx);
}