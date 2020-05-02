package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * conjunction: 'inversion' ('and' 'inversion')*
 */
public final class Conjunction extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("conjunction", RuleType.Conjunction, true);

    private final Inversion inversion;
    private final List<Conjunction2> conjunction2List;

    public Conjunction(
            Inversion inversion,
            List<Conjunction2> conjunction2List
    ) {
        this.inversion = inversion;
        this.conjunction2List = conjunction2List;
    }

    @Override
    protected void buildRule() {
        addRequired("inversion", inversion());
        addRequired("conjunction2List", conjunction2List());
    }

    public Inversion inversion() {
        return inversion;
    }

    public List<Conjunction2> conjunction2List() {
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
        while (true) {
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
    public static final class Conjunction2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("conjunction:2", RuleType.Conjunction, false);

        private final boolean isTokenAnd;
        private final Inversion inversion;

        public Conjunction2(
                boolean isTokenAnd,
                Inversion inversion
        ) {
            this.isTokenAnd = isTokenAnd;
            this.inversion = inversion;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAnd", isTokenAnd());
            addRequired("inversion", inversion());
        }

        public boolean isTokenAnd() {
            return isTokenAnd;
        }

        public Inversion inversion() {
            return inversion;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("and");
            result = result && Inversion.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
