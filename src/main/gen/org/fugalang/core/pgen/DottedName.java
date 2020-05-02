package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * dotted_name: 'NAME' ('.' 'NAME')*
 */
public final class DottedName extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dotted_name", RuleType.Conjunction, true);

    public static DottedName of(ParseTreeNode node) {
        return new DottedName(node);
    }

    private DottedName(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<DottedName2> dottedName2List;

    @Override
    protected void buildRule() {
        addRequired("name", name());
        addRequired("dottedName2List", dottedName2List());
    }

    public String name() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return (String) element.asObject();
    }

    public List<DottedName2> dottedName2List() {
        if (dottedName2List != null) {
            return dottedName2List;
        }
        List<DottedName2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(DottedName2.of(node));
        }
        dottedName2List = result == null ? Collections.emptyList() : result;
        return dottedName2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenType("NAME");
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!DottedName2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '.' 'NAME'
     */
    public static final class DottedName2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dotted_name:2", RuleType.Conjunction, false);

        public static DottedName2 of(ParseTreeNode node) {
            return new DottedName2(node);
        }

        private DottedName2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenDot", isTokenDot());
            addRequired("name", name());
        }

        public boolean isTokenDot() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return (String) element.asObject();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(".");
            result = result && parseTree.consumeTokenType("NAME");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
