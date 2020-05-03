package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * varargslist: 'vfpdef' ['=' 'expr'] (',' 'vfpdef' ['=' 'expr'])*
 */
public final class Varargslist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("varargslist", RuleType.Conjunction, true);

    public static Varargslist of(ParseTreeNode node) {
        return new Varargslist(node);
    }

    private Varargslist(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Varargslist3> varargslist3List;

    @Override
    protected void buildRule() {
        addRequired(vfpdef());
        addOptional(varargslist2());
        addRequired(varargslist3List());
    }

    public Vfpdef vfpdef() {
        var element = getItem(0);
        element.failIfAbsent(Vfpdef.RULE);
        return Vfpdef.of(element);
    }

    public Varargslist2 varargslist2() {
        var element = getItem(1);
        if (!element.isPresent(Varargslist2.RULE)) {
            return null;
        }
        return Varargslist2.of(element);
    }

    public boolean hasVarargslist2() {
        return varargslist2() != null;
    }

    public List<Varargslist3> varargslist3List() {
        if (varargslist3List != null) {
            return varargslist3List;
        }
        List<Varargslist3> result = null;
        var element = getItem(2);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(Varargslist3.of(node));
        }
        varargslist3List = result == null ? Collections.emptyList() : result;
        return varargslist3List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Vfpdef.parse(parseTree, level + 1);
        Varargslist2.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Varargslist3.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '=' 'expr'
     */
    public static final class Varargslist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("varargslist:2", RuleType.Conjunction, false);

        public static Varargslist2 of(ParseTreeNode node) {
            return new Varargslist2(node);
        }

        private Varargslist2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenAssign());
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

    /**
     * ',' 'vfpdef' ['=' 'expr']
     */
    public static final class Varargslist3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("varargslist:3", RuleType.Conjunction, false);

        public static Varargslist3 of(ParseTreeNode node) {
            return new Varargslist3(node);
        }

        private Varargslist3(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenComma());
            addRequired(vfpdef());
            addOptional(varargslist33());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Vfpdef vfpdef() {
            var element = getItem(1);
            element.failIfAbsent(Vfpdef.RULE);
            return Vfpdef.of(element);
        }

        public Varargslist33 varargslist33() {
            var element = getItem(2);
            if (!element.isPresent(Varargslist33.RULE)) {
                return null;
            }
            return Varargslist33.of(element);
        }

        public boolean hasVarargslist33() {
            return varargslist33() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Vfpdef.parse(parseTree, level + 1);
            Varargslist33.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '=' 'expr'
     */
    public static final class Varargslist33 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("varargslist:3:3", RuleType.Conjunction, false);

        public static Varargslist33 of(ParseTreeNode node) {
            return new Varargslist33(node);
        }

        private Varargslist33(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenAssign());
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
