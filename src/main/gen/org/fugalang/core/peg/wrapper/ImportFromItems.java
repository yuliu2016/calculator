package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * import_from_items:
 * *   | '*'
 * *   | import_as_names_sp
 * *   | import_as_names
 */
public final class ImportFromItems extends NodeWrapper {

    public ImportFromItems(ParseTreeNode node) {
        super(node);
    }

    public boolean isTimes() {
        return is(0);
    }

    public ImportAsNamesSp importAsNamesSp() {
        return new ImportAsNamesSp(get(1));
    }

    public boolean hasImportAsNamesSp() {
        return has(1);
    }

    public ImportAsNames importAsNames() {
        return new ImportAsNames(get(2));
    }

    public boolean hasImportAsNames() {
        return has(2);
    }
}
