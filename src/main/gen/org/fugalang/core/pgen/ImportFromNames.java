package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * import_from_names: 'dotted_name' | '.'+ ['dotted_name']
 */
public final class ImportFromNames extends NodeWrapper {

    public ImportFromNames(ParseTreeNode node) {
        super(node);
    }

    public DottedName dottedName() {
        return get(0, DottedName.class);
    }

    public boolean hasDottedName() {
        return has(0);
    }

    public ImportFromNames2 importFromNames2() {
        return get(1, ImportFromNames2.class);
    }

    public boolean hasImportFromNames2() {
        return has(1);
    }

    /**
     * '.'+ ['dotted_name']
     */
    public static final class ImportFromNames2 extends NodeWrapper {

        public ImportFromNames2(ParseTreeNode node) {
            super(node);
        }

        public List<Boolean> isDots() {
            return getList(0, ParseTreeNode::asBoolean);
        }

        public DottedName dottedName() {
            return get(1, DottedName.class);
        }

        public boolean hasDottedName() {
            return has(1);
        }
    }
}
