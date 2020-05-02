package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * eval_input: 'exprlist' 'NEWLINE'* 'ENDMARKER'
 */
public final class EvalInput extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("eval_input", RuleType.Conjunction, true);

    public static EvalInput of(ParseTreeNode node) {
        return new EvalInput(node);
    }

    private EvalInput(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<String> newlineList;

    @Override
    protected void buildRule() {
        addRequired("exprlist", exprlist());
        addRequired("newlineList", newlineList());
        addRequired("endmarker", endmarker());
    }

    public Exprlist exprlist() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Exprlist.of(element);
    }

    public List<String> newlineList() {
        if (newlineList != null) {
            return newlineList;
        }
        List<String> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(node.asString());
        }
        newlineList = result == null ? Collections.emptyList() : result;
        return newlineList;
    }

    public String endmarker() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return element.asString();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
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
