package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * sub_rule: group | optional | NAME | STRING
 */
public final class SubRule extends NodeWrapper {

    public SubRule(ParseTreeNode node) {
        super(node);
    }

    public Group group() {
        return get(0, Group.class);
    }

    public boolean hasGroup() {
        return has(0);
    }

    public Optional optional() {
        return get(1, Optional.class);
    }

    public boolean hasOptional() {
        return has(1);
    }

    public String name() {
        return get(2, TokenType.NAME);
    }

    public boolean hasName() {
        return has(2);
    }

    public String string() {
        return get(3, TokenType.STRING);
    }

    public boolean hasString() {
        return has(3);
    }
}
