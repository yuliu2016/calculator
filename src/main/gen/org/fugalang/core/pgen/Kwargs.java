package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * kwargs: '**' 'typed_arg' [',']
 */
public final class Kwargs extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("kwargs", RuleType.Conjunction);

    public static Kwargs of(ParseTreeNode node) {
        return new Kwargs(node);
    }

    private Kwargs(ParseTreeNode node) {
        super(RULE, node);
    }

    public TypedArg typedArg() {
        return get(1, TypedArg::of);
    }

    public boolean isComma() {
        return is(2);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("**");
        r = r && TypedArg.parse(t, lv + 1);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }
}
