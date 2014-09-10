// Generated from src/main/resources/click_grammar/Click.g by ANTLR 4.1
package generated.click;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ClickParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, CONJUNCTION=7, NON_CAPITALIZED_STRING=8, 
		CAPITALIZED_STRING=9, DASH=10, NUMERIC_CONF_PARAM=11, ARROW=12, NUMBER=13, 
		ANY_STRING=14, DEFINE_SYMBOL=15, NEWLINE=16, WS=17;
	public static final String[] tokenNames = {
		"<INVALID>", "']'", "')'", "','", "'['", "'('", "';'", "'&&'", "NON_CAPITALIZED_STRING", 
		"CAPITALIZED_STRING", "'-'", "NUMERIC_CONF_PARAM", "ARROW", "NUMBER", 
		"ANY_STRING", "'::'", "'\n'", "WS"
	};
	public static final int
		RULE_configFile = 0, RULE_line = 1, RULE_component = 2, RULE_newElement = 3, 
		RULE_elementInstance = 4, RULE_configParameter = 5, RULE_config = 6, RULE_className = 7, 
		RULE_elementName = 8, RULE_path = 9, RULE_startOfPath = 10, RULE_inTheMiddle = 11, 
		RULE_endOfPath = 12, RULE_pathElement = 13, RULE_port = 14, RULE_portId = 15;
	public static final String[] ruleNames = {
		"configFile", "line", "component", "newElement", "elementInstance", "configParameter", 
		"config", "className", "elementName", "path", "startOfPath", "inTheMiddle", 
		"endOfPath", "pathElement", "port", "portId"
	};

	@Override
	public String getGrammarFileName() { return "Click.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public ClickParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ConfigFileContext extends ParserRuleContext {
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public ConfigFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_configFile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterConfigFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitConfigFile(this);
		}
	}

	public final ConfigFileContext configFile() throws RecognitionException {
		ConfigFileContext _localctx = new ConfigFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_configFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(32); line();
				}
				}
				setState(35); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NON_CAPITALIZED_STRING) | (1L << CAPITALIZED_STRING) | (1L << NEWLINE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(ClickParser.NEWLINE, 0); }
		public ComponentContext component(int i) {
			return getRuleContext(ComponentContext.class,i);
		}
		public List<ComponentContext> component() {
			return getRuleContexts(ComponentContext.class);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitLine(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_line);
		int _la;
		try {
			int _alt;
			setState(55);
			switch (_input.LA(1)) {
			case NON_CAPITALIZED_STRING:
			case CAPITALIZED_STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(37); component();
				setState(42);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				while ( _alt!=2 && _alt!=-1 ) {
					if ( _alt==1 ) {
						{
						{
						setState(38); match(6);
						setState(39); component();
						}
						} 
					}
					setState(44);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				}
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==6) {
					{
					{
					setState(45); match(6);
					}
					}
					setState(50);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(52);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(51); match(NEWLINE);
					}
					break;
				}
				}
				break;
			case NEWLINE:
				enterOuterAlt(_localctx, 2);
				{
				setState(54); match(NEWLINE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComponentContext extends ParserRuleContext {
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public NewElementContext newElement() {
			return getRuleContext(NewElementContext.class,0);
		}
		public ComponentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_component; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterComponent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitComponent(this);
		}
	}

	public final ComponentContext component() throws RecognitionException {
		ComponentContext _localctx = new ComponentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_component);
		try {
			setState(59);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(57); newElement();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(58); path();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewElementContext extends ParserRuleContext {
		public ElementNameContext elementName() {
			return getRuleContext(ElementNameContext.class,0);
		}
		public TerminalNode DEFINE_SYMBOL() { return getToken(ClickParser.DEFINE_SYMBOL, 0); }
		public ElementInstanceContext elementInstance() {
			return getRuleContext(ElementInstanceContext.class,0);
		}
		public NewElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterNewElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitNewElement(this);
		}
	}

	public final NewElementContext newElement() throws RecognitionException {
		NewElementContext _localctx = new NewElementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_newElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			_la = _input.LA(1);
			if (_la==NON_CAPITALIZED_STRING) {
				{
				setState(61); elementName();
				setState(62); match(DEFINE_SYMBOL);
				}
			}

			setState(66); elementInstance();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementInstanceContext extends ParserRuleContext {
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public ConfigContext config() {
			return getRuleContext(ConfigContext.class,0);
		}
		public ElementInstanceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementInstance; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterElementInstance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitElementInstance(this);
		}
	}

	public final ElementInstanceContext elementInstance() throws RecognitionException {
		ElementInstanceContext _localctx = new ElementInstanceContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_elementInstance);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68); className();
			setState(70);
			_la = _input.LA(1);
			if (_la==5) {
				{
				setState(69); config();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConfigParameterContext extends ParserRuleContext {
		public TerminalNode NON_CAPITALIZED_STRING(int i) {
			return getToken(ClickParser.NON_CAPITALIZED_STRING, i);
		}
		public TerminalNode NUMERIC_CONF_PARAM(int i) {
			return getToken(ClickParser.NUMERIC_CONF_PARAM, i);
		}
		public List<TerminalNode> NUMERIC_CONF_PARAM() { return getTokens(ClickParser.NUMERIC_CONF_PARAM); }
		public TerminalNode NUMBER(int i) {
			return getToken(ClickParser.NUMBER, i);
		}
		public List<TerminalNode> DASH() { return getTokens(ClickParser.DASH); }
		public List<TerminalNode> CONJUNCTION() { return getTokens(ClickParser.CONJUNCTION); }
		public TerminalNode CONJUNCTION(int i) {
			return getToken(ClickParser.CONJUNCTION, i);
		}
		public TerminalNode DASH(int i) {
			return getToken(ClickParser.DASH, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(ClickParser.NUMBER); }
		public List<TerminalNode> CAPITALIZED_STRING() { return getTokens(ClickParser.CAPITALIZED_STRING); }
		public List<TerminalNode> NON_CAPITALIZED_STRING() { return getTokens(ClickParser.NON_CAPITALIZED_STRING); }
		public TerminalNode CAPITALIZED_STRING(int i) {
			return getToken(ClickParser.CAPITALIZED_STRING, i);
		}
		public ConfigParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_configParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterConfigParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitConfigParameter(this);
		}
	}

	public final ConfigParameterContext configParameter() throws RecognitionException {
		ConfigParameterContext _localctx = new ConfigParameterContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_configParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(72);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONJUNCTION) | (1L << NON_CAPITALIZED_STRING) | (1L << CAPITALIZED_STRING) | (1L << DASH) | (1L << NUMERIC_CONF_PARAM) | (1L << NUMBER))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				}
				setState(75); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONJUNCTION) | (1L << NON_CAPITALIZED_STRING) | (1L << CAPITALIZED_STRING) | (1L << DASH) | (1L << NUMERIC_CONF_PARAM) | (1L << NUMBER))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConfigContext extends ParserRuleContext {
		public ConfigParameterContext configParameter(int i) {
			return getRuleContext(ConfigParameterContext.class,i);
		}
		public List<ConfigParameterContext> configParameter() {
			return getRuleContexts(ConfigParameterContext.class);
		}
		public ConfigContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_config; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterConfig(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitConfig(this);
		}
	}

	public final ConfigContext config() throws RecognitionException {
		ConfigContext _localctx = new ConfigContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_config);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77); match(5);
			setState(86);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONJUNCTION) | (1L << NON_CAPITALIZED_STRING) | (1L << CAPITALIZED_STRING) | (1L << DASH) | (1L << NUMERIC_CONF_PARAM) | (1L << NUMBER))) != 0)) {
				{
				setState(78); configParameter();
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==3) {
					{
					{
					setState(79); match(3);
					setState(80); configParameter();
					}
					}
					setState(85);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(88); match(2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassNameContext extends ParserRuleContext {
		public TerminalNode CAPITALIZED_STRING() { return getToken(ClickParser.CAPITALIZED_STRING, 0); }
		public ClassNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_className; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterClassName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitClassName(this);
		}
	}

	public final ClassNameContext className() throws RecognitionException {
		ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_className);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90); match(CAPITALIZED_STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementNameContext extends ParserRuleContext {
		public TerminalNode NON_CAPITALIZED_STRING() { return getToken(ClickParser.NON_CAPITALIZED_STRING, 0); }
		public ElementNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterElementName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitElementName(this);
		}
	}

	public final ElementNameContext elementName() throws RecognitionException {
		ElementNameContext _localctx = new ElementNameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_elementName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92); match(NON_CAPITALIZED_STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathContext extends ParserRuleContext {
		public List<InTheMiddleContext> inTheMiddle() {
			return getRuleContexts(InTheMiddleContext.class);
		}
		public InTheMiddleContext inTheMiddle(int i) {
			return getRuleContext(InTheMiddleContext.class,i);
		}
		public EndOfPathContext endOfPath() {
			return getRuleContext(EndOfPathContext.class,0);
		}
		public StartOfPathContext startOfPath() {
			return getRuleContext(StartOfPathContext.class,0);
		}
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitPath(this);
		}
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_path);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(94); startOfPath();
			setState(98);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(95); inTheMiddle();
					}
					} 
				}
				setState(100);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(101); endOfPath();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StartOfPathContext extends ParserRuleContext {
		public PortContext port() {
			return getRuleContext(PortContext.class,0);
		}
		public PathElementContext pathElement() {
			return getRuleContext(PathElementContext.class,0);
		}
		public StartOfPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startOfPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterStartOfPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitStartOfPath(this);
		}
	}

	public final StartOfPathContext startOfPath() throws RecognitionException {
		StartOfPathContext _localctx = new StartOfPathContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_startOfPath);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103); pathElement();
			setState(105);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(104); port();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InTheMiddleContext extends ParserRuleContext {
		public List<PortContext> port() {
			return getRuleContexts(PortContext.class);
		}
		public PathElementContext pathElement() {
			return getRuleContext(PathElementContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(ClickParser.ARROW, 0); }
		public PortContext port(int i) {
			return getRuleContext(PortContext.class,i);
		}
		public InTheMiddleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inTheMiddle; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterInTheMiddle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitInTheMiddle(this);
		}
	}

	public final InTheMiddleContext inTheMiddle() throws RecognitionException {
		InTheMiddleContext _localctx = new InTheMiddleContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_inTheMiddle);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107); match(ARROW);
			setState(109);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(108); port();
				}
			}

			setState(111); pathElement();
			setState(113);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(112); port();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EndOfPathContext extends ParserRuleContext {
		public PortContext port() {
			return getRuleContext(PortContext.class,0);
		}
		public PathElementContext pathElement() {
			return getRuleContext(PathElementContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(ClickParser.ARROW, 0); }
		public EndOfPathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endOfPath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterEndOfPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitEndOfPath(this);
		}
	}

	public final EndOfPathContext endOfPath() throws RecognitionException {
		EndOfPathContext _localctx = new EndOfPathContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_endOfPath);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115); match(ARROW);
			setState(117);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(116); port();
				}
			}

			setState(119); pathElement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathElementContext extends ParserRuleContext {
		public ElementNameContext elementName() {
			return getRuleContext(ElementNameContext.class,0);
		}
		public NewElementContext newElement() {
			return getRuleContext(NewElementContext.class,0);
		}
		public PathElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterPathElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitPathElement(this);
		}
	}

	public final PathElementContext pathElement() throws RecognitionException {
		PathElementContext _localctx = new PathElementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_pathElement);
		try {
			setState(123);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(121); elementName();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(122); newElement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PortContext extends ParserRuleContext {
		public PortIdContext portId() {
			return getRuleContext(PortIdContext.class,0);
		}
		public PortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_port; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitPort(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_port);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125); match(4);
			setState(126); portId();
			setState(127); match(1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PortIdContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(ClickParser.NUMBER, 0); }
		public PortIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_portId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterPortId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitPortId(this);
		}
	}

	public final PortIdContext portId() throws RecognitionException {
		PortIdContext _localctx = new PortIdContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_portId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129); match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\23\u0086\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\6\2"+
		"$\n\2\r\2\16\2%\3\3\3\3\3\3\7\3+\n\3\f\3\16\3.\13\3\3\3\7\3\61\n\3\f\3"+
		"\16\3\64\13\3\3\3\5\3\67\n\3\3\3\5\3:\n\3\3\4\3\4\5\4>\n\4\3\5\3\5\3\5"+
		"\5\5C\n\5\3\5\3\5\3\6\3\6\5\6I\n\6\3\7\6\7L\n\7\r\7\16\7M\3\b\3\b\3\b"+
		"\3\b\7\bT\n\b\f\b\16\bW\13\b\5\bY\n\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\7\13c\n\13\f\13\16\13f\13\13\3\13\3\13\3\f\3\f\5\fl\n\f\3\r\3\r\5\rp"+
		"\n\r\3\r\3\r\5\rt\n\r\3\16\3\16\5\16x\n\16\3\16\3\16\3\17\3\17\5\17~\n"+
		"\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\2\22\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36 \2\3\4\2\t\r\17\17\u0086\2#\3\2\2\2\49\3\2\2\2\6=\3\2\2\2"+
		"\bB\3\2\2\2\nF\3\2\2\2\fK\3\2\2\2\16O\3\2\2\2\20\\\3\2\2\2\22^\3\2\2\2"+
		"\24`\3\2\2\2\26i\3\2\2\2\30m\3\2\2\2\32u\3\2\2\2\34}\3\2\2\2\36\177\3"+
		"\2\2\2 \u0083\3\2\2\2\"$\5\4\3\2#\"\3\2\2\2$%\3\2\2\2%#\3\2\2\2%&\3\2"+
		"\2\2&\3\3\2\2\2\',\5\6\4\2()\7\b\2\2)+\5\6\4\2*(\3\2\2\2+.\3\2\2\2,*\3"+
		"\2\2\2,-\3\2\2\2-\62\3\2\2\2.,\3\2\2\2/\61\7\b\2\2\60/\3\2\2\2\61\64\3"+
		"\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\65\67\7"+
		"\22\2\2\66\65\3\2\2\2\66\67\3\2\2\2\67:\3\2\2\28:\7\22\2\29\'\3\2\2\2"+
		"98\3\2\2\2:\5\3\2\2\2;>\5\b\5\2<>\5\24\13\2=;\3\2\2\2=<\3\2\2\2>\7\3\2"+
		"\2\2?@\5\22\n\2@A\7\21\2\2AC\3\2\2\2B?\3\2\2\2BC\3\2\2\2CD\3\2\2\2DE\5"+
		"\n\6\2E\t\3\2\2\2FH\5\20\t\2GI\5\16\b\2HG\3\2\2\2HI\3\2\2\2I\13\3\2\2"+
		"\2JL\t\2\2\2KJ\3\2\2\2LM\3\2\2\2MK\3\2\2\2MN\3\2\2\2N\r\3\2\2\2OX\7\7"+
		"\2\2PU\5\f\7\2QR\7\5\2\2RT\5\f\7\2SQ\3\2\2\2TW\3\2\2\2US\3\2\2\2UV\3\2"+
		"\2\2VY\3\2\2\2WU\3\2\2\2XP\3\2\2\2XY\3\2\2\2YZ\3\2\2\2Z[\7\4\2\2[\17\3"+
		"\2\2\2\\]\7\13\2\2]\21\3\2\2\2^_\7\n\2\2_\23\3\2\2\2`d\5\26\f\2ac\5\30"+
		"\r\2ba\3\2\2\2cf\3\2\2\2db\3\2\2\2de\3\2\2\2eg\3\2\2\2fd\3\2\2\2gh\5\32"+
		"\16\2h\25\3\2\2\2ik\5\34\17\2jl\5\36\20\2kj\3\2\2\2kl\3\2\2\2l\27\3\2"+
		"\2\2mo\7\16\2\2np\5\36\20\2on\3\2\2\2op\3\2\2\2pq\3\2\2\2qs\5\34\17\2"+
		"rt\5\36\20\2sr\3\2\2\2st\3\2\2\2t\31\3\2\2\2uw\7\16\2\2vx\5\36\20\2wv"+
		"\3\2\2\2wx\3\2\2\2xy\3\2\2\2yz\5\34\17\2z\33\3\2\2\2{~\5\22\n\2|~\5\b"+
		"\5\2}{\3\2\2\2}|\3\2\2\2~\35\3\2\2\2\177\u0080\7\6\2\2\u0080\u0081\5 "+
		"\21\2\u0081\u0082\7\3\2\2\u0082\37\3\2\2\2\u0083\u0084\7\17\2\2\u0084"+
		"!\3\2\2\2\23%,\62\669=BHMUXdkosw}";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}