package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
public final class SimpleStmt extends ConjunctionRule {
    private final SmallStmt smallStmt;
    private final List<SimpleStmt2Group> simpleStmt2GroupList;
    private final boolean isTokenSemicolon;

    public SimpleStmt(
            SmallStmt smallStmt,
            List<SimpleStmt2Group> simpleStmt2GroupList,
            boolean isTokenSemicolon
    ) {
        this.smallStmt = smallStmt;
        this.simpleStmt2GroupList = simpleStmt2GroupList;
        this.isTokenSemicolon = isTokenSemicolon;

        addRequired("smallStmt", smallStmt);
        addRequired("simpleStmt2GroupList", simpleStmt2GroupList);
        addRequired("isTokenSemicolon", isTokenSemicolon);
    }

    public SmallStmt getSmallStmt() {
        return smallStmt;
    }

    public List<SimpleStmt2Group> getSimpleStmt2GroupList() {
        return simpleStmt2GroupList;
    }

    public boolean getIsTokenSemicolon() {
        return isTokenSemicolon;
    }

    // ';' 'small_stmt'
    public static final class SimpleStmt2Group extends ConjunctionRule {
        private final boolean isTokenSemicolon;
        private final SmallStmt smallStmt;

        public SimpleStmt2Group(
                boolean isTokenSemicolon,
                SmallStmt smallStmt
        ) {
            this.isTokenSemicolon = isTokenSemicolon;
            this.smallStmt = smallStmt;

            addRequired("isTokenSemicolon", isTokenSemicolon);
            addRequired("smallStmt", smallStmt);
        }

        public boolean getIsTokenSemicolon() {
            return isTokenSemicolon;
        }

        public SmallStmt getSmallStmt() {
            return smallStmt;
        }
    }
}
