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

    @Override
    protected void buildRule() {
        addRequired(isTokenLsqb(), "[");
        addRequired(slicelist());
        addRequired(isTokenRsqb(), "]");
    }

    public boolean isTokenLsqb() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Slicelist slicelist() {
        var element = getItem(1);
        element.failIfAbsent(Slicelist.RULE);
        return Slicelist.of(element);
    }

    public boolean isTokenRsqb() {
        var element = getItem(2);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("[");
        result = result && Slicelist.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("]");

        parseTree.exit(level, marker, result);
        return result;
    }
}
