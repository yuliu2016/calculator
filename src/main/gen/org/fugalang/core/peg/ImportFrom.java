package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * import_from: 'from' import_from_names 'import' import_from_items
 */
public final class ImportFrom extends NodeWrapper {

    public ImportFrom(ParseTreeNode node) {
        super(node);
    }

    public ImportFromNames importFromNames() {
        return get(1, ImportFromNames.class);
    }

    public ImportFromItems importFromItems() {
        return get(3, ImportFromItems.class);
    }
}
