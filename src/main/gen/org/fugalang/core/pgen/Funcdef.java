package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * funcdef: 'def' ['func_type_hint'] ['func_args'] 'func_suite'
 */
public final class Funcdef extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("funcdef", RuleType.Conjunction);

    public static Funcdef of(ParseTreeNode node) {
        return new Funcdef(node);
    }

    private Funcdef(ParseTreeNode node) {
        super(RULE, node);
    }

    public FuncTypeHint funcTypeHint() {
        return FuncTypeHint.of(getItem(1));
    }

    public boolean hasFuncTypeHint() {
        return hasItemOfRule(1, FuncTypeHint.RULE);
    }

    public FuncArgs funcArgs() {
        return FuncArgs.of(getItem(2));
    }

    public boolean hasFuncArgs() {
        return hasItemOfRule(2, FuncArgs.RULE);
    }

    public FuncSuite funcSuite() {
        return FuncSuite.of(getItem(3));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("def");
        if (r) FuncTypeHint.parse(t, lv + 1);
        if (r) FuncArgs.parse(t, lv + 1);
        r = r && FuncSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
