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
        return get(1, FuncTypeHint::of);
    }

    public boolean hasFuncTypeHint() {
        return has(1, FuncTypeHint.RULE);
    }

    public FuncArgs funcArgs() {
        return get(2, FuncArgs::of);
    }

    public boolean hasFuncArgs() {
        return has(2, FuncArgs.RULE);
    }

    public FuncSuite funcSuite() {
        return get(3, FuncSuite::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("def");
        if (r) FuncTypeHint.parse(t, lv + 1);
        if (r) FuncArgs.parse(t, lv + 1);
        r = r && FuncSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
