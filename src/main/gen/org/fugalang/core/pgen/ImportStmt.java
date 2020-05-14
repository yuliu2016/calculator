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

    public ImportName importName() {
        return ImportName.of(getItem(0));
    }

    public boolean hasImportName() {
        return hasItemOfRule(0, ImportName.RULE);
    }

    public ImportFrom importFrom() {
        return ImportFrom.of(getItem(1));
    }

    public boolean hasImportFrom() {
        return hasItemOfRule(1, ImportFrom.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = ImportName.parse(parseTree, level + 1);
        result = result || ImportFrom.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
