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

    @Override
    protected void buildRule() {
        addRequired(typedArg());
        addOptional(defaultArg2OrNull());
    }

    public TypedArg typedArg() {
        var element = getItem(0);
        element.failIfAbsent(TypedArg.RULE);
        return TypedArg.of(element);
    }

    public DefaultArg2 defaultArg2() {
        var element = getItem(1);
        element.failIfAbsent(DefaultArg2.RULE);
        return DefaultArg2.of(element);
    }

    public DefaultArg2 defaultArg2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(DefaultArg2.RULE)) {
            return null;
        }
        return DefaultArg2.of(element);
    }

    public boolean hasDefaultArg2() {
        var element = getItem(1);
        return element.isPresent(DefaultArg2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        @Override
        protected void buildRule() {
            addRequired(isTokenAssign(), "=");
            addRequired(expr());
        }

        public boolean isTokenAssign() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
