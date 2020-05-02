package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * rules: 'single_rule'+
 */
public final class Rules extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("rules", RuleType.Conjunction, true);

    private final List<SingleRule> singleRuleList;

    public Rules(
            List<SingleRule> singleRuleList
    ) {
        this.singleRuleList = singleRuleList;
    }

    @Override
    protected void buildRule() {
        addRequired("singleRuleList", singleRuleList());
    }

    public List<SingleRule> singleRuleList() {
        return singleRuleList;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        parseTree.enterCollection();
        result = SingleRule.parse(parseTree, level + 1);
        while (true) {
            var pos = parseTree.position();
            if (!SingleRule.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }
}
