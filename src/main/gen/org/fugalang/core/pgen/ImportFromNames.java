package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * import_from_names: '.'* 'dotted_name' | '.'+
 */
public final class ImportFromNames extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("import_from_names", RuleType.Disjunction, true);

    public static ImportFromNames of(ParseTreeNode node) {
        return new ImportFromNames(node);
    }

    private ImportFromNames(ParseTreeNode node) {
        super(RULE, node);
    }

    public ImportFromNames1 importFromNames1() {
        return ImportFromNames1.of(getItem(0));
    }

    public boolean hasImportFromNames1() {
        return hasItemOfRule(0, ImportFromNames1.RULE);
    }

    public List<Boolean> isTokenDotList() {
        return getList(1, ParseTreeNode::asBoolean);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ImportFromNames1.parse(parseTree, level + 1);
        result = result || parseIsTokenDotList(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static boolean parseIsTokenDotList(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enterCollection();
        var result = parseTree.consumeToken(".");
        if (result) while (true) {
            var pos = parseTree.position();
            if (!parseTree.consumeToken(".")) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
        return result;
    }

    /**
     * '.'* 'dotted_name'
     */
    public static final class ImportFromNames1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("import_from_names:1", RuleType.Conjunction, false);

        public static ImportFromNames1 of(ParseTreeNode node) {
            return new ImportFromNames1(node);
        }

        private ImportFromNames1(ParseTreeNode node) {
            super(RULE, node);
        }

        public List<Boolean> isTokenDotList() {
            return getList(0, ParseTreeNode::asBoolean);
        }

        public DottedName dottedName() {
            return DottedName.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            parseIsTokenDotList(parseTree, level + 1);
            result = DottedName.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }

        private static void parseIsTokenDotList(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return;
            parseTree.enterCollection();
            while (true) {
                var pos = parseTree.position();
                if (!parseTree.consumeToken(".")) break;
                if (parseTree.guardLoopExit(pos)) break;
            }
            parseTree.exitCollection();
        }
    }
}
