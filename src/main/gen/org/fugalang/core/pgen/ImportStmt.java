package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_stmt: 'import_name' | 'import_from'
 */
public final class ImportStmt extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("import_stmt", RuleType.Disjunction, true);

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
        addChoice("importName", importName());
        addChoice("importFrom", importFrom());
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
