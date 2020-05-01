package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// rules: 'single_rule'*
public final class Rules extends ConjunctionRule {
    public static final String RULE_NAME = "rules";

    private final List<SingleRule> singleRuleList;

    public Rules(
            List<SingleRule> singleRuleList
    ) {
        this.singleRuleList = singleRuleList;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("singleRuleList", singleRuleList);
    }

    public List<SingleRule> singleRuleList() {
        return singleRuleList;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        parseTree.enterCollection();
        while (true) {
            if (!SingleRule.parse(parseTree, level + 1)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }
}
