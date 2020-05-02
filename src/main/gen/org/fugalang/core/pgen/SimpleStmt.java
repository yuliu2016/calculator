package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
 */
public final class SimpleStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("simple_stmt", RuleType.Conjunction, true);

    private final SmallStmt smallStmt;
    private final List<SimpleStmt2> simpleStmt2List;
    private final boolean isTokenSemicolon;

    public SimpleStmt(
            SmallStmt smallStmt,
            List<SimpleStmt2> simpleStmt2List,
            boolean isTokenSemicolon
    ) {
        this.smallStmt = smallStmt;
        this.simpleStmt2List = simpleStmt2List;
        this.isTokenSemicolon = isTokenSemicolon;
    }

    @Override
    protected void buildRule() {
        addRequired("smallStmt", smallStmt());
        addRequired("simpleStmt2List", simpleStmt2List());
        addRequired("isTokenSemicolon", isTokenSemicolon());
    }

    public SmallStmt smallStmt() {
        return smallStmt;
    }

    public List<SimpleStmt2> simpleStmt2List() {
        return simpleStmt2List;
    }

    public boolean isTokenSemicolon() {
        return isTokenSemicolon;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = SmallStmt.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!SimpleStmt2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(";");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ';' 'small_stmt'
     */
    public static final class SimpleStmt2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("simple_stmt:2", RuleType.Conjunction, false);

        private final boolean isTokenSemicolon;
        private final SmallStmt smallStmt;

        public SimpleStmt2(
                boolean isTokenSemicolon,
                SmallStmt smallStmt
        ) {
            this.isTokenSemicolon = isTokenSemicolon;
            this.smallStmt = smallStmt;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenSemicolon", isTokenSemicolon());
            addRequired("smallStmt", smallStmt());
        }

        public boolean isTokenSemicolon() {
            return isTokenSemicolon;
        }

        public SmallStmt smallStmt() {
            return smallStmt;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(";");
            result = result && SmallStmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
