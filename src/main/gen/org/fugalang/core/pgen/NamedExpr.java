package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * named_expr: 'NAME' ':=' 'expr' | 'expr'
 */
public final class NamedExpr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("named_expr", RuleType.Disjunction);

    public static NamedExpr of(ParseTreeNode node) {
        return new NamedExpr(node);
    }

    private NamedExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExpr1 nameExpr() {
        return get(0, NamedExpr1::of);
    }

    public boolean hasNameExpr() {
        return has(0, NamedExpr1.RULE);
    }

    public Expr expr() {
        return get(1, Expr::of);
    }

    public boolean hasExpr() {
        return has(1, Expr.RULE);
    }

    /**
     * 'NAME' ':=' 'expr'
     */
    public static final class NamedExpr1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("named_expr:1", RuleType.Conjunction);

        public static NamedExpr1 of(ParseTreeNode node) {
            return new NamedExpr1(node);
        }

        private NamedExpr1(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public Expr expr() {
            return get(2, Expr::of);
        }
    }
}
