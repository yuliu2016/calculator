package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;
import org.fugalang.core.parser.ParseTree;

/**
 * import_stmt: 'import_name' | 'import_from'
 */
public final class ImportStmt extends DisjunctionRule {
    public static final String RULE_NAME = "import_stmt";

    private final ImportName importName;
    private final ImportFrom importFrom;

    public ImportStmt(
            ImportName importName,
            ImportFrom importFrom
    ) {
        this.importName = importName;
        this.importFrom = importFrom;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("importName", importName);
        addChoice("importFrom", importFrom);
    }

    public ImportName importName() {
        return importName;
    }

    public boolean hasImportName() {
        return importName() != null;
    }

    public ImportFrom importFrom() {
        return importFrom;
    }

    public boolean hasImportFrom() {
        return importFrom() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = ImportName.parse(parseTree, level + 1);
        result = result || ImportFrom.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
