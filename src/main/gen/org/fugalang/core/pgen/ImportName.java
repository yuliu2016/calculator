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
        return get(1, DottedAsNames::of);
    }
}
