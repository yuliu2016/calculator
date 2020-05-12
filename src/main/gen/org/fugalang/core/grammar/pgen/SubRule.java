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

    @Override
    protected void buildRule() {
        addChoice(subRule1OrNull());
        addChoice(subRule2OrNull());
        addChoice(tokenOrNull());
    }

    public SubRule1 subRule1() {
        var element = getItem(0);
        element.failIfAbsent(SubRule1.RULE);
        return SubRule1.of(element);
    }

    public SubRule1 subRule1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(SubRule1.RULE)) {
            return null;
        }
        return SubRule1.of(element);
    }

    public boolean hasSubRule1() {
        var element = getItem(0);
        return element.isPresent(SubRule1.RULE);
    }

    public SubRule2 subRule2() {
        var element = getItem(1);
        element.failIfAbsent(SubRule2.RULE);
        return SubRule2.of(element);
    }

    public SubRule2 subRule2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(SubRule2.RULE)) {
            return null;
        }
        return SubRule2.of(element);
    }

    public boolean hasSubRule2() {
        var element = getItem(1);
        return element.isPresent(SubRule2.RULE);
    }

    public String token() {
        var element = getItem(2);
        element.failIfAbsent(MetaTokenType.TOK);
        return element.asString();
    }

    public String tokenOrNull() {
        var element = getItem(2);
        if (!element.isPresent(MetaTokenType.TOK)) {
            return null;
        }
        return element.asString();
    }

    public boolean hasToken() {
        var element = getItem(2);
        return element.isPresent(MetaTokenType.TOK);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        @Override
        protected void buildRule() {
            addRequired(lpar());
            addRequired(orRule());
            addRequired(rpar());
        }

        public String lpar() {
            var element = getItem(0);
            element.failIfAbsent(MetaTokenType.LPAR);
            return element.asString();
        }

        public OrRule orRule() {
            var element = getItem(1);
            element.failIfAbsent(OrRule.RULE);
            return OrRule.of(element);
        }

        public String rpar() {
            var element = getItem(2);
            element.failIfAbsent(MetaTokenType.RPAR);
            return element.asString();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
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

        @Override
        protected void buildRule() {
            addRequired(lsqb());
            addRequired(orRule());
            addRequired(rsqb());
        }

        public String lsqb() {
            var element = getItem(0);
            element.failIfAbsent(MetaTokenType.LSQB);
            return element.asString();
        }

        public OrRule orRule() {
            var element = getItem(1);
            element.failIfAbsent(OrRule.RULE);
            return OrRule.of(element);
        }

        public String rsqb() {
            var element = getItem(2);
            element.failIfAbsent(MetaTokenType.RSQB);
            return element.asString();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
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
