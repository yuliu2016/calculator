package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * element:
 * *   | directive
 * *   | rule
 */
public final class Element extends NodeWrapper {

    public Element(ParseTreeNode node) {
        super(node);
    }

    public Directive directive() {
        return new Directive(get(0));
    }

    public boolean hasDirective() {
        return has(0);
    }

    public Rule rule() {
        return new Rule(get(1));
    }

    public boolean hasRule() {
        return has(1);
    }
}
