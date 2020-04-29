package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// with_item: 'expr' ['as' 'NAME']
public final class WithItem extends ConjunctionRule {
    private final Expr expr;
    private final WithItem2Group withItem2Group;

    public WithItem(
            Expr expr,
            WithItem2Group withItem2Group
    ) {
        this.expr = expr;
        this.withItem2Group = withItem2Group;
    }

    public Expr getExpr() {
        return expr;
    }

    public Optional<WithItem2Group> getWithItem2Group() {
        return Optional.ofNullable(withItem2Group);
    }

    // 'as' 'NAME'
    public static final class WithItem2Group extends ConjunctionRule {
        private final boolean isTokenAs;
        private final Object name;

        public WithItem2Group(
                boolean isTokenAs,
                Object name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        public boolean getIsTokenAs() {
            return isTokenAs;
        }

        public Object getName() {
            return name;
        }
    }
}
