package generated.click;// Generated from Click.g by ANTLR 4.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ClickLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, CONJUNCTION=7, NON_CAPITALIZED_STRING=8, 
		CAPITALIZED_STRING=9, DASH=10, NUMERIC_CONF_PARAM=11, ARROW=12, NUMBER=13, 
		ANY_STRING=14, DEFINE_SYMBOL=15, NEWLINE=16, WS=17;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "')'", "','", "'['", "'('", "';'", "'&&'", "NON_CAPITALIZED_STRING", 
		"CAPITALIZED_STRING", "'-'", "NUMERIC_CONF_PARAM", "ARROW", "NUMBER", 
		"ANY_STRING", "'::'", "'\n'", "WS"
	};
	public static final String[] ruleNames = {
		"T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "CONJUNCTION", "NON_CAPITALIZED_STRING", 
		"CAPITALIZED_STRING", "DASH", "NUMERIC_CONF_PARAM", "ARROW", "NUMBER", 
		"ANY_STRING", "DEFINE_SYMBOL", "NEWLINE", "WS"
	};


	public ClickLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Click.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 16: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\23o\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\7"+
		"\t\67\n\t\f\t\16\t:\13\t\3\n\3\n\7\n>\n\n\f\n\16\nA\13\n\3\13\3\13\3\f"+
		"\6\fF\n\f\r\f\16\fG\3\f\3\f\6\fL\n\f\r\f\16\fM\6\fP\n\f\r\f\16\fQ\3\r"+
		"\3\r\3\r\3\r\5\rX\n\r\3\16\6\16[\n\16\r\16\16\16\\\3\17\6\17`\n\17\r\17"+
		"\16\17a\3\20\3\20\3\20\3\21\3\21\3\22\6\22j\n\22\r\22\16\22k\3\22\3\22"+
		"\2\23\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f"+
		"\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\2\3\2\n\3\2c|\7\2"+
		"/<B\\^^aac|\3\2C\\\5\2\62;CHch\6\2\60\61<<ZZzz\3\2\62;\7\2\60;B\\^^aa"+
		"c|\5\2\13\13\17\17\"\"w\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2\2\7)\3\2\2\2\t+\3\2\2"+
		"\2\13-\3\2\2\2\r/\3\2\2\2\17\61\3\2\2\2\21\64\3\2\2\2\23;\3\2\2\2\25B"+
		"\3\2\2\2\27E\3\2\2\2\31W\3\2\2\2\33Z\3\2\2\2\35_\3\2\2\2\37c\3\2\2\2!"+
		"f\3\2\2\2#i\3\2\2\2%&\7_\2\2&\4\3\2\2\2\'(\7+\2\2(\6\3\2\2\2)*\7.\2\2"+
		"*\b\3\2\2\2+,\7]\2\2,\n\3\2\2\2-.\7*\2\2.\f\3\2\2\2/\60\7=\2\2\60\16\3"+
		"\2\2\2\61\62\7(\2\2\62\63\7(\2\2\63\20\3\2\2\2\648\t\2\2\2\65\67\t\3\2"+
		"\2\66\65\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29\22\3\2\2\2:8\3\2\2"+
		"\2;?\t\4\2\2<>\t\3\2\2=<\3\2\2\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2\2@\24\3\2"+
		"\2\2A?\3\2\2\2BC\7/\2\2C\26\3\2\2\2DF\t\5\2\2ED\3\2\2\2FG\3\2\2\2GE\3"+
		"\2\2\2GH\3\2\2\2HO\3\2\2\2IK\t\6\2\2JL\t\5\2\2KJ\3\2\2\2LM\3\2\2\2MK\3"+
		"\2\2\2MN\3\2\2\2NP\3\2\2\2OI\3\2\2\2PQ\3\2\2\2QO\3\2\2\2QR\3\2\2\2R\30"+
		"\3\2\2\2ST\7/\2\2TX\7@\2\2UV\7?\2\2VX\7@\2\2WS\3\2\2\2WU\3\2\2\2X\32\3"+
		"\2\2\2Y[\t\7\2\2ZY\3\2\2\2[\\\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]\34\3\2\2"+
		"\2^`\t\b\2\2_^\3\2\2\2`a\3\2\2\2a_\3\2\2\2ab\3\2\2\2b\36\3\2\2\2cd\7<"+
		"\2\2de\7<\2\2e \3\2\2\2fg\7\f\2\2g\"\3\2\2\2hj\t\t\2\2ih\3\2\2\2jk\3\2"+
		"\2\2ki\3\2\2\2kl\3\2\2\2lm\3\2\2\2mn\b\22\2\2n$\3\2\2\2\f\28?GMQW\\ak";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}