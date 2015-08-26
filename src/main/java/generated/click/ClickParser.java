package generated.click;// Generated from Click.g by ANTLR 4.1
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
		RULE_endOfPath = 12, RULE_pathElement = 13, RULE_entryPort = 14, RULE_exitPort = 15, 
		RULE_port = 16, RULE_portId = 17;
	public static final String[] ruleNames = {
		"configFile", "line", "component", "newElement", "elementInstance", "configParameter", 
		"config", "className", "elementName", "path", "startOfPath", "inTheMiddle", 
		"endOfPath", "pathElement", "entryPort", "exitPort", "port", "portId"
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
			setState(37); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(36); line();
				}
				}
				setState(39); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 4) | (1L << NON_CAPITALIZED_STRING) | (1L << CAPITALIZED_STRING) | (1L << NEWLINE))) != 0) );
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
			setState(59);
			switch (_input.LA(1)) {
			case 4:
			case NON_CAPITALIZED_STRING:
			case CAPITALIZED_STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(41); component();
				setState(46);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				while ( _alt!=2 && _alt!=-1 ) {
					if ( _alt==1 ) {
						{
						{
						setState(42); match(6);
						setState(43); component();
						}
						} 
					}
					setState(48);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				}
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==6) {
					{
					{
					setState(49); match(6);
					}
					}
					setState(54);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(56);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(55); match(NEWLINE);
					}
					break;
				}
				}
				break;
			case NEWLINE:
				enterOuterAlt(_localctx, 2);
				{
				setState(58); match(NEWLINE);
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
			setState(63);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(61); newElement();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(62); path();
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
			setState(68);
			_la = _input.LA(1);
			if (_la==NON_CAPITALIZED_STRING) {
				{
				setState(65); elementName();
				setState(66); match(DEFINE_SYMBOL);
				}
			}

			setState(70); elementInstance();
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
			setState(72); className();
			setState(74);
			_la = _input.LA(1);
			if (_la==5) {
				{
				setState(73); config();
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
			setState(77); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(76);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONJUNCTION) | (1L << NON_CAPITALIZED_STRING) | (1L << CAPITALIZED_STRING) | (1L << DASH) | (1L << NUMERIC_CONF_PARAM) | (1L << NUMBER))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				}
				setState(79); 
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
			setState(81); match(5);
			setState(90);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONJUNCTION) | (1L << NON_CAPITALIZED_STRING) | (1L << CAPITALIZED_STRING) | (1L << DASH) | (1L << NUMERIC_CONF_PARAM) | (1L << NUMBER))) != 0)) {
				{
				setState(82); configParameter();
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==3) {
					{
					{
					setState(83); match(3);
					setState(84); configParameter();
					}
					}
					setState(89);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(92); match(2);
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
			setState(94); match(CAPITALIZED_STRING);
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
			setState(96); match(NON_CAPITALIZED_STRING);
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
			setState(98); startOfPath();
			setState(102);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(99); inTheMiddle();
					}
					} 
				}
				setState(104);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(105); endOfPath();
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
		public EntryPortContext entryPort() {
			return getRuleContext(EntryPortContext.class,0);
		}
		public PathElementContext pathElement() {
			return getRuleContext(PathElementContext.class,0);
		}
		public ExitPortContext exitPort() {
			return getRuleContext(ExitPortContext.class,0);
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
			setState(108);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(107); entryPort();
				}
			}

			setState(110); pathElement();
			setState(112);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(111); exitPort();
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
		public EntryPortContext entryPort() {
			return getRuleContext(EntryPortContext.class,0);
		}
		public PathElementContext pathElement() {
			return getRuleContext(PathElementContext.class,0);
		}
		public ExitPortContext exitPort() {
			return getRuleContext(ExitPortContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(ClickParser.ARROW, 0); }
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
			setState(114); match(ARROW);
			setState(116);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(115); entryPort();
				}
			}

			setState(118); pathElement();
			setState(120);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(119); exitPort();
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
		public EntryPortContext entryPort() {
			return getRuleContext(EntryPortContext.class,0);
		}
		public PathElementContext pathElement() {
			return getRuleContext(PathElementContext.class,0);
		}
		public ExitPortContext exitPort() {
			return getRuleContext(ExitPortContext.class,0);
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
			setState(122); match(ARROW);
			setState(124);
			_la = _input.LA(1);
			if (_la==4) {
				{
				setState(123); entryPort();
				}
			}

			setState(126); pathElement();
			setState(128);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(127); exitPort();
				}
				break;
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
			setState(132);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(130); elementName();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(131); newElement();
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

	public static class EntryPortContext extends ParserRuleContext {
		public PortContext port() {
			return getRuleContext(PortContext.class,0);
		}
		public EntryPortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entryPort; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterEntryPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitEntryPort(this);
		}
	}

	public final EntryPortContext entryPort() throws RecognitionException {
		EntryPortContext _localctx = new EntryPortContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_entryPort);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134); port();
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

	public static class ExitPortContext extends ParserRuleContext {
		public PortContext port() {
			return getRuleContext(PortContext.class,0);
		}
		public ExitPortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exitPort; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).enterExitPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ClickListener ) ((ClickListener)listener).exitExitPort(this);
		}
	}

	public final ExitPortContext exitPort() throws RecognitionException {
		ExitPortContext _localctx = new ExitPortContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_exitPort);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136); port();
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
		enterRule(_localctx, 32, RULE_port);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138); match(4);
			setState(139); portId();
			setState(140); match(1);
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
		enterRule(_localctx, 34, RULE_portId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142); match(NUMBER);
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
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\23\u0093\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\6\2(\n\2\r\2\16\2)\3\3\3\3\3\3\7\3/\n\3\f\3\16\3\62\13"+
		"\3\3\3\7\3\65\n\3\f\3\16\38\13\3\3\3\5\3;\n\3\3\3\5\3>\n\3\3\4\3\4\5\4"+
		"B\n\4\3\5\3\5\3\5\5\5G\n\5\3\5\3\5\3\6\3\6\5\6M\n\6\3\7\6\7P\n\7\r\7\16"+
		"\7Q\3\b\3\b\3\b\3\b\7\bX\n\b\f\b\16\b[\13\b\5\b]\n\b\3\b\3\b\3\t\3\t\3"+
		"\n\3\n\3\13\3\13\7\13g\n\13\f\13\16\13j\13\13\3\13\3\13\3\f\5\fo\n\f\3"+
		"\f\3\f\5\fs\n\f\3\r\3\r\5\rw\n\r\3\r\3\r\5\r{\n\r\3\16\3\16\5\16\177\n"+
		"\16\3\16\3\16\5\16\u0083\n\16\3\17\3\17\5\17\u0087\n\17\3\20\3\20\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\2\24\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$\2\3\4\2\t\r\17\17\u0093\2\'\3\2\2\2\4=\3\2\2\2\6A"+
		"\3\2\2\2\bF\3\2\2\2\nJ\3\2\2\2\fO\3\2\2\2\16S\3\2\2\2\20`\3\2\2\2\22b"+
		"\3\2\2\2\24d\3\2\2\2\26n\3\2\2\2\30t\3\2\2\2\32|\3\2\2\2\34\u0086\3\2"+
		"\2\2\36\u0088\3\2\2\2 \u008a\3\2\2\2\"\u008c\3\2\2\2$\u0090\3\2\2\2&("+
		"\5\4\3\2\'&\3\2\2\2()\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*\3\3\2\2\2+\60\5\6"+
		"\4\2,-\7\b\2\2-/\5\6\4\2.,\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2\60\61\3\2\2"+
		"\2\61\66\3\2\2\2\62\60\3\2\2\2\63\65\7\b\2\2\64\63\3\2\2\2\658\3\2\2\2"+
		"\66\64\3\2\2\2\66\67\3\2\2\2\67:\3\2\2\28\66\3\2\2\29;\7\22\2\2:9\3\2"+
		"\2\2:;\3\2\2\2;>\3\2\2\2<>\7\22\2\2=+\3\2\2\2=<\3\2\2\2>\5\3\2\2\2?B\5"+
		"\b\5\2@B\5\24\13\2A?\3\2\2\2A@\3\2\2\2B\7\3\2\2\2CD\5\22\n\2DE\7\21\2"+
		"\2EG\3\2\2\2FC\3\2\2\2FG\3\2\2\2GH\3\2\2\2HI\5\n\6\2I\t\3\2\2\2JL\5\20"+
		"\t\2KM\5\16\b\2LK\3\2\2\2LM\3\2\2\2M\13\3\2\2\2NP\t\2\2\2ON\3\2\2\2PQ"+
		"\3\2\2\2QO\3\2\2\2QR\3\2\2\2R\r\3\2\2\2S\\\7\7\2\2TY\5\f\7\2UV\7\5\2\2"+
		"VX\5\f\7\2WU\3\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2"+
		"\\T\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^_\7\4\2\2_\17\3\2\2\2`a\7\13\2\2a\21"+
		"\3\2\2\2bc\7\n\2\2c\23\3\2\2\2dh\5\26\f\2eg\5\30\r\2fe\3\2\2\2gj\3\2\2"+
		"\2hf\3\2\2\2hi\3\2\2\2ik\3\2\2\2jh\3\2\2\2kl\5\32\16\2l\25\3\2\2\2mo\5"+
		"\36\20\2nm\3\2\2\2no\3\2\2\2op\3\2\2\2pr\5\34\17\2qs\5 \21\2rq\3\2\2\2"+
		"rs\3\2\2\2s\27\3\2\2\2tv\7\16\2\2uw\5\36\20\2vu\3\2\2\2vw\3\2\2\2wx\3"+
		"\2\2\2xz\5\34\17\2y{\5 \21\2zy\3\2\2\2z{\3\2\2\2{\31\3\2\2\2|~\7\16\2"+
		"\2}\177\5\36\20\2~}\3\2\2\2~\177\3\2\2\2\177\u0080\3\2\2\2\u0080\u0082"+
		"\5\34\17\2\u0081\u0083\5 \21\2\u0082\u0081\3\2\2\2\u0082\u0083\3\2\2\2"+
		"\u0083\33\3\2\2\2\u0084\u0087\5\22\n\2\u0085\u0087\5\b\5\2\u0086\u0084"+
		"\3\2\2\2\u0086\u0085\3\2\2\2\u0087\35\3\2\2\2\u0088\u0089\5\"\22\2\u0089"+
		"\37\3\2\2\2\u008a\u008b\5\"\22\2\u008b!\3\2\2\2\u008c\u008d\7\6\2\2\u008d"+
		"\u008e\5$\23\2\u008e\u008f\7\3\2\2\u008f#\3\2\2\2\u0090\u0091\7\17\2\2"+
		"\u0091%\3\2\2\2\25)\60\66:=AFLQY\\hnrvz~\u0082\u0086";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}