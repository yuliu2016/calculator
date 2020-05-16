package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * tuple_atom: '(' ['named_expr_list'] ')'
 */
public final class TupleAtom extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("tuple_atom", RuleType.Conjunction);

    public static TupleAtom of(ParseTreeNode node) {
        return new TupleAtom(node);
    }

    private TupleAtom(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExprList namedExprList() {
        return NamedExprList.of(get(1));
    }

    public boolean hasNamedExprList() {
        return has(1, NamedExprList.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("(");
        if (r) NamedExprList.parse(t, lv + 1);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }
}
