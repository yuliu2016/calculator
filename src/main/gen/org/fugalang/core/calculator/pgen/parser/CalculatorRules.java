package org.fugalang.core.calculator.pgen.parser;

import org.fugalang.core.parser.ParserRule;

import static org.fugalang.core.parser.ParserRule.and_rule;
import static org.fugalang.core.parser.ParserRule.or_rule;

public class CalculatorRules {
    public static final ParserRule SUM = and_rule("sum");
    public static final ParserRule SUM_2 = and_rule("sum:2");
    public static final ParserRule SUM_2_1 = or_rule("sum:2:1");
    public static final ParserRule TERM = and_rule("term");
    public static final ParserRule TERM_2 = and_rule("term:2");
    public static final ParserRule TERM_2_1 = or_rule("term:2:1");
    public static final ParserRule FACTOR = or_rule("factor");
    public static final ParserRule FACTOR_1 = and_rule("factor:1");
    public static final ParserRule FACTOR_1_1 = or_rule("factor:1:1");
    public static final ParserRule POWER = and_rule("power");
    public static final ParserRule POWER_2 = and_rule("power:2");
    public static final ParserRule ATOM = or_rule("atom");
    public static final ParserRule ATOM_1 = and_rule("atom:1");
}
