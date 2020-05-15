package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_name: 'import' 'dotted_as_names'
 */
public final class ImportName extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("import_name", RuleType.Conjunction);

    public static ImportName of(ParseTreeNode node) {
        return new ImportName(node);
    }

    private ImportName(ParseTreeNode node) {
        super(RULE, node);
    }

    public DottedAsNames dottedAsNames() {
        return DottedAsNames.of(get(1));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("import");
        r = r && DottedAsNames.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
