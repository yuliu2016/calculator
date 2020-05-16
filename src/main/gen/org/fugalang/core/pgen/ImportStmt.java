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
        return get(0, ImportName::of);
    }

    public boolean hasImportName() {
        return has(0, ImportName.RULE);
    }

    public ImportFrom importFrom() {
        return get(1, ImportFrom::of);
    }

    public boolean hasImportFrom() {
        return has(1, ImportFrom.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = ImportName.parse(t, lv + 1);
        r = r || ImportFrom.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
