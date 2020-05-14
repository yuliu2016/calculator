package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

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

    public Exprlist exprlist() {
        return Exprlist.of(getItem(0));
    }

    public List<String> newlineList() {
        return getList(1, ParseTreeNode::asString);
    }

    public String endmarker() {
        return getItemOfType(2,TokenType.ENDMARKER);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Exprlist.parse(parseTree, level + 1);
        if (result) parseNewlineList(parseTree, level + 1);
        result = result && parseTree.consumeToken(TokenType.ENDMARKER);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseNewlineList(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!parseTree.consumeToken(TokenType.NEWLINE)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }
}
