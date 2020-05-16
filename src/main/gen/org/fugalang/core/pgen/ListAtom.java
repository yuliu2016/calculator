package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * list_atom: '[' ['named_expr_list'] ']'
 */
public final class ListAtom extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("list_atom", RuleType.Conjunction);

    public static ListAtom of(ParseTreeNode node) {
        return new ListAtom(node);
    }

    private ListAtom(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExprList namedExprList() {
        return get(1, NamedExprList::of);
    }

    public boolean hasNamedExprList() {
        return has(1, NamedExprList.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("[");
        if (r) NamedExprList.parse(t, lv + 1);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }
}
