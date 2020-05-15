package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * single_input: 'NEWLINE' | 'simple_stmt' | 'compound_stmt' 'NEWLINE'
 */
public final class SingleInput extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("single_input", RuleType.Disjunction, true);

    public static SingleInput of(ParseTreeNode node) {
        return new SingleInput(node);
    }

    private SingleInput(ParseTreeNode node) {
        super(RULE, node);
    }

    public String newline() {
        return getItemOfType(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return hasItemOfType(0, TokenType.NEWLINE);
    }

    public SimpleStmt simpleStmt() {
        return SimpleStmt.of(getItem(1));
    }

    public boolean hasSimpleStmt() {
        return hasItemOfRule(1, SimpleStmt.RULE);
    }

    public SingleInput3 singleInput3() {
        return SingleInput3.of(getItem(2));
    }

    public boolean hasSingleInput3() {
        return hasItemOfRule(2, SingleInput3.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken(TokenType.NEWLINE);
        r = r || SimpleStmt.parse(t, l + 1);
        r = r || SingleInput3.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'compound_stmt' 'NEWLINE'
     */
    public static final class SingleInput3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("single_input:3", RuleType.Conjunction, false);

        public static SingleInput3 of(ParseTreeNode node) {
            return new SingleInput3(node);
        }

        private SingleInput3(ParseTreeNode node) {
            super(RULE, node);
        }

        public CompoundStmt compoundStmt() {
            return CompoundStmt.of(getItem(0));
        }

        public String newline() {
            return getItemOfType(1, TokenType.NEWLINE);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = CompoundStmt.parse(t, l + 1);
            r = r && t.consumeToken(TokenType.NEWLINE);
            t.exit(r);
            return r;
        }
    }
}
