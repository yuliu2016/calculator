package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * import_as_names: import_as_name (',' import_as_name)*
 */
public final class ImportAsNames extends NodeWrapper {

    public ImportAsNames(ParseTreeNode node) {
        super(node);
    }

    public ImportAsName importAsName() {
        return get(0, ImportAsName.class);
    }

    public List<ImportAsNames2> commaImportAsNames() {
        return getList(1, ImportAsNames2.class);
    }

    /**
     * ',' import_as_name
     */
    public static final class ImportAsNames2 extends NodeWrapper {

        public ImportAsNames2(ParseTreeNode node) {
            super(node);
        }

        public ImportAsName importAsName() {
            return get(1, ImportAsName.class);
        }
    }
}
