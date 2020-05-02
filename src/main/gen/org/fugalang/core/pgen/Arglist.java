package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * arglist: 'argument' (',' 'argument')* [',']
 */
public final class Arglist extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("arglist", RuleType.Conjunction, true);

    private final Argument argument;
    private final List<Arglist2> arglist2List;
    private final boolean isTokenComma;

    public Arglist(
            Argument argument,
            List<Arglist2> arglist2List,
            boolean isTokenComma
    ) {
        this.argument = argument;
        this.arglist2List = arglist2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        addRequired("argument", argument());
        addRequired("arglist2List", arglist2List());
        addRequired("isTokenComma", isTokenComma());
    }

    public Argument argument() {
        return argument;
    }

    public List<Arglist2> arglist2List() {
        return arglist2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Argument.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Arglist2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'argument'
     */
    public static final class Arglist2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("arglist:2", RuleType.Conjunction, false);

        private final boolean isTokenComma;
        private final Argument argument;

        public Arglist2(
                boolean isTokenComma,
                Argument argument
        ) {
            this.isTokenComma = isTokenComma;
            this.argument = argument;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("argument", argument());
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Argument argument() {
            return argument;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && Argument.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
