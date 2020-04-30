package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
public final class SimpleStmt extends ConjunctionRule {
    public static final String RULE_NAME = "simple_stmt";

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
        setExplicitName(RULE_NAME);
        addRequired("smallStmt", smallStmt);
        addRequired("simpleStmt2List", simpleStmt2List);
        addRequired("isTokenSemicolon", isTokenSemicolon);
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

    // ';' 'small_stmt'
    public static final class SimpleStmt2 extends ConjunctionRule {
        public static final String RULE_NAME = "simple_stmt:2";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenSemicolon", isTokenSemicolon);
            addRequired("smallStmt", smallStmt);
        }

        public boolean isTokenSemicolon() {
            return isTokenSemicolon;
        }

        public SmallStmt smallStmt() {
            return smallStmt;
        }
    }
}
