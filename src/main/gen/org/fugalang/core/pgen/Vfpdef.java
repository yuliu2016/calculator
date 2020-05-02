package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * vfpdef: 'NAME'
 */
public final class Vfpdef extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("vfpdef", RuleType.Conjunction, true);

    public static Vfpdef of(ParseTreeNode node) {
        return new Vfpdef(node);
    }

    private Vfpdef(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("name", name());
    }

    public String name() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return element.asString();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenType("NAME");

        parseTree.exit(level, marker, result);
        return result;
    }
}
