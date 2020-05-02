package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * dotted_as_name: 'dotted_name' ['as' 'NAME']
 */
public final class DottedAsName extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dotted_as_name", RuleType.Conjunction, true);

    public static DottedAsName of(ParseTreeNode node) {
        return new DottedAsName(node);
    }

    private DottedAsName(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("dottedName", dottedName());
        addOptional("dottedAsName2", dottedAsName2());
    }

    public DottedName dottedName() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return DottedName.of(element);
    }

    public DottedAsName2 dottedAsName2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return DottedAsName2.of(element);
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
    public static final class DottedAsName2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dotted_as_name:2", RuleType.Conjunction, false);

        public static DottedAsName2 of(ParseTreeNode node) {
            return new DottedAsName2(node);
        }

        private DottedAsName2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAs", isTokenAs());
            addRequired("name", name());
        }

        public boolean isTokenAs() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return element.asString();
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
