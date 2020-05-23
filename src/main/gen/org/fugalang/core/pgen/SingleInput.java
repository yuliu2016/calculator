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
        return get(1, SimpleStmt::of);
    }

    public boolean hasSimpleStmt() {
        return has(1, SimpleStmt.RULE);
    }

    public SingleInput3 compoundStmtNewline() {
        return get(2, SingleInput3::of);
    }

    public boolean hasCompoundStmtNewline() {
        return has(2, SingleInput3.RULE);
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
            return get(0, CompoundStmt::of);
        }

        public String newline() {
            return get(1, TokenType.NEWLINE);
        }
    }
}
