package org.fugalang.core.grammar.pgen.parser;

import org.fugalang.core.parser.ParserRule;

import static org.fugalang.core.parser.ParserRule.and_rule;
import static org.fugalang.core.parser.ParserRule.or_rule;

public class MetaRules {
    public static final ParserRule RULES = and_rule("rules");
    public static final ParserRule SINGLE_RULE = and_rule("single_rule");
    public static final ParserRule SINGLE_RULE_3 = and_rule("single_rule:3");
    public static final ParserRule OR_RULE = and_rule("or_rule");
    public static final ParserRule OR_RULE_2 = and_rule("or_rule:2");
    public static final ParserRule AND_RULE = and_rule("and_rule");
    public static final ParserRule REPEAT_RULE = and_rule("repeat_rule");
    public static final ParserRule REPEAT_RULE_2 = or_rule("repeat_rule:2");
    public static final ParserRule SUB_RULE = or_rule("sub_rule");
    public static final ParserRule GROUP = and_rule("group");
    public static final ParserRule OPTIONAL = and_rule("optional");
}
