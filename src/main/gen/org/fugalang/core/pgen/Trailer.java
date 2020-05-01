package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// trailer: '(' ['arglist'] ')' | '[' 'subscriptlist' ']' | '.' 'NAME' | 'block_suite'
public final class Trailer extends DisjunctionRule {
    public static final String RULE_NAME = "trailer";

    private final Trailer1 trailer1;
    private final Trailer2 trailer2;
    private final Trailer3 trailer3;
    private final BlockSuite blockSuite;

    public Trailer(
            Trailer1 trailer1,
            Trailer2 trailer2,
            Trailer3 trailer3,
            BlockSuite blockSuite
    ) {
        this.trailer1 = trailer1;
        this.trailer2 = trailer2;
        this.trailer3 = trailer3;
        this.blockSuite = blockSuite;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("trailer1", trailer1);
        addChoice("trailer2", trailer2);
        addChoice("trailer3", trailer3);
        addChoice("blockSuite", blockSuite);
    }

    public Trailer1 trailer1() {
        return trailer1;
    }

    public Trailer2 trailer2() {
        return trailer2;
    }

    public Trailer3 trailer3() {
        return trailer3;
    }

    public BlockSuite blockSuite() {
        return blockSuite;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Trailer1.parse(parseTree, level + 1);
        result = result || Trailer2.parse(parseTree, level + 1);
        result = result || Trailer3.parse(parseTree, level + 1);
        result = result || BlockSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    // '(' ['arglist'] ')'
    public static final class Trailer1 extends ConjunctionRule {
        public static final String RULE_NAME = "trailer:1";

        private final boolean isTokenLpar;
        private final Arglist arglist;
        private final boolean isTokenRpar;

        public Trailer1(
                boolean isTokenLpar,
                Arglist arglist,
                boolean isTokenRpar
        ) {
            this.isTokenLpar = isTokenLpar;
            this.arglist = arglist;
            this.isTokenRpar = isTokenRpar;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenLpar", isTokenLpar);
            addOptional("arglist", arglist);
            addRequired("isTokenRpar", isTokenRpar);
        }

        public boolean isTokenLpar() {
            return isTokenLpar;
        }

        public Optional<Arglist> arglist() {
            return Optional.ofNullable(arglist);
        }

        public boolean isTokenRpar() {
            return isTokenRpar;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("(");
            Arglist.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '[' 'subscriptlist' ']'
    public static final class Trailer2 extends ConjunctionRule {
        public static final String RULE_NAME = "trailer:2";

        private final boolean isTokenLsqb;
        private final Subscriptlist subscriptlist;
        private final boolean isTokenRsqb;

        public Trailer2(
                boolean isTokenLsqb,
                Subscriptlist subscriptlist,
                boolean isTokenRsqb
        ) {
            this.isTokenLsqb = isTokenLsqb;
            this.subscriptlist = subscriptlist;
            this.isTokenRsqb = isTokenRsqb;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenLsqb", isTokenLsqb);
            addRequired("subscriptlist", subscriptlist);
            addRequired("isTokenRsqb", isTokenRsqb);
        }

        public boolean isTokenLsqb() {
            return isTokenLsqb;
        }

        public Subscriptlist subscriptlist() {
            return subscriptlist;
        }

        public boolean isTokenRsqb() {
            return isTokenRsqb;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("[");
            result = result && Subscriptlist.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("]");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '.' 'NAME'
    public static final class Trailer3 extends ConjunctionRule {
        public static final String RULE_NAME = "trailer:3";

        private final boolean isTokenDot;
        private final String name;

        public Trailer3(
                boolean isTokenDot,
                String name
        ) {
            this.isTokenDot = isTokenDot;
            this.name = name;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenDot", isTokenDot);
            addRequired("name", name);
        }

        public boolean isTokenDot() {
            return isTokenDot;
        }

        public String name() {
            return name;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral(".");
            result = result && parseTree.consumeTokenType("NAME");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
