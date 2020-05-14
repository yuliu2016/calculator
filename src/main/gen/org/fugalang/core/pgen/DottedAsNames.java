package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')*
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

    public DottedAsName dottedAsName() {
        return DottedAsName.of(getItem(0));
    }

    public List<DottedAsNames2> dottedAsNames2List() {
        return getList(1, DottedAsNames2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DottedAsName.parse(parseTree, level + 1);
        if (result) parseDottedAsNames2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseDottedAsNames2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!DottedAsNames2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
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

        public DottedAsName dottedAsName() {
            return DottedAsName.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && DottedAsName.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
