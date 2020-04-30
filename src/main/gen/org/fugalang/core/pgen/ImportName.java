package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// import_name: 'import' 'dotted_as_names'
public final class ImportName extends ConjunctionRule {
    public static final String RULE_NAME = "import_name";

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
        setExplicitName(RULE_NAME);
        addRequired("isTokenImport", isTokenImport);
        addRequired("dottedAsNames", dottedAsNames);
    }

    public boolean isTokenImport() {
        return isTokenImport;
    }

    public DottedAsNames dottedAsNames() {
        return dottedAsNames;
    }
}
