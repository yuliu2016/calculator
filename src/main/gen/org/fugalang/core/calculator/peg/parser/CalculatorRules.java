package org.fugalang.core.calculator.peg.parser;

import org.fugalang.core.parser.ParserRule;

import static org.fugalang.core.parser.ParserRule.and_rule;
import static org.fugalang.core.parser.ParserRule.or_rule;

public class CalculatorRules {
    public static final ParserRule SUM = or_rule("sum");
    public static final ParserRule SUM_1 = and_rule("sum:1");
    public static final ParserRule SUM_2 = and_rule("sum:2");
    public static final ParserRule TERM = or_rule("term");
    public static final ParserRule TERM_1 = and_rule("term:1");
    public static final ParserRule TERM_2 = and_rule("term:2");
    public static final ParserRule TERM_3 = and_rule("term:3");
    public static final ParserRule FACTOR = or_rule("factor");
    public static final ParserRule FACTOR_1 = and_rule("factor:1");
    public static final ParserRule FACTOR_2 = and_rule("factor:2");
    public static final ParserRule FACTOR_3 = and_rule("factor:3");
    public static final ParserRule POWER = or_rule("power");
    public static final ParserRule POWER_1 = and_rule("power:1");
    public static final ParserRule ATOM = or_rule("atom");
    public static final ParserRule ATOM_1 = and_rule("atom:1");
}
