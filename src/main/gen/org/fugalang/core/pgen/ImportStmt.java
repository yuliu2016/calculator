package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_stmt: 'import_name' | 'import_from'
 */
public final class ImportStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("import_stmt", RuleType.Disjunction);

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

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = ImportName.parse(t, lv + 1);
        r = r || ImportFrom.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
