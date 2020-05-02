package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * funcdef: ['async'] 'def' ['varargslist'] (':' 'expr' | 'block_suite')
 */
public final class Funcdef extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("funcdef", RuleType.Conjunction, true);

    public static Funcdef of(ParseTreeNode node) {
        return new Funcdef(node);
    }

    private Funcdef(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenAsync", isTokenAsync());
        addRequired("isTokenDef", isTokenDef());
        addOptional("varargslist", varargslist());
        addRequired("funcdef4", funcdef4());
    }

    public boolean isTokenAsync() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public boolean isTokenDef() {
        var element = getItem(1);
        return element.asBoolean();
    }

    public Varargslist varargslist() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return Varargslist.of(element);
    }

    public boolean hasVarargslist() {
        return varargslist() != null;
    }

    public Funcdef4 funcdef4() {
        var element = getItem(3);
        if (!element.isPresent()) return null;
        return Funcdef4.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("async");
        result = result && parseTree.consumeTokenLiteral("def");
        Varargslist.parse(parseTree, level + 1);
        result = result && Funcdef4.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ':' 'expr' | 'block_suite'
     */
    public static final class Funcdef4 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("funcdef:4", RuleType.Disjunction, false);

        public static Funcdef4 of(ParseTreeNode node) {
            return new Funcdef4(node);
        }

        private Funcdef4(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice("funcdef41", funcdef41());
            addChoice("blockSuite", blockSuite());
        }

        public Funcdef41 funcdef41() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return Funcdef41.of(element);
        }

        public boolean hasFuncdef41() {
            return funcdef41() != null;
        }

        public BlockSuite blockSuite() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return BlockSuite.of(element);
        }

        public boolean hasBlockSuite() {
            return blockSuite() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Funcdef41.parse(parseTree, level + 1);
            result = result || BlockSuite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * ':' 'expr'
     */
    public static final class Funcdef41 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("funcdef:4:1", RuleType.Conjunction, false);

        public static Funcdef41 of(ParseTreeNode node) {
            return new Funcdef41(node);
        }

        private Funcdef41(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenColon", isTokenColon());
            addRequired("expr", expr());
        }

        public boolean isTokenColon() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(":");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
