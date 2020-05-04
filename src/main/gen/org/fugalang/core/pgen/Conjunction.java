package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * conjunction: 'inversion' ('and' 'inversion')*
 */
public final class Conjunction extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("conjunction", RuleType.Conjunction, true);

    public static Conjunction of(ParseTreeNode node) {
        return new Conjunction(node);
    }

    private Conjunction(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Conjunction2> conjunction2List;

    @Override
    protected void buildRule() {
        addRequired(inversion());
        addRequired(conjunction2List());
    }

    public Inversion inversion() {
        var element = getItem(0);
        element.failIfAbsent(Inversion.RULE);
        return Inversion.of(element);
    }

    public List<Conjunction2> conjunction2List() {
        if (conjunction2List != null) {
            return conjunction2List;
        }
        List<Conjunction2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(Conjunction2.of(node));
        }
        conjunction2List = result == null ? Collections.emptyList() : result;
        return conjunction2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Inversion.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!Conjunction2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'and' 'inversion'
     */
    public static final class Conjunction2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("conjunction:2", RuleType.Conjunction, false);

        public static Conjunction2 of(ParseTreeNode node) {
            return new Conjunction2(node);
        }

        private Conjunction2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenAnd(), "and");
            addRequired(inversion());
        }

        public boolean isTokenAnd() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Inversion inversion() {
            var element = getItem(1);
            element.failIfAbsent(Inversion.RULE);
            return Inversion.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("and");
            result = result && Inversion.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
