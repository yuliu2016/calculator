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
}
