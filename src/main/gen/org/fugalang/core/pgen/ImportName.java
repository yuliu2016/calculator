package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_name: 'import' 'dotted_as_names'
 */
public final class ImportName extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("import_name", RuleType.Conjunction, true);

    private final boolean isTokenImport;
    private final DottedAsNames dottedAsNames;

    public ImportName(
            boolean isTokenImport,
            DottedAsNames dottedAsNames
    ) {
        this.isTokenImport = isTokenImport;
        this.dottedAsNames = dottedAsNames;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenImport", isTokenImport());
        addRequired("dottedAsNames", dottedAsNames());
    }

    public boolean isTokenImport() {
        return isTokenImport;
    }

    public DottedAsNames dottedAsNames() {
        return dottedAsNames;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("import");
        result = result && DottedAsNames.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
