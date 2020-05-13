package org.fugalang.core.grammar.pgen;

import org.fugalang.core.grammar.token.MetaTokenType;
import org.fugalang.core.parser.*;

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

    public AndRule andRule() {
        return AndRule.of(getItem(0));
    }

    public List<OrRule2> orRule2List() {
        return getList(1, OrRule2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
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

        public String or() {
            return getItemOfType(0,MetaTokenType.OR);
        }

        public AndRule andRule() {
            return AndRule.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(MetaTokenType.OR);
            result = result && AndRule.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
