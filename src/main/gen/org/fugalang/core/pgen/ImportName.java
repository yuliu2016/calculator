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

    @Override
    protected void buildRule() {
        addRequired("isTokenImport", isTokenImport());
        addRequired("dottedAsNames", dottedAsNames());
    }

    public boolean isTokenImport() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public DottedAsNames dottedAsNames() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return DottedAsNames.of(element);
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
