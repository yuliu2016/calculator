package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * import_as_names: 'import_as_name' (',' 'import_as_name')*
 */
public final class ImportAsNames extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("import_as_names", RuleType.Conjunction);

    public static ImportAsNames of(ParseTreeNode node) {
        return new ImportAsNames(node);
    }

    private ImportAsNames(ParseTreeNode node) {
        super(RULE, node);
    }

    public ImportAsName importAsName() {
        return get(0, ImportAsName::of);
    }

    public List<ImportAsNames2> importAsNames() {
        return getList(1, ImportAsNames2::of);
    }

    /**
     * ',' 'import_as_name'
     */
    public static final class ImportAsNames2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("import_as_names:2", RuleType.Conjunction);

        public static ImportAsNames2 of(ParseTreeNode node) {
            return new ImportAsNames2(node);
        }

        private ImportAsNames2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ImportAsName importAsName() {
            return get(1, ImportAsName::of);
        }
    }
}
