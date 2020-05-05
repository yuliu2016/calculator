package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<SingleRule> singleRuleList;

    @Override
    protected void buildRule() {
        addRequired(singleRuleList());
    }

    public List<SingleRule> singleRuleList() {
        if (singleRuleList != null) {
            return singleRuleList;
        }
        List<SingleRule> result = null;
        var element = getItem(0);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(SingleRule.of(node));
        }
        singleRuleList = result == null ? Collections.emptyList() : result;
        return singleRuleList;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        parseTree.enterCollection();
        var firstItem = SingleRule.parse(parseTree, level + 1);
        result = firstItem;
        if (firstItem) while (true) {
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
