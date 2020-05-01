package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// rules: 'single_rule'+
public final class Rules extends ConjunctionRule {
    public static final String RULE_NAME = "rules";

    private final SingleRule singleRule;
    private final List<SingleRule> singleRuleList;

    public Rules(
            SingleRule singleRule,
            List<SingleRule> singleRuleList
    ) {
        this.singleRule = singleRule;
        this.singleRuleList = singleRuleList;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("singleRule", singleRule);
        addRequired("singleRuleList", singleRuleList);
    }

    public SingleRule singleRule() {
        return singleRule;
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

        result = SingleRule.parse(parseTree, level + 1);
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
