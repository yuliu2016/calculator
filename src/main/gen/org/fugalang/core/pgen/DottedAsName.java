package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * dotted_as_name: 'dotted_name' ['as' 'NAME']
 */
public final class DottedAsName extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("dotted_as_name", RuleType.Conjunction, true);

    private final DottedName dottedName;
    private final DottedAsName2 dottedAsName2;

    public DottedAsName(
            DottedName dottedName,
            DottedAsName2 dottedAsName2
    ) {
        this.dottedName = dottedName;
        this.dottedAsName2 = dottedAsName2;
    }

    @Override
    protected void buildRule() {
        addRequired("dottedName", dottedName());
        addOptional("dottedAsName2", dottedAsName2());
    }

    public DottedName dottedName() {
        return dottedName;
    }

    public DottedAsName2 dottedAsName2() {
        return dottedAsName2;
    }

    public boolean hasDottedAsName2() {
        return dottedAsName2() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DottedName.parse(parseTree, level + 1);
        DottedAsName2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'as' 'NAME'
     */
    public static final class DottedAsName2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("dotted_as_name:2", RuleType.Conjunction, false);

        private final boolean isTokenAs;
        private final String name;

        public DottedAsName2(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAs", isTokenAs());
            addRequired("name", name());
        }

        public boolean isTokenAs() {
            return isTokenAs;
        }

        public String name() {
            return name;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("as");
            result = result && parseTree.consumeTokenType("NAME");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
