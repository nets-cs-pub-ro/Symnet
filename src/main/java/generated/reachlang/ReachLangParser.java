package generated.reachlang;// Generated from ReachLang.g by ANTLR 4.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ReachLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__13=1, T__12=2, T__11=3, T__10=4, T__9=5, T__8=6, T__7=7, T__6=8, T__5=9, 
		T__4=10, T__3=11, T__2=12, T__1=13, T__0=14, NUMBER=15, CLIENT=16, INTERNET=17, 
		ID=18, ARROW=19, WS=20;
	public static final String[] tokenNames = {
		"<INVALID>", "'.'", "'udp'", "'reach from'", "'-'", "':'", "'src port'", 
		"'tcp'", "'src'", "';'", "'&&'", "'proto'", "'dst port'", "'dst'", "'/'", 
		"NUMBER", "'client'", "'internet'", "ID", "'->'", "WS"
	};
	public static final int
		RULE_requirements = 0, RULE_test = 1, RULE_entrance = 2, RULE_middle = 3, 
		RULE_exit = 4, RULE_condition = 5, RULE_trafficdesc = 6, RULE_invariant = 7, 
		RULE_constraint = 8, RULE_ipfield = 9, RULE_ipconstraint = 10, RULE_l4field = 11, 
		RULE_l4constraint = 12, RULE_protoconstraint = 13, RULE_field = 14, RULE_nport = 15, 
		RULE_port = 16, RULE_ipv4 = 17, RULE_mask = 18, RULE_range = 19;
	public static final String[] ruleNames = {
		"requirements", "test", "entrance", "middle", "exit", "condition", "trafficdesc", 
		"invariant", "constraint", "ipfield", "ipconstraint", "l4field", "l4constraint", 
		"protoconstraint", "field", "nport", "port", "ipv4", "mask", "range"
	};

	@Override
	public String getGrammarFileName() { return "ReachLang.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ReachLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RequirementsContext extends ParserRuleContext {
		public TestContext test(int i) {
			return getRuleContext(TestContext.class,i);
		}
		public List<TestContext> test() {
			return getRuleContexts(TestContext.class);
		}
		public RequirementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_requirements; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitRequirements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RequirementsContext requirements() throws RecognitionException {
		RequirementsContext _localctx = new RequirementsContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_requirements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << CLIENT) | (1L << INTERNET))) != 0)) {
				{
				{
				setState(40); test();
				}
				}
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class TestContext extends ParserRuleContext {
		public TerminalNode ARROW(int i) {
			return getToken(ReachLangParser.ARROW, i);
		}
		public EntranceContext entrance() {
			return getRuleContext(EntranceContext.class,0);
		}
		public MiddleContext middle(int i) {
			return getRuleContext(MiddleContext.class,i);
		}
		public List<TerminalNode> ARROW() { return getTokens(ReachLangParser.ARROW); }
		public List<MiddleContext> middle() {
			return getRuleContexts(MiddleContext.class);
		}
		public ExitContext exit() {
			return getRuleContext(ExitContext.class,0);
		}
		public TestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_test; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitTest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestContext test() throws RecognitionException {
		TestContext _localctx = new TestContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_test);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(46); match(T__11);
				}
			}

			setState(49); entrance();
			setState(54);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(50); match(ARROW);
					setState(51); middle();
					}
					} 
				}
				setState(56);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(57); match(ARROW);
			setState(58); exit();
			setState(59); match(T__13);
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

	public static class EntranceContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode INTERNET() { return getToken(ReachLangParser.INTERNET, 0); }
		public TerminalNode CLIENT() { return getToken(ReachLangParser.CLIENT, 0); }
		public EntranceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entrance; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitEntrance(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntranceContext entrance() throws RecognitionException {
		EntranceContext _localctx = new EntranceContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_entrance);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			_la = _input.LA(1);
			if ( !(_la==CLIENT || _la==INTERNET) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(62); condition();
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

	public static class MiddleContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public NportContext nport() {
			return getRuleContext(NportContext.class,0);
		}
		public MiddleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_middle; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitMiddle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MiddleContext middle() throws RecognitionException {
		MiddleContext _localctx = new MiddleContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_middle);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64); nport();
			setState(65); condition();
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

	public static class ExitContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode INTERNET() { return getToken(ReachLangParser.INTERNET, 0); }
		public TerminalNode CLIENT() { return getToken(ReachLangParser.CLIENT, 0); }
		public ExitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitExit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExitContext exit() throws RecognitionException {
		ExitContext _localctx = new ExitContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_exit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			_la = _input.LA(1);
			if ( !(_la==CLIENT || _la==INTERNET) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(68); condition();
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

	public static class ConditionContext extends ParserRuleContext {
		public InvariantContext invariant() {
			return getRuleContext(InvariantContext.class,0);
		}
		public TrafficdescContext trafficdesc() {
			return getRuleContext(TrafficdescContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__8) | (1L << T__7) | (1L << T__6) | (1L << T__2) | (1L << T__1))) != 0)) {
				{
				setState(70); trafficdesc();
				}
			}

			setState(75);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(73); match(T__5);
				setState(74); invariant();
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

	public static class TrafficdescContext extends ParserRuleContext {
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public TrafficdescContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trafficdesc; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitTrafficdesc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TrafficdescContext trafficdesc() throws RecognitionException {
		TrafficdescContext _localctx = new TrafficdescContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_trafficdesc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77); constraint();
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(78); match(T__4);
				setState(79); constraint();
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class InvariantContext extends ParserRuleContext {
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public InvariantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_invariant; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitInvariant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InvariantContext invariant() throws RecognitionException {
		InvariantContext _localctx = new InvariantContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_invariant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85); field();
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(86); match(T__4);
				setState(87); field();
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class ConstraintContext extends ParserRuleContext {
		public L4constraintContext l4constraint() {
			return getRuleContext(L4constraintContext.class,0);
		}
		public IpconstraintContext ipconstraint() {
			return getRuleContext(IpconstraintContext.class,0);
		}
		public ProtoconstraintContext protoconstraint() {
			return getRuleContext(ProtoconstraintContext.class,0);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_constraint);
		try {
			setState(96);
			switch (_input.LA(1)) {
			case T__6:
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(93); ipconstraint();
				}
				break;
			case T__8:
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(94); l4constraint();
				}
				break;
			case T__12:
			case T__7:
				enterOuterAlt(_localctx, 3);
				{
				setState(95); protoconstraint();
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

	public static class IpfieldContext extends ParserRuleContext {
		public IpfieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ipfield; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitIpfield(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IpfieldContext ipfield() throws RecognitionException {
		IpfieldContext _localctx = new IpfieldContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ipfield);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			_la = _input.LA(1);
			if ( !(_la==T__6 || _la==T__1) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class IpconstraintContext extends ParserRuleContext {
		public MaskContext mask() {
			return getRuleContext(MaskContext.class,0);
		}
		public IpfieldContext ipfield() {
			return getRuleContext(IpfieldContext.class,0);
		}
		public Ipv4Context ipv4() {
			return getRuleContext(Ipv4Context.class,0);
		}
		public IpconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ipconstraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitIpconstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IpconstraintContext ipconstraint() throws RecognitionException {
		IpconstraintContext _localctx = new IpconstraintContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_ipconstraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100); ipfield();
			setState(103);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(101); mask();
				}
				break;

			case 2:
				{
				setState(102); ipv4();
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

	public static class L4fieldContext extends ParserRuleContext {
		public L4fieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_l4field; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitL4field(this);
			else return visitor.visitChildren(this);
		}
	}

	public final L4fieldContext l4field() throws RecognitionException {
		L4fieldContext _localctx = new L4fieldContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_l4field);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_la = _input.LA(1);
			if ( !(_la==T__8 || _la==T__2) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class L4constraintContext extends ParserRuleContext {
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(ReachLangParser.NUMBER, 0); }
		public L4fieldContext l4field() {
			return getRuleContext(L4fieldContext.class,0);
		}
		public L4constraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_l4constraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitL4constraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final L4constraintContext l4constraint() throws RecognitionException {
		L4constraintContext _localctx = new L4constraintContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_l4constraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107); l4field();
			setState(110);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(108); range();
				}
				break;

			case 2:
				{
				setState(109); match(NUMBER);
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

	public static class ProtoconstraintContext extends ParserRuleContext {
		public ProtoconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_protoconstraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitProtoconstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProtoconstraintContext protoconstraint() throws RecognitionException {
		ProtoconstraintContext _localctx = new ProtoconstraintContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_protoconstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			_la = _input.LA(1);
			if ( !(_la==T__12 || _la==T__7) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class FieldContext extends ParserRuleContext {
		public IpfieldContext ipfield() {
			return getRuleContext(IpfieldContext.class,0);
		}
		public L4fieldContext l4field() {
			return getRuleContext(L4fieldContext.class,0);
		}
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			switch (_input.LA(1)) {
			case T__6:
			case T__1:
				{
				setState(114); ipfield();
				}
				break;
			case T__8:
			case T__2:
				{
				setState(115); l4field();
				}
				break;
			case T__3:
				{
				setState(116); match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class NportContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ReachLangParser.ID); }
		public TerminalNode NUMBER() { return getToken(ReachLangParser.NUMBER, 0); }
		public TerminalNode ID(int i) {
			return getToken(ReachLangParser.ID, i);
		}
		public NportContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nport; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitNport(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NportContext nport() throws RecognitionException {
		NportContext _localctx = new NportContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_nport);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119); match(ID);
			setState(120); match(T__9);
			setState(121); match(ID);
			setState(124);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(122); match(T__9);
				setState(123); match(NUMBER);
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

	public static class PortContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(ReachLangParser.NUMBER, 0); }
		public PortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_port; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitPort(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_port);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126); match(NUMBER);
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

	public static class Ipv4Context extends ParserRuleContext {
		public TerminalNode NUMBER(int i) {
			return getToken(ReachLangParser.NUMBER, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(ReachLangParser.NUMBER); }
		public Ipv4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ipv4; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitIpv4(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ipv4Context ipv4() throws RecognitionException {
		Ipv4Context _localctx = new Ipv4Context(_ctx, getState());
		enterRule(_localctx, 34, RULE_ipv4);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128); match(NUMBER);
			{
			setState(129); match(T__13);
			setState(130); match(NUMBER);
			}
			{
			setState(132); match(T__13);
			setState(133); match(NUMBER);
			}
			{
			setState(135); match(T__13);
			setState(136); match(NUMBER);
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

	public static class MaskContext extends ParserRuleContext {
		public Ipv4Context ipv4() {
			return getRuleContext(Ipv4Context.class,0);
		}
		public TerminalNode NUMBER() { return getToken(ReachLangParser.NUMBER, 0); }
		public MaskContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mask; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitMask(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MaskContext mask() throws RecognitionException {
		MaskContext _localctx = new MaskContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_mask);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138); ipv4();
			setState(139); match(T__0);
			setState(140); match(NUMBER);
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

	public static class RangeContext extends ParserRuleContext {
		public TerminalNode NUMBER(int i) {
			return getToken(ReachLangParser.NUMBER, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(ReachLangParser.NUMBER); }
		public RangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_range; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ReachLangVisitor ) return ((ReachLangVisitor<? extends T>)visitor).visitRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RangeContext range() throws RecognitionException {
		RangeContext _localctx = new RangeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_range);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142); match(NUMBER);
			setState(143); match(T__10);
			setState(144); match(NUMBER);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\26\u0095\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\7\2,\n\2\f\2\16\2/\13\2\3\3\5\3\62"+
		"\n\3\3\3\3\3\3\3\7\3\67\n\3\f\3\16\3:\13\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\3\6\3\6\3\6\3\7\5\7J\n\7\3\7\3\7\5\7N\n\7\3\b\3\b\3\b\7\b"+
		"S\n\b\f\b\16\bV\13\b\3\t\3\t\3\t\7\t[\n\t\f\t\16\t^\13\t\3\n\3\n\3\n\5"+
		"\nc\n\n\3\13\3\13\3\f\3\f\3\f\5\fj\n\f\3\r\3\r\3\16\3\16\3\16\5\16q\n"+
		"\16\3\17\3\17\3\20\3\20\3\20\5\20x\n\20\3\21\3\21\3\21\3\21\3\21\5\21"+
		"\177\n\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\2\2\26\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(\2\6\3\2\22\23\4\2\n\n\17\17\4\2\b\b\16\16"+
		"\4\2\4\4\t\t\u008e\2-\3\2\2\2\4\61\3\2\2\2\6?\3\2\2\2\bB\3\2\2\2\nE\3"+
		"\2\2\2\fI\3\2\2\2\16O\3\2\2\2\20W\3\2\2\2\22b\3\2\2\2\24d\3\2\2\2\26f"+
		"\3\2\2\2\30k\3\2\2\2\32m\3\2\2\2\34r\3\2\2\2\36w\3\2\2\2 y\3\2\2\2\"\u0080"+
		"\3\2\2\2$\u0082\3\2\2\2&\u008c\3\2\2\2(\u0090\3\2\2\2*,\5\4\3\2+*\3\2"+
		"\2\2,/\3\2\2\2-+\3\2\2\2-.\3\2\2\2.\3\3\2\2\2/-\3\2\2\2\60\62\7\5\2\2"+
		"\61\60\3\2\2\2\61\62\3\2\2\2\62\63\3\2\2\2\638\5\6\4\2\64\65\7\25\2\2"+
		"\65\67\5\b\5\2\66\64\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2"+
		"\2:8\3\2\2\2;<\7\25\2\2<=\5\n\6\2=>\7\3\2\2>\5\3\2\2\2?@\t\2\2\2@A\5\f"+
		"\7\2A\7\3\2\2\2BC\5 \21\2CD\5\f\7\2D\t\3\2\2\2EF\t\2\2\2FG\5\f\7\2G\13"+
		"\3\2\2\2HJ\5\16\b\2IH\3\2\2\2IJ\3\2\2\2JM\3\2\2\2KL\7\13\2\2LN\5\20\t"+
		"\2MK\3\2\2\2MN\3\2\2\2N\r\3\2\2\2OT\5\22\n\2PQ\7\f\2\2QS\5\22\n\2RP\3"+
		"\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2U\17\3\2\2\2VT\3\2\2\2W\\\5\36\20"+
		"\2XY\7\f\2\2Y[\5\36\20\2ZX\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]\21"+
		"\3\2\2\2^\\\3\2\2\2_c\5\26\f\2`c\5\32\16\2ac\5\34\17\2b_\3\2\2\2b`\3\2"+
		"\2\2ba\3\2\2\2c\23\3\2\2\2de\t\3\2\2e\25\3\2\2\2fi\5\24\13\2gj\5&\24\2"+
		"hj\5$\23\2ig\3\2\2\2ih\3\2\2\2j\27\3\2\2\2kl\t\4\2\2l\31\3\2\2\2mp\5\30"+
		"\r\2nq\5(\25\2oq\7\21\2\2pn\3\2\2\2po\3\2\2\2q\33\3\2\2\2rs\t\5\2\2s\35"+
		"\3\2\2\2tx\5\24\13\2ux\5\30\r\2vx\7\r\2\2wt\3\2\2\2wu\3\2\2\2wv\3\2\2"+
		"\2x\37\3\2\2\2yz\7\24\2\2z{\7\7\2\2{~\7\24\2\2|}\7\7\2\2}\177\7\21\2\2"+
		"~|\3\2\2\2~\177\3\2\2\2\177!\3\2\2\2\u0080\u0081\7\21\2\2\u0081#\3\2\2"+
		"\2\u0082\u0083\7\21\2\2\u0083\u0084\7\3\2\2\u0084\u0085\7\21\2\2\u0085"+
		"\u0086\3\2\2\2\u0086\u0087\7\3\2\2\u0087\u0088\7\21\2\2\u0088\u0089\3"+
		"\2\2\2\u0089\u008a\7\3\2\2\u008a\u008b\7\21\2\2\u008b%\3\2\2\2\u008c\u008d"+
		"\5$\23\2\u008d\u008e\7\20\2\2\u008e\u008f\7\21\2\2\u008f\'\3\2\2\2\u0090"+
		"\u0091\7\21\2\2\u0091\u0092\7\6\2\2\u0092\u0093\7\21\2\2\u0093)\3\2\2"+
		"\2\16-\618IMT\\bipw~";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}