package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// subscriptlist: 'subscript' (',' 'subscript')* [',']
public final class Subscriptlist extends ConjunctionRule {
    public static final String RULE_NAME = "subscriptlist";

    private final Subscript subscript;
    private final List<Subscriptlist2> subscriptlist2List;
    private final boolean isTokenComma;

    public Subscriptlist(
            Subscript subscript,
            List<Subscriptlist2> subscriptlist2List,
            boolean isTokenComma
    ) {
        this.subscript = subscript;
        this.subscriptlist2List = subscriptlist2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("subscript", subscript);
        addRequired("subscriptlist2List", subscriptlist2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public Subscript subscript() {
        return subscript;
    }

    public List<Subscriptlist2> subscriptlist2List() {
        return subscriptlist2List;
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

        result = Subscript.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Subscriptlist2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    // ',' 'subscript'
    public static final class Subscriptlist2 extends ConjunctionRule {
        public static final String RULE_NAME = "subscriptlist:2";

        private final boolean isTokenComma;
        private final Subscript subscript;

        public Subscriptlist2(
                boolean isTokenComma,
                Subscript subscript
        ) {
            this.isTokenComma = isTokenComma;
            this.subscript = subscript;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("subscript", subscript);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Subscript subscript() {
            return subscript;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && Subscript.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
