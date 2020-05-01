package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// arglist: 'argument' (',' 'argument')* [',']
public final class Arglist extends ConjunctionRule {
    public static final String RULE_NAME = "arglist";

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
        setExplicitName(RULE_NAME);
        addRequired("argument", argument);
        addRequired("arglist2List", arglist2List);
        addRequired("isTokenComma", isTokenComma);
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
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
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

    // ',' 'argument'
    public static final class Arglist2 extends ConjunctionRule {
        public static final String RULE_NAME = "arglist:2";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("argument", argument);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Argument argument() {
            return argument;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && Argument.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
