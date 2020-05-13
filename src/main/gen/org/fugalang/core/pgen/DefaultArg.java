package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * default_arg: 'typed_arg' ['=' 'expr']
 */
public final class DefaultArg extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("default_arg", RuleType.Conjunction, true);

    public static DefaultArg of(ParseTreeNode node) {
        return new DefaultArg(node);
    }

    private DefaultArg(ParseTreeNode node) {
        super(RULE, node);
    }

    public TypedArg typedArg() {
        return TypedArg.of(getItem(0));
    }

    public DefaultArg2 defaultArg2() {
        return DefaultArg2.of(getItem(1));
    }

    public boolean hasDefaultArg2() {
        return hasItemOfRule(1, DefaultArg2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = TypedArg.parse(parseTree, level + 1);
        if (result) DefaultArg2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '=' 'expr'
     */
    public static final class DefaultArg2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("default_arg:2", RuleType.Conjunction, false);

        public static DefaultArg2 of(ParseTreeNode node) {
            return new DefaultArg2(node);
        }

        private DefaultArg2(ParseTreeNode node) {
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
