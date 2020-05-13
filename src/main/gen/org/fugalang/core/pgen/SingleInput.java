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
        return getItemOfType(0,TokenType.NEWLINE);
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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(TokenType.NEWLINE);
        result = result || SimpleStmt.parse(parseTree, level + 1);
        result = result || SingleInput3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
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
            return getItemOfType(1,TokenType.NEWLINE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = CompoundStmt.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(TokenType.NEWLINE);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
