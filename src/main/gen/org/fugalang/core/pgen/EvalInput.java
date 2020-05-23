package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * eval_input: exprlist NEWLINE* ENDMARKER
 */
public final class EvalInput extends NodeWrapper {

    public EvalInput(ParseTreeNode node) {
        super(node);
    }

    public Exprlist exprlist() {
        return get(0, Exprlist.class);
    }

    public List<String> newlines() {
        return getList(1, ParseTreeNode::asString);
    }

    public String endmarker() {
        return get(2, TokenType.ENDMARKER);
    }
}
