package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * import_from_names: '.'* 'dotted_name' | '.'+
 */
public final class ImportFromNames extends NodeWrapper {

    public ImportFromNames(ParseTreeNode node) {
        super(node);
    }

    public ImportFromNames1 importFromNames1() {
        return get(0, ImportFromNames1.class);
    }

    public boolean hasImportFromNames1() {
        return has(0);
    }

    public List<Boolean> isDots() {
        return getList(1, ParseTreeNode::asBoolean);
    }

    /**
     * '.'* 'dotted_name'
     */
    public static final class ImportFromNames1 extends NodeWrapper {

        public ImportFromNames1(ParseTreeNode node) {
            super(node);
        }

        public List<Boolean> isDots() {
            return getList(0, ParseTreeNode::asBoolean);
        }

        public DottedName dottedName() {
            return get(1, DottedName.class);
        }
    }
}
