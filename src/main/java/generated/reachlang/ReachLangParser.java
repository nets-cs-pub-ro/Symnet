package generated.reachlang;// Generated from ReachLang.g by ANTLR 4.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ReachLangParser extends Parser {
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << CLIENT) | (1L << INTERNET))) != 0)) {
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
			if (_la==3) {
				{
				setState(46); match(3);
				}
			}

			setState(49); entrance();
			setState(54);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
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
			setState(60);
			_la = _input.LA(1);
			if ( !(_la==CLIENT || _la==INTERNET) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(61); condition();
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
			setState(63); nport();
			setState(64); condition();
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
			setState(66);
			_la = _input.LA(1);
			if ( !(_la==CLIENT || _la==INTERNET) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(67); condition();
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
			setState(70);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << 6) | (1L << 7) | (1L << 8) | (1L << 12) | (1L << 13))) != 0)) {
				{
				setState(69); trafficdesc();
				}
			}

			setState(74);
			_la = _input.LA(1);
			if (_la==9) {
				{
				setState(72); match(9);
				setState(73); invariant();
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
			setState(76); constraint();
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==10) {
				{
				{
				setState(77); match(10);
				setState(78); constraint();
				}
				}
				setState(83);
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
			setState(84); field();
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==10) {
				{
				{
				setState(85); match(10);
				setState(86); field();
				}
				}
				setState(91);
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
			setState(95);
			switch (_input.LA(1)) {
			case 8:
			case 13:
				enterOuterAlt(_localctx, 1);
				{
				setState(92); ipconstraint();
				}
				break;
			case 6:
			case 12:
				enterOuterAlt(_localctx, 2);
				{
				setState(93); l4constraint();
				}
				break;
			case 2:
			case 7:
				enterOuterAlt(_localctx, 3);
				{
				setState(94); protoconstraint();
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
			setState(97);
			_la = _input.LA(1);
			if ( !(_la==8 || _la==13) ) {
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
			setState(99); ipfield();
			setState(102);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(100); mask();
				}
				break;

			case 2:
				{
				setState(101); ipv4();
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
			setState(104);
			_la = _input.LA(1);
			if ( !(_la==6 || _la==12) ) {
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
			setState(106); l4field();
			setState(109);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(107); range();
				}
				break;

			case 2:
				{
				setState(108); match(NUMBER);
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
			setState(111);
			_la = _input.LA(1);
			if ( !(_la==2 || _la==7) ) {
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
			setState(116);
			switch (_input.LA(1)) {
			case 8:
			case 13:
				{
				setState(113); ipfield();
				}
				break;
			case 6:
			case 12:
				{
				setState(114); l4field();
				}
				break;
			case 11:
				{
				setState(115); match(11);
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
			setState(118); match(ID);
			setState(119); match(5);
			setState(120); match(ID);
			setState(123);
			_la = _input.LA(1);
			if (_la==5) {
				{
				setState(121); match(5);
				setState(122); match(NUMBER);
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
			setState(125); match(NUMBER);
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
			setState(127); match(NUMBER);
			{
			setState(128); match(1);
			setState(129); match(NUMBER);
			}
			{
			setState(131); match(1);
			setState(132); match(NUMBER);
			}
			{
			setState(134); match(1);
			setState(135); match(NUMBER);
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
			setState(137); ipv4();
			setState(138); match(14);
			setState(139); match(NUMBER);
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
			setState(141); match(NUMBER);
			setState(142); match(4);
			setState(143); match(NUMBER);
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
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\26\u0094\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\7\2,\n\2\f\2\16\2/\13\2\3\3\5\3\62"+
		"\n\3\3\3\3\3\3\3\7\3\67\n\3\f\3\16\3:\13\3\3\3\3\3\3\3\3\4\3\4\3\4\3\5"+
		"\3\5\3\5\3\6\3\6\3\6\3\7\5\7I\n\7\3\7\3\7\5\7M\n\7\3\b\3\b\3\b\7\bR\n"+
		"\b\f\b\16\bU\13\b\3\t\3\t\3\t\7\tZ\n\t\f\t\16\t]\13\t\3\n\3\n\3\n\5\n"+
		"b\n\n\3\13\3\13\3\f\3\f\3\f\5\fi\n\f\3\r\3\r\3\16\3\16\3\16\5\16p\n\16"+
		"\3\17\3\17\3\20\3\20\3\20\5\20w\n\20\3\21\3\21\3\21\3\21\3\21\5\21~\n"+
		"\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3"+
		"\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\2\26\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36 \"$&(\2\6\3\2\22\23\4\2\n\n\17\17\4\2\b\b\16\16\4\2\4\4\t"+
		"\t\u008d\2-\3\2\2\2\4\61\3\2\2\2\6>\3\2\2\2\bA\3\2\2\2\nD\3\2\2\2\fH\3"+
		"\2\2\2\16N\3\2\2\2\20V\3\2\2\2\22a\3\2\2\2\24c\3\2\2\2\26e\3\2\2\2\30"+
		"j\3\2\2\2\32l\3\2\2\2\34q\3\2\2\2\36v\3\2\2\2 x\3\2\2\2\"\177\3\2\2\2"+
		"$\u0081\3\2\2\2&\u008b\3\2\2\2(\u008f\3\2\2\2*,\5\4\3\2+*\3\2\2\2,/\3"+
		"\2\2\2-+\3\2\2\2-.\3\2\2\2.\3\3\2\2\2/-\3\2\2\2\60\62\7\5\2\2\61\60\3"+
		"\2\2\2\61\62\3\2\2\2\62\63\3\2\2\2\638\5\6\4\2\64\65\7\25\2\2\65\67\5"+
		"\b\5\2\66\64\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2\2:8\3\2"+
		"\2\2;<\7\25\2\2<=\5\n\6\2=\5\3\2\2\2>?\t\2\2\2?@\5\f\7\2@\7\3\2\2\2AB"+
		"\5 \21\2BC\5\f\7\2C\t\3\2\2\2DE\t\2\2\2EF\5\f\7\2F\13\3\2\2\2GI\5\16\b"+
		"\2HG\3\2\2\2HI\3\2\2\2IL\3\2\2\2JK\7\13\2\2KM\5\20\t\2LJ\3\2\2\2LM\3\2"+
		"\2\2M\r\3\2\2\2NS\5\22\n\2OP\7\f\2\2PR\5\22\n\2QO\3\2\2\2RU\3\2\2\2SQ"+
		"\3\2\2\2ST\3\2\2\2T\17\3\2\2\2US\3\2\2\2V[\5\36\20\2WX\7\f\2\2XZ\5\36"+
		"\20\2YW\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\\21\3\2\2\2][\3\2\2\2"+
		"^b\5\26\f\2_b\5\32\16\2`b\5\34\17\2a^\3\2\2\2a_\3\2\2\2a`\3\2\2\2b\23"+
		"\3\2\2\2cd\t\3\2\2d\25\3\2\2\2eh\5\24\13\2fi\5&\24\2gi\5$\23\2hf\3\2\2"+
		"\2hg\3\2\2\2i\27\3\2\2\2jk\t\4\2\2k\31\3\2\2\2lo\5\30\r\2mp\5(\25\2np"+
		"\7\21\2\2om\3\2\2\2on\3\2\2\2p\33\3\2\2\2qr\t\5\2\2r\35\3\2\2\2sw\5\24"+
		"\13\2tw\5\30\r\2uw\7\r\2\2vs\3\2\2\2vt\3\2\2\2vu\3\2\2\2w\37\3\2\2\2x"+
		"y\7\24\2\2yz\7\7\2\2z}\7\24\2\2{|\7\7\2\2|~\7\21\2\2}{\3\2\2\2}~\3\2\2"+
		"\2~!\3\2\2\2\177\u0080\7\21\2\2\u0080#\3\2\2\2\u0081\u0082\7\21\2\2\u0082"+
		"\u0083\7\3\2\2\u0083\u0084\7\21\2\2\u0084\u0085\3\2\2\2\u0085\u0086\7"+
		"\3\2\2\u0086\u0087\7\21\2\2\u0087\u0088\3\2\2\2\u0088\u0089\7\3\2\2\u0089"+
		"\u008a\7\21\2\2\u008a%\3\2\2\2\u008b\u008c\5$\23\2\u008c\u008d\7\20\2"+
		"\2\u008d\u008e\7\21\2\2\u008e\'\3\2\2\2\u008f\u0090\7\21\2\2\u0090\u0091"+
		"\7\6\2\2\u0091\u0092\7\21\2\2\u0092)\3\2\2\2\16-\618HLS[ahov}";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}