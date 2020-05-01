package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import java.util.Optional;

// varargslist: 'vfpdef' ['=' 'expr'] (',' 'vfpdef' ['=' 'expr'])*
public final class Varargslist extends ConjunctionRule {
    public static final String RULE_NAME = "varargslist";

    private final Vfpdef vfpdef;
    private final Varargslist2 varargslist2;
    private final List<Varargslist3> varargslist3List;

    public Varargslist(
            Vfpdef vfpdef,
            Varargslist2 varargslist2,
            List<Varargslist3> varargslist3List
    ) {
        this.vfpdef = vfpdef;
        this.varargslist2 = varargslist2;
        this.varargslist3List = varargslist3List;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("vfpdef", vfpdef);
        addOptional("varargslist2", varargslist2);
        addRequired("varargslist3List", varargslist3List);
    }

    public Vfpdef vfpdef() {
        return vfpdef;
    }

    public Optional<Varargslist2> varargslist2() {
        return Optional.ofNullable(varargslist2);
    }

    public List<Varargslist3> varargslist3List() {
        return varargslist3List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Vfpdef.parse(parseTree, level + 1);
        Varargslist2.parse(parseTree, level + 1);
        while (true) {
            if (!Varargslist3.parse(parseTree, level + 1)) {
                break;
            }
        }

        parseTree.exit(level, marker, result);
        return result;
    }

    // '=' 'expr'
    public static final class Varargslist2 extends ConjunctionRule {
        public static final String RULE_NAME = "varargslist:2";

        private final boolean isTokenAssign;
        private final Expr expr;

        public Varargslist2(
                boolean isTokenAssign,
                Expr expr
        ) {
            this.isTokenAssign = isTokenAssign;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenAssign", isTokenAssign);
            addRequired("expr", expr);
        }

        public boolean isTokenAssign() {
            return isTokenAssign;
        }

        public Expr expr() {
            return expr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // ',' 'vfpdef' ['=' 'expr']
    public static final class Varargslist3 extends ConjunctionRule {
        public static final String RULE_NAME = "varargslist:3";

        private final boolean isTokenComma;
        private final Vfpdef vfpdef;
        private final Varargslist33 varargslist33;

        public Varargslist3(
                boolean isTokenComma,
                Vfpdef vfpdef,
                Varargslist33 varargslist33
        ) {
            this.isTokenComma = isTokenComma;
            this.vfpdef = vfpdef;
            this.varargslist33 = varargslist33;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("vfpdef", vfpdef);
            addOptional("varargslist33", varargslist33);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Vfpdef vfpdef() {
            return vfpdef;
        }

        public Optional<Varargslist33> varargslist33() {
            return Optional.ofNullable(varargslist33);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && Vfpdef.parse(parseTree, level + 1);
            Varargslist33.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '=' 'expr'
    public static final class Varargslist33 extends ConjunctionRule {
        public static final String RULE_NAME = "varargslist:3:3";

        private final boolean isTokenAssign;
        private final Expr expr;

        public Varargslist33(
                boolean isTokenAssign,
                Expr expr
        ) {
            this.isTokenAssign = isTokenAssign;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenAssign", isTokenAssign);
            addRequired("expr", expr);
        }

        public boolean isTokenAssign() {
            return isTokenAssign;
        }

        public Expr expr() {
            return expr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("=");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
