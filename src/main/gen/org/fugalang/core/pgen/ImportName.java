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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("import");
        r = r && DottedAsNames.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
