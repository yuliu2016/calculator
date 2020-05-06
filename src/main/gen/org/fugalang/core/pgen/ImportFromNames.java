package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<Boolean> isTokenDotList;

    @Override
    protected void buildRule() {
        addChoice(importFromNames1OrNull());
        addChoice(isTokenDotList());
    }

    public ImportFromNames1 importFromNames1() {
        var element = getItem(0);
        element.failIfAbsent(ImportFromNames1.RULE);
        return ImportFromNames1.of(element);
    }

    public ImportFromNames1 importFromNames1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(ImportFromNames1.RULE)) {
            return null;
        }
        return ImportFromNames1.of(element);
    }

    public boolean hasImportFromNames1() {
        var element = getItem(0);
        return element.isPresent(ImportFromNames1.RULE);
    }

    public List<Boolean> isTokenDotList() {
        if (isTokenDotList != null) {
            return isTokenDotList;
        }
        List<Boolean> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(node.asBoolean());
        }
        isTokenDotList = result == null ? Collections.emptyList() : result;
        return isTokenDotList;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ImportFromNames1.parse(parseTree, level + 1);
        parseTree.enterCollection();
        var firstItem = parseTree.consumeToken(".");
        result = result || firstItem;
        if (firstItem) while (true) {
            var pos = parseTree.position();
            if (!parseTree.consumeToken(".") ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
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

        private List<Boolean> isTokenDotList;

        @Override
        protected void buildRule() {
            addRequired(isTokenDotList());
            addRequired(dottedName());
        }

        public List<Boolean> isTokenDotList() {
            if (isTokenDotList != null) {
                return isTokenDotList;
            }
            List<Boolean> result = null;
            var element = getItem(0);
            for (var node : element.asCollection()) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(node.asBoolean());
            }
            isTokenDotList = result == null ? Collections.emptyList() : result;
            return isTokenDotList;
        }

        public DottedName dottedName() {
            var element = getItem(1);
            element.failIfAbsent(DottedName.RULE);
            return DottedName.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            parseTree.enterCollection();
            while (true) {
                var pos = parseTree.position();
                if (!parseTree.consumeToken(".") ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            result = DottedName.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
