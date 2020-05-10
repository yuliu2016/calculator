package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * or_rule: 'and_rule' ('|' 'and_rule')*
 */
public final class OrRule extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("or_rule", RuleType.Conjunction, true);

    public static OrRule of(ParseTreeNode node) {
        return new OrRule(node);
    }

    private OrRule(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<OrRule2> orRule2List;

    @Override
    protected void buildRule() {
        addRequired(andRule());
        addRequired(orRule2List());
    }

    public AndRule andRule() {
        var element = getItem(0);
        element.failIfAbsent(AndRule.RULE);
        return AndRule.of(element);
    }

    public List<OrRule2> orRule2List() {
        if (orRule2List != null) {
            return orRule2List;
        }
        List<OrRule2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(OrRule2.of(node));
        }
        orRule2List = result == null ? Collections.emptyList() : result;
        return orRule2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = AndRule.parse(parseTree, level + 1);
        if (result) parseOrRule2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseOrRule2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!OrRule2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * '|' 'and_rule'
     */
    public static final class OrRule2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("or_rule:2", RuleType.Conjunction, false);

        public static OrRule2 of(ParseTreeNode node) {
            return new OrRule2(node);
        }

        private OrRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenOr(), "|");
            addRequired(andRule());
        }

        public boolean isTokenOr() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public AndRule andRule() {
            var element = getItem(1);
            element.failIfAbsent(AndRule.RULE);
            return AndRule.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("|");
            result = result && AndRule.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
