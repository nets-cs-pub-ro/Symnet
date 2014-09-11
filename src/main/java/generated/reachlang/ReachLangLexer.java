package generated.reachlang;// Generated from ReachLang.g by ANTLR 4.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ReachLangLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__13=1, T__12=2, T__11=3, T__10=4, T__9=5, T__8=6, T__7=7, T__6=8, T__5=9, 
		T__4=10, T__3=11, T__2=12, T__1=13, T__0=14, NUMBER=15, CLIENT=16, INTERNET=17, 
		ID=18, ARROW=19, WS=20;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'.'", "'udp'", "'reach from'", "'-'", "':'", "'src port'", "'tcp'", "'src'", 
		"';'", "'&&'", "'proto'", "'dst port'", "'dst'", "'/'", "NUMBER", "'client'", 
		"'internet'", "ID", "'->'", "WS"
	};
	public static final String[] ruleNames = {
		"T__13", "T__12", "T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", 
		"T__4", "T__3", "T__2", "T__1", "T__0", "NUMBER", "CLIENT", "INTERNET", 
		"ID", "ARROW", "WS"
	};


	public ReachLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ReachLang.g"; }

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
		case 19: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\26\u008f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\17\3\17\3\20\6\20m\n\20\r\20\16\20n\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\6\23\u0082"+
		"\n\23\r\23\16\23\u0083\3\24\3\24\3\24\3\25\6\25\u008a\n\25\r\25\16\25"+
		"\u008b\3\25\3\25\2\26\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n"+
		"\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%"+
		"\24\1\'\25\1)\26\2\3\2\5\3\2\62;\6\2//C\\aac|\5\2\13\13\17\17\"\"\u0091"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\3+\3\2\2\2\5-\3\2\2\2\7\61\3\2"+
		"\2\2\t<\3\2\2\2\13>\3\2\2\2\r@\3\2\2\2\17I\3\2\2\2\21M\3\2\2\2\23Q\3\2"+
		"\2\2\25S\3\2\2\2\27V\3\2\2\2\31\\\3\2\2\2\33e\3\2\2\2\35i\3\2\2\2\37l"+
		"\3\2\2\2!p\3\2\2\2#w\3\2\2\2%\u0081\3\2\2\2\'\u0085\3\2\2\2)\u0089\3\2"+
		"\2\2+,\7\60\2\2,\4\3\2\2\2-.\7w\2\2./\7f\2\2/\60\7r\2\2\60\6\3\2\2\2\61"+
		"\62\7t\2\2\62\63\7g\2\2\63\64\7c\2\2\64\65\7e\2\2\65\66\7j\2\2\66\67\7"+
		"\"\2\2\678\7h\2\289\7t\2\29:\7q\2\2:;\7o\2\2;\b\3\2\2\2<=\7/\2\2=\n\3"+
		"\2\2\2>?\7<\2\2?\f\3\2\2\2@A\7u\2\2AB\7t\2\2BC\7e\2\2CD\7\"\2\2DE\7r\2"+
		"\2EF\7q\2\2FG\7t\2\2GH\7v\2\2H\16\3\2\2\2IJ\7v\2\2JK\7e\2\2KL\7r\2\2L"+
		"\20\3\2\2\2MN\7u\2\2NO\7t\2\2OP\7e\2\2P\22\3\2\2\2QR\7=\2\2R\24\3\2\2"+
		"\2ST\7(\2\2TU\7(\2\2U\26\3\2\2\2VW\7r\2\2WX\7t\2\2XY\7q\2\2YZ\7v\2\2Z"+
		"[\7q\2\2[\30\3\2\2\2\\]\7f\2\2]^\7u\2\2^_\7v\2\2_`\7\"\2\2`a\7r\2\2ab"+
		"\7q\2\2bc\7t\2\2cd\7v\2\2d\32\3\2\2\2ef\7f\2\2fg\7u\2\2gh\7v\2\2h\34\3"+
		"\2\2\2ij\7\61\2\2j\36\3\2\2\2km\t\2\2\2lk\3\2\2\2mn\3\2\2\2nl\3\2\2\2"+
		"no\3\2\2\2o \3\2\2\2pq\7e\2\2qr\7n\2\2rs\7k\2\2st\7g\2\2tu\7p\2\2uv\7"+
		"v\2\2v\"\3\2\2\2wx\7k\2\2xy\7p\2\2yz\7v\2\2z{\7g\2\2{|\7t\2\2|}\7p\2\2"+
		"}~\7g\2\2~\177\7v\2\2\177$\3\2\2\2\u0080\u0082\t\3\2\2\u0081\u0080\3\2"+
		"\2\2\u0082\u0083\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"&\3\2\2\2\u0085\u0086\7/\2\2\u0086\u0087\7@\2\2\u0087(\3\2\2\2\u0088\u008a"+
		"\t\4\2\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u0089\3\2\2\2\u008b"+
		"\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\b\25\2\2\u008e*\3\2\2\2"+
		"\6\2n\u0083\u008b";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}