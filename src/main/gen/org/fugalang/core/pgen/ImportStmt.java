package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_stmt: 'import_name' | 'import_from'
 */
public final class ImportStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("import_stmt", RuleType.Disjunction, true);

    public static ImportStmt of(ParseTreeNode node) {
        return new ImportStmt(node);
    }

    private ImportStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(importNameOrNull());
        addChoice(importFromOrNull());
    }

    public ImportName importName() {
        var element = getItem(0);
        element.failIfAbsent(ImportName.RULE);
        return ImportName.of(element);
    }

    public ImportName importNameOrNull() {
        var element = getItem(0);
        if (!element.isPresent(ImportName.RULE)) {
            return null;
        }
        return ImportName.of(element);
    }

    public boolean hasImportName() {
        var element = getItem(0);
        return element.isPresent(ImportName.RULE);
    }

    public ImportFrom importFrom() {
        var element = getItem(1);
        element.failIfAbsent(ImportFrom.RULE);
        return ImportFrom.of(element);
    }

    public ImportFrom importFromOrNull() {
        var element = getItem(1);
        if (!element.isPresent(ImportFrom.RULE)) {
            return null;
        }
        return ImportFrom.of(element);
    }

    public boolean hasImportFrom() {
        var element = getItem(1);
        return element.isPresent(ImportFrom.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ImportName.parse(parseTree, level + 1);
        result = result || ImportFrom.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
