package org.fugalang.grammar.peg.parser;

import org.fugalang.core.parser.ParserRule;

import static org.fugalang.core.parser.ParserRule.and_rule;
import static org.fugalang.core.parser.ParserRule.or_rule;

public class MetaRules {
    public static final ParserRule GRAMMAR = and_rule("grammar");
    public static final ParserRule RULE = and_rule("rule");
    public static final ParserRule RULE_ARGS = and_rule("rule_args");
    public static final ParserRule RULE_ARG = and_rule("rule_arg");
    public static final ParserRule RULE_ARG_2 = and_rule("rule_arg:2");
    public static final ParserRule RULE_SUITE = and_rule("rule_suite");
    public static final ParserRule ALT_LIST = and_rule("alt_list");
    public static final ParserRule ALT_LIST_2 = and_rule("alt_list:2");
    public static final ParserRule SEQUENCE = and_rule("sequence");
    public static final ParserRule PRIMARY = or_rule("primary");
    public static final ParserRule PRIMARY_2 = and_rule("primary:2");
    public static final ParserRule PRIMARY_3 = and_rule("primary:3");
    public static final ParserRule PRIMARY_4 = and_rule("primary:4");
    public static final ParserRule PRIMARY_5 = and_rule("primary:5");
    public static final ParserRule ITEM = or_rule("item");
    public static final ParserRule GROUP = and_rule("group");
    public static final ParserRule OPTIONAL = and_rule("optional");
    public static final ParserRule DELIMITED = and_rule("delimited");
}
