package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * import_from_names: '.'* 'dotted_name' | '.'+
 */
public final class ImportFromNames extends NodeWrapper {

    public ImportFromNames(ParseTreeNode node) {
        super(ParserRules.IMPORT_FROM_NAMES, node);
    }

    public ImportFromNames1 importFromNames1() {
        return get(0, ImportFromNames1::new);
    }

    public boolean hasImportFromNames1() {
        return has(0, ParserRules.IMPORT_FROM_NAMES_1);
    }

    public List<Boolean> isDots() {
        return getList(1, ParseTreeNode::asBoolean);
    }

    /**
     * '.'* 'dotted_name'
     */
    public static final class ImportFromNames1 extends NodeWrapper {

        public ImportFromNames1(ParseTreeNode node) {
            super(ParserRules.IMPORT_FROM_NAMES_1, node);
        }

        public List<Boolean> isDots() {
            return getList(0, ParseTreeNode::asBoolean);
        }

        public DottedName dottedName() {
            return get(1, DottedName::new);
        }
    }
}
