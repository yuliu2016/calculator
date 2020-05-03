package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')* [',']
 */
public final class DottedAsNames extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dotted_as_names", RuleType.Conjunction, true);

    public static DottedAsNames of(ParseTreeNode node) {
        return new DottedAsNames(node);
    }

    private DottedAsNames(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<DottedAsNames2> dottedAsNames2List;

    @Override
    protected void buildRule() {
        addRequired(dottedAsName());
        addRequired(dottedAsNames2List());
        addRequired(isTokenComma());
    }

    public DottedAsName dottedAsName() {
        var element = getItem(0);
        element.failIfAbsent(DottedAsName.RULE);
        return DottedAsName.of(element);
    }

    public List<DottedAsNames2> dottedAsNames2List() {
        if (dottedAsNames2List != null) {
            return dottedAsNames2List;
        }
        List<DottedAsNames2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(DottedAsNames2.of(node));
        }
        dottedAsNames2List = result == null ? Collections.emptyList() : result;
        return dottedAsNames2List;
    }

    public boolean isTokenComma() {
        var element = getItem(2);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DottedAsName.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!DottedAsNames2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'dotted_as_name'
     */
    public static final class DottedAsNames2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dotted_as_names:2", RuleType.Conjunction, false);

        public static DottedAsNames2 of(ParseTreeNode node) {
            return new DottedAsNames2(node);
        }

        private DottedAsNames2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenComma());
            addRequired(dottedAsName());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public DottedAsName dottedAsName() {
            var element = getItem(1);
            element.failIfAbsent(DottedAsName.RULE);
            return DottedAsName.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && DottedAsName.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
