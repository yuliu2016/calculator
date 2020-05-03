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

    @Override
    protected void buildRule() {
        addChoice(newline());
        addChoice(simpleStmt());
        addChoice(singleInput3());
    }

    public String newline() {
        var element = getItem(0);
        if (!element.isPresent(TokenType.NEWLINE)) {
            return null;
        }
        return element.asString();
    }

    public boolean hasNewline() {
        return newline() != null;
    }

    public SimpleStmt simpleStmt() {
        var element = getItem(1);
        if (!element.isPresent(SimpleStmt.RULE)) {
            return null;
        }
        return SimpleStmt.of(element);
    }

    public boolean hasSimpleStmt() {
        return simpleStmt() != null;
    }

    public SingleInput3 singleInput3() {
        var element = getItem(2);
        if (!element.isPresent(SingleInput3.RULE)) {
            return null;
        }
        return SingleInput3.of(element);
    }

    public boolean hasSingleInput3() {
        return singleInput3() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        @Override
        protected void buildRule() {
            addRequired(compoundStmt());
            addRequired(newline());
        }

        public CompoundStmt compoundStmt() {
            var element = getItem(0);
            element.failIfAbsent(CompoundStmt.RULE);
            return CompoundStmt.of(element);
        }

        public String newline() {
            var element = getItem(1);
            element.failIfAbsent(TokenType.NEWLINE);
            return element.asString();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = CompoundStmt.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(TokenType.NEWLINE);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
