package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_name: 'import' 'dotted_as_names'
 */
public final class ImportName extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("import_name", RuleType.Conjunction, true);

    public static ImportName of(ParseTreeNode node) {
        return new ImportName(node);
    }

    private ImportName(ParseTreeNode node) {
        super(RULE, node);
    }

    public DottedAsNames dottedAsNames() {
        return DottedAsNames.of(getItem(1));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("import");
        result = result && DottedAsNames.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
