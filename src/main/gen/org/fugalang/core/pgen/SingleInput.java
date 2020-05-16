package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * single_input: 'NEWLINE' | 'simple_stmt' | 'compound_stmt' 'NEWLINE'
 */
public final class SingleInput extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("single_input", RuleType.Disjunction);

    public static SingleInput of(ParseTreeNode node) {
        return new SingleInput(node);
    }

    private SingleInput(ParseTreeNode node) {
        super(RULE, node);
    }

    public String newline() {
        return get(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return has(0, TokenType.NEWLINE);
    }

    public SimpleStmt simpleStmt() {
        return SimpleStmt.of(get(1));
    }

    public boolean hasSimpleStmt() {
        return has(1, SimpleStmt.RULE);
    }

    public SingleInput3 compoundStmtNewline() {
        return SingleInput3.of(get(2));
    }

    public boolean hasCompoundStmtNewline() {
        return has(2, SingleInput3.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume(TokenType.NEWLINE);
        r = r || SimpleStmt.parse(t, lv + 1);
        r = r || SingleInput3.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'compound_stmt' 'NEWLINE'
     */
    public static final class SingleInput3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("single_input:3", RuleType.Conjunction);

        public static SingleInput3 of(ParseTreeNode node) {
            return new SingleInput3(node);
        }

        private SingleInput3(ParseTreeNode node) {
            super(RULE, node);
        }

        public CompoundStmt compoundStmt() {
            return CompoundStmt.of(get(0));
        }

        public String newline() {
            return get(1, TokenType.NEWLINE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = CompoundStmt.parse(t, lv + 1);
            r = r && t.consume(TokenType.NEWLINE);
            t.exit(r);
            return r;
        }
    }
}
