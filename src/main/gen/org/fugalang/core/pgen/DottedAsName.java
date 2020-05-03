package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

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
        addRequired(dottedName());
        addOptional(dottedAsName2());
    }

    public DottedName dottedName() {
        var element = getItem(0);
        element.failIfAbsent(DottedName.RULE);
        return DottedName.of(element);
    }

    public DottedAsName2 dottedAsName2() {
        var element = getItem(1);
        if (!element.isPresent(DottedAsName2.RULE)) {
            return null;
        }
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
            addRequired(isTokenAs());
            addRequired(name());
        }

        public boolean isTokenAs() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            element.failIfAbsent(TokenType.NAME);
            return element.asString();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("as");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
