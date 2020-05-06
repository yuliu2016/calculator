package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * import_as_names: 'import_as_name' (',' 'import_as_name')*
 */
public final class ImportAsNames extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("import_as_names", RuleType.Conjunction, true);

    public static ImportAsNames of(ParseTreeNode node) {
        return new ImportAsNames(node);
    }

    private ImportAsNames(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<ImportAsNames2> importAsNames2List;

    @Override
    protected void buildRule() {
        addRequired(importAsName());
        addRequired(importAsNames2List());
    }

    public ImportAsName importAsName() {
        var element = getItem(0);
        element.failIfAbsent(ImportAsName.RULE);
        return ImportAsName.of(element);
    }

    public List<ImportAsNames2> importAsNames2List() {
        if (importAsNames2List != null) {
            return importAsNames2List;
        }
        List<ImportAsNames2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(ImportAsNames2.of(node));
        }
        importAsNames2List = result == null ? Collections.emptyList() : result;
        return importAsNames2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ImportAsName.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!ImportAsNames2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'import_as_name'
     */
    public static final class ImportAsNames2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("import_as_names:2", RuleType.Conjunction, false);

        public static ImportAsNames2 of(ParseTreeNode node) {
            return new ImportAsNames2(node);
        }

        private ImportAsNames2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenComma(), ",");
            addRequired(importAsName());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public ImportAsName importAsName() {
            var element = getItem(1);
            element.failIfAbsent(ImportAsName.RULE);
            return ImportAsName.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && ImportAsName.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
