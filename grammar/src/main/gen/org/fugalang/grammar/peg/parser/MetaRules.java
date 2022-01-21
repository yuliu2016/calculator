package org.fugalang.grammar.peg.parser;

import org.fugalang.core.parser.ParserRule;

import static org.fugalang.core.parser.ParserRule.and_rule;
import static org.fugalang.core.parser.ParserRule.or_rule;

public class MetaRules {
    public static final ParserRule GRAMMAR = and_rule("grammar");
    public static final ParserRule ELEMENT = or_rule("element");
    public static final ParserRule DIRECTIVE = and_rule("directive");
    public static final ParserRule ARGUMENTS = and_rule("arguments");
    public static final ParserRule ARGUMENT = and_rule("argument");
    public static final ParserRule RULE = and_rule("rule");
    public static final ParserRule RETURN_TYPE = and_rule("return_type");
    public static final ParserRule RULE_ARGS = and_rule("rule_args");
    public static final ParserRule RULE_ARG = and_rule("rule_arg");
    public static final ParserRule RULE_ARG_2 = and_rule("rule_arg:2");
    public static final ParserRule RULE_SUITE = and_rule("rule_suite");
    public static final ParserRule ALT_LIST = and_rule("alt_list");
    public static final ParserRule ALTERNATIVE = and_rule("alternative");
    public static final ParserRule SEQUENCE = and_rule("sequence");
    public static final ParserRule INLINE_HINT = and_rule("inline_hint");
    public static final ParserRule RESULT_EXPR = and_rule("result_expr");
    public static final ParserRule EXPR_NAME = and_rule("expr_name");
    public static final ParserRule EXPR_ARG = or_rule("expr_arg");
    public static final ParserRule EXPR_ARG_1 = and_rule("expr_arg:1");
    public static final ParserRule EXPR_CALL = and_rule("expr_call");
    public static final ParserRule EXPR_CALL_3 = and_rule("expr_call:3");
    public static final ParserRule EXPRESSION = or_rule("expression");
    public static final ParserRule GROUP = and_rule("group");
    public static final ParserRule OPTIONAL = and_rule("optional");
    public static final ParserRule SMALL_OPTIONAL = and_rule("small_optional");
    public static final ParserRule SMALL_OPTIONAL_1 = or_rule("small_optional:1");
    public static final ParserRule DELIMITED = and_rule("delimited");
    public static final ParserRule CUSTOM_MATCH = and_rule("custom_match");
    public static final ParserRule PRIMARY = or_rule("primary");
    public static final ParserRule PRIMARY_2 = and_rule("primary:2");
    public static final ParserRule PRIMARY_3 = and_rule("primary:3");
    public static final ParserRule PRIMARY_4 = and_rule("primary:4");
    public static final ParserRule PRIMARY_5 = and_rule("primary:5");
    public static final ParserRule ITEM = or_rule("item");
}
