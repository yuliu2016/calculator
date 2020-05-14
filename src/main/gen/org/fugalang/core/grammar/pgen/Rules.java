package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * rules: 'single_rule'+
 */
public final class Rules extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("rules", RuleType.Conjunction, true);

    public static Rules of(ParseTreeNode node) {
        return new Rules(node);
    }

    private Rules(ParseTreeNode node) {
        super(RULE, node);
    }

    public List<SingleRule> singleRuleList() {
        return getList(0, SingleRule::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseSingleRuleList(parseTree, level);

        parseTree.exit(result);
        return result;
    }

    private static boolean parseSingleRuleList(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        var result = SingleRule.parse(parseTree, level + 1);
        if (result) while (true) {
            var pos = parseTree.position();
            if (!SingleRule.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
        return result;
    }
}
