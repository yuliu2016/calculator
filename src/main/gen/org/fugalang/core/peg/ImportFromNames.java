package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * import_from_names: dotted_name | '.'+ [dotted_name]
 */
public final class ImportFromNames extends NodeWrapper {

    public ImportFromNames(ParseTreeNode node) {
        super(node);
    }

    public DottedName dottedName() {
        return new DottedName(get(0));
    }

    public boolean hasDottedName() {
        return has(0);
    }

    public ImportFromNames2 importFromNames2() {
        return new ImportFromNames2(get(1));
    }

    public boolean hasImportFromNames2() {
        return has(1);
    }

    /**
     * '.'+ [dotted_name]
     */
    public static final class ImportFromNames2 extends NodeWrapper {

        public ImportFromNames2(ParseTreeNode node) {
            super(node);
        }

        public List<Boolean> isDots() {
            return getList(0, ParseTreeNode::asBoolean);
        }

        public DottedName dottedName() {
            return new DottedName(get(1));
        }

        public boolean hasDottedName() {
            return has(1);
        }
    }
}
