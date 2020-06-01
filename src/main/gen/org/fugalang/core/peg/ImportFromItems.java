package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * import_from_items: '*' | '(' import_as_names [','] ')' | import_as_names
 */
public final class ImportFromItems extends NodeWrapper {

    public ImportFromItems(ParseTreeNode node) {
        super(node);
    }

    public boolean isTimes() {
        return is(0);
    }

    public ImportFromItems2 importFromItems2() {
        return get(1, ImportFromItems2.class);
    }

    public boolean hasImportFromItems2() {
        return has(1);
    }

    public ImportAsNames importAsNames() {
        return get(2, ImportAsNames.class);
    }

    public boolean hasImportAsNames() {
        return has(2);
    }

    /**
     * '(' import_as_names [','] ')'
     */
    public static final class ImportFromItems2 extends NodeWrapper {

        public ImportFromItems2(ParseTreeNode node) {
            super(node);
        }

        public ImportAsNames importAsNames() {
            return get(1, ImportAsNames.class);
        }

        public boolean isComma() {
            return is(2);
        }
    }
}
