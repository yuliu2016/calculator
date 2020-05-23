package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * subscript: '[' 'slicelist' ']'
 */
public final class Subscript extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("subscript", RuleType.Conjunction);

    public static Subscript of(ParseTreeNode node) {
        return new Subscript(node);
    }

    private Subscript(ParseTreeNode node) {
        super(RULE, node);
    }

    public Slicelist slicelist() {
        return get(1, Slicelist::of);
    }
}
