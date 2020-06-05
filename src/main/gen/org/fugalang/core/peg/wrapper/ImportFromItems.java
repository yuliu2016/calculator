package org.fugalang.core.peg.wrapper;

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
        return new ImportFromItems2(get(1));
    }

    public boolean hasImportFromItems2() {
        return has(1);
    }

    public ImportAsNames importAsNames() {
        return new ImportAsNames(get(2));
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
            return new ImportAsNames(get(1));
        }

        public boolean isComma() {
            return is(2);
        }
    }
}
