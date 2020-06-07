package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * import_from:
 * *   | 'from' import_from_names 'import' import_from_items
 */
public final class ImportFrom extends NodeWrapper {

    public ImportFrom(ParseTreeNode node) {
        super(node);
    }

    public ImportFromNames importFromNames() {
        return new ImportFromNames(get(1));
    }

    public ImportFromItems importFromItems() {
        return new ImportFromItems(get(3));
    }
}
