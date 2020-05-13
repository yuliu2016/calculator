package org.fugalang.core.grammar.pgen;

import org.fugalang.core.grammar.token.MetaTokenType;
import org.fugalang.core.parser.*;

/**
 * sub_rule: '(' 'or_rule' ')' | '[' 'or_rule' ']' | 'TOK'
 */
public final class SubRule extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("sub_rule", RuleType.Disjunction, true);

    public static SubRule of(ParseTreeNode node) {
        return new SubRule(node);
    }

    private SubRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public SubRule1 subRule1() {
        return SubRule1.of(getItem(0));
    }

    public boolean hasSubRule1() {
        return hasItemOfRule(0, SubRule1.RULE);
    }

    public SubRule2 subRule2() {
        return SubRule2.of(getItem(1));
    }

    public boolean hasSubRule2() {
        return hasItemOfRule(1, SubRule2.RULE);
    }

    public String token() {
        return getItemOfType(2,MetaTokenType.TOK);
    }

    public boolean hasToken() {
        return hasItemOfType(2, MetaTokenType.TOK);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = SubRule1.parse(parseTree, level + 1);
        result = result || SubRule2.parse(parseTree, level + 1);
        result = result || parseTree.consumeToken(MetaTokenType.TOK);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '(' 'or_rule' ')'
     */
    public static final class SubRule1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("sub_rule:1", RuleType.Conjunction, false);

        public static SubRule1 of(ParseTreeNode node) {
            return new SubRule1(node);
        }

        private SubRule1(ParseTreeNode node) {
            super(RULE, node);
        }

        public String lpar() {
            return getItemOfType(0,MetaTokenType.LPAR);
        }

        public OrRule orRule() {
            return OrRule.of(getItem(1));
        }

        public String rpar() {
            return getItemOfType(2,MetaTokenType.RPAR);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(MetaTokenType.LPAR);
            result = result && OrRule.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(MetaTokenType.RPAR);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '[' 'or_rule' ']'
     */
    public static final class SubRule2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("sub_rule:2", RuleType.Conjunction, false);

        public static SubRule2 of(ParseTreeNode node) {
            return new SubRule2(node);
        }

        private SubRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String lsqb() {
            return getItemOfType(0,MetaTokenType.LSQB);
        }

        public OrRule orRule() {
            return OrRule.of(getItem(1));
        }

        public String rsqb() {
            return getItemOfType(2,MetaTokenType.RSQB);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(MetaTokenType.LSQB);
            result = result && OrRule.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(MetaTokenType.RSQB);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
