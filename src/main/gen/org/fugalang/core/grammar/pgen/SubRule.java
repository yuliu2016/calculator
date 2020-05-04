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
        addChoice(subRule1());
        addChoice(subRule2());
        addChoice(token());
    }

    public SubRule1 subRule1() {
        var element = getItem(0);
        if (!element.isPresent(SubRule1.RULE)) {
            return null;
        }
        return SubRule1.of(element);
    }

    public boolean hasSubRule1() {
        return subRule1() != null;
    }

    public SubRule2 subRule2() {
        var element = getItem(1);
        if (!element.isPresent(SubRule2.RULE)) {
            return null;
        }
        return SubRule2.of(element);
    }

    public boolean hasSubRule2() {
        return subRule2() != null;
    }

    public String token() {
        var element = getItem(2);
        if (!element.isPresent(MetaTokenType.TOK)) {
            return null;
        }
        return element.asString();
    }

    public boolean hasToken() {
        return token() != null;
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
            addRequired(isTokenLpar(), "(");
            addRequired(orRule());
            addRequired(isTokenRpar(), ")");
        }

        public boolean isTokenLpar() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public OrRule orRule() {
            var element = getItem(1);
            element.failIfAbsent(OrRule.RULE);
            return OrRule.of(element);
        }

        public boolean isTokenRpar() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("(");
            result = result && OrRule.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(")");

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
            addRequired(isTokenLsqb(), "[");
            addRequired(orRule());
            addRequired(isTokenRsqb(), "]");
        }

        public boolean isTokenLsqb() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public OrRule orRule() {
            var element = getItem(1);
            element.failIfAbsent(OrRule.RULE);
            return OrRule.of(element);
        }

        public boolean isTokenRsqb() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("[");
            result = result && OrRule.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken("]");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
