package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * vfpdef: 'NAME'
 */
public final class Vfpdef extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("vfpdef", RuleType.Conjunction, true);

    private final String name;

    public Vfpdef(
            String name
    ) {
        this.name = name;
    }

    @Override
    protected void buildRule() {
        addRequired("name", name());
    }

    public String name() {
        return name;
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
