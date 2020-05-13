package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * simple_arg: 'NAME' ['=' 'expr']
 */
public final class SimpleArg extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("simple_arg", RuleType.Conjunction, true);

    public static SimpleArg of(ParseTreeNode node) {
        return new SimpleArg(node);
    }

    private SimpleArg(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return getItemOfType(0,TokenType.NAME);
    }

    public SimpleArg2 simpleArg2() {
        return SimpleArg2.of(getItem(1));
    }

    public boolean hasSimpleArg2() {
        return hasItemOfRule(1, SimpleArg2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(TokenType.NAME);
        if (result) SimpleArg2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '=' 'expr'
     */
    public static final class SimpleArg2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("simple_arg:2", RuleType.Conjunction, false);

        public static SimpleArg2 of(ParseTreeNode node) {
            return new SimpleArg2(node);
        }

        private SimpleArg2(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenAssign() {
            return true;
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
