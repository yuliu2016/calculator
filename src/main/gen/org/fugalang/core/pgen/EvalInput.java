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
        return getItemOfType(2, TokenType.ENDMARKER);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Exprlist.parse(t, l + 1);
        if (r) parseNewlineList(t, l);
        r = r && t.consumeToken(TokenType.ENDMARKER);
        t.exit(r);
        return r;
    }

    private static void parseNewlineList(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!t.consumeToken(TokenType.NEWLINE)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }
}
