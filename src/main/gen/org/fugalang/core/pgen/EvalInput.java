package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.ParseTree;

import java.util.List;

/**
 * eval_input: 'exprlist' 'NEWLINE'* 'ENDMARKER'
 */
public final class EvalInput extends ConjunctionRule {
    public static final String RULE_NAME = "eval_input";

    private final Exprlist exprlist;
    private final List<Object> newlineList;
    private final Object endmarker;

    public EvalInput(
            Exprlist exprlist,
            List<Object> newlineList,
            Object endmarker
    ) {
        this.exprlist = exprlist;
        this.newlineList = newlineList;
        this.endmarker = endmarker;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("exprlist", exprlist);
        addRequired("newlineList", newlineList);
        addRequired("endmarker", endmarker);
    }

    public Exprlist exprlist() {
        return exprlist;
    }

    public List<Object> newlineList() {
        return newlineList;
    }

    public Object endmarker() {
        return endmarker;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Exprlist.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!parseTree.consumeTokenType("NEWLINE") ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenType("ENDMARKER");

        parseTree.exit(level, marker, result);
        return result;
    }
}
