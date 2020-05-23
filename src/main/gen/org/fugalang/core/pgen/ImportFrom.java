package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * import_from: 'from' 'import_from_names' 'import' ('*' | '(' 'import_as_names' [','] ')' | 'import_as_names')
 */
public final class ImportFrom extends NodeWrapper {

    public ImportFrom(ParseTreeNode node) {
        super(ParserRules.IMPORT_FROM, node);
    }

    public ImportFromNames importFromNames() {
        return get(1, ImportFromNames::new);
    }

    public ImportFrom4 importFrom4() {
        return get(3, ImportFrom4::new);
    }

    /**
     * '*' | '(' 'import_as_names' [','] ')' | 'import_as_names'
     */
    public static final class ImportFrom4 extends NodeWrapper {

        public ImportFrom4(ParseTreeNode node) {
            super(ParserRules.IMPORT_FROM_4, node);
        }

        public boolean isTimes() {
            return is(0);
        }

        public ImportFrom42 importFrom42() {
            return get(1, ImportFrom42::new);
        }

        public boolean hasImportFrom42() {
            return has(1, ParserRules.IMPORT_FROM_4_2);
        }

        public ImportAsNames importAsNames() {
            return get(2, ImportAsNames::new);
        }

        public boolean hasImportAsNames() {
            return has(2, ParserRules.IMPORT_AS_NAMES);
        }
    }

    /**
     * '(' 'import_as_names' [','] ')'
     */
    public static final class ImportFrom42 extends NodeWrapper {

        public ImportFrom42(ParseTreeNode node) {
            super(ParserRules.IMPORT_FROM_4_2, node);
        }

        public ImportAsNames importAsNames() {
            return get(1, ImportAsNames::new);
        }

        public boolean isComma() {
            return is(2);
        }
    }
}
