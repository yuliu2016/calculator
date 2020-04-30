package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.DisjunctionRule;

// import_stmt: 'import_name' | 'import_from'
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

    public ImportFrom importFrom() {
        return importFrom;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }
}
