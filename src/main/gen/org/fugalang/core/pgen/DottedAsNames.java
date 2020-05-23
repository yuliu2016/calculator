package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

import java.util.List;

/**
 * dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')*
 */
public final class DottedAsNames extends NodeWrapper {

    public DottedAsNames(ParseTreeNode node) {
        super(FugaRules.DOTTED_AS_NAMES, node);
    }

    public DottedAsName dottedAsName() {
        return get(0, DottedAsName.class);
    }

    public List<DottedAsNames2> dottedAsNames() {
        return getList(1, DottedAsNames2.class);
    }

    /**
     * ',' 'dotted_as_name'
     */
    public static final class DottedAsNames2 extends NodeWrapper {

        public DottedAsNames2(ParseTreeNode node) {
            super(FugaRules.DOTTED_AS_NAMES_2, node);
        }

        public DottedAsName dottedAsName() {
            return get(1, DottedAsName.class);
        }
    }
}
