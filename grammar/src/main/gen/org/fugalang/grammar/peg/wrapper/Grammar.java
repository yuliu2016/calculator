package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * grammar:
 * *   | [NEWLINE] element+
 */
public final class Grammar extends NodeWrapper {

    public Grammar(ParseTreeNode node) {
        super(node);
    }

    public String newline() {
        return get(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return has(0);
    }

    public List<Element> elements() {
        return getList(1, Element::new);
    }
}
