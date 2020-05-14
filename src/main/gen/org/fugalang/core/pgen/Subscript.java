package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * subscript: '[' 'slicelist' ']'
 */
public final class Subscript extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("subscript", RuleType.Conjunction, true);

    public static Subscript of(ParseTreeNode node) {
        return new Subscript(node);
    }

    private Subscript(ParseTreeNode node) {
        super(RULE, node);
    }

    public Slicelist slicelist() {
        return Slicelist.of(getItem(1));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("[");
        result = result && Slicelist.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("]");

        parseTree.exit(level, marker, result);
        return result;
    }
}
