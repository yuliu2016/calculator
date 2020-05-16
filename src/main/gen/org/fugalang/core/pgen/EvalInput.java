package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * eval_input: 'exprlist' 'NEWLINE'* 'ENDMARKER'
 */
public final class EvalInput extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("eval_input", RuleType.Conjunction);

    public static EvalInput of(ParseTreeNode node) {
        return new EvalInput(node);
    }

    private EvalInput(ParseTreeNode node) {
        super(RULE, node);
    }

    public Exprlist exprlist() {
        return get(0, Exprlist::of);
    }

    public List<String> newlines() {
        return getList(1, ParseTreeNode::asString);
    }

    public String endmarker() {
        return get(2, TokenType.ENDMARKER);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Exprlist.parse(t, lv + 1);
        if (r) parseNewlines(t, lv);
        r = r && t.consume(TokenType.ENDMARKER);
        t.exit(r);
        return r;
    }

    private static void parseNewlines(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!t.consume(TokenType.NEWLINE) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }
}
