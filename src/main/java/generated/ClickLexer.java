// Generated from Click.g by ANTLR 4.1
package generated;

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
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, NON_CAPITALIZED_STRING=7, 
		CAPITALIZED_STRING=8, DASH=9, NUMERIC_CONF_PARAM=10, ARROW=11, NUMBER=12, 
		ANY_STRING=13, DEFINE_SYMBOL=14, NEWLINE=15, WS=16;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "')'", "','", "'['", "'('", "';'", "NON_CAPITALIZED_STRING", "CAPITALIZED_STRING", 
		"'-'", "NUMERIC_CONF_PARAM", "ARROW", "NUMBER", "ANY_STRING", "'::'", 
		"'\n'", "WS"
	};
	public static final String[] ruleNames = {
		"T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "NON_CAPITALIZED_STRING", 
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
		case 15: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\22j\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\7\b\62\n\b\f\b\16\b\65"+
		"\13\b\3\t\3\t\7\t9\n\t\f\t\16\t<\13\t\3\n\3\n\3\13\6\13A\n\13\r\13\16"+
		"\13B\3\13\3\13\6\13G\n\13\r\13\16\13H\6\13K\n\13\r\13\16\13L\3\f\3\f\3"+
		"\f\3\f\5\fS\n\f\3\r\6\rV\n\r\r\r\16\rW\3\16\6\16[\n\16\r\16\16\16\\\3"+
		"\17\3\17\3\17\3\20\3\20\3\21\6\21e\n\21\r\21\16\21f\3\21\3\21\2\22\3\3"+
		"\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1"+
		"\31\16\1\33\17\1\35\20\1\37\21\1!\22\2\3\2\b\3\2c|\7\2//\61;B\\aac|\3"+
		"\2C\\\3\2\62;\7\2\60;B\\^^aac|\4\2\13\13\"\"r\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\3#\3\2\2\2\5%\3\2\2\2\7\'\3\2"+
		"\2\2\t)\3\2\2\2\13+\3\2\2\2\r-\3\2\2\2\17/\3\2\2\2\21\66\3\2\2\2\23=\3"+
		"\2\2\2\25@\3\2\2\2\27R\3\2\2\2\31U\3\2\2\2\33Z\3\2\2\2\35^\3\2\2\2\37"+
		"a\3\2\2\2!d\3\2\2\2#$\7_\2\2$\4\3\2\2\2%&\7+\2\2&\6\3\2\2\2\'(\7.\2\2"+
		"(\b\3\2\2\2)*\7]\2\2*\n\3\2\2\2+,\7*\2\2,\f\3\2\2\2-.\7=\2\2.\16\3\2\2"+
		"\2/\63\t\2\2\2\60\62\t\3\2\2\61\60\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2"+
		"\63\64\3\2\2\2\64\20\3\2\2\2\65\63\3\2\2\2\66:\t\4\2\2\679\t\3\2\28\67"+
		"\3\2\2\29<\3\2\2\2:8\3\2\2\2:;\3\2\2\2;\22\3\2\2\2<:\3\2\2\2=>\7/\2\2"+
		">\24\3\2\2\2?A\t\5\2\2@?\3\2\2\2AB\3\2\2\2B@\3\2\2\2BC\3\2\2\2CJ\3\2\2"+
		"\2DF\4\60\61\2EG\t\5\2\2FE\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2"+
		"\2\2JD\3\2\2\2KL\3\2\2\2LJ\3\2\2\2LM\3\2\2\2M\26\3\2\2\2NO\7/\2\2OS\7"+
		"@\2\2PQ\7?\2\2QS\7@\2\2RN\3\2\2\2RP\3\2\2\2S\30\3\2\2\2TV\t\5\2\2UT\3"+
		"\2\2\2VW\3\2\2\2WU\3\2\2\2WX\3\2\2\2X\32\3\2\2\2Y[\t\6\2\2ZY\3\2\2\2["+
		"\\\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]\34\3\2\2\2^_\7<\2\2_`\7<\2\2`\36\3\2"+
		"\2\2ab\7\f\2\2b \3\2\2\2ce\t\7\2\2dc\3\2\2\2ef\3\2\2\2fd\3\2\2\2fg\3\2"+
		"\2\2gh\3\2\2\2hi\b\21\2\2i\"\3\2\2\2\f\2\63:BHLRW\\f";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}