package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')*
 */
public final class DottedAsNames extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("dotted_as_names", RuleType.Conjunction);

    public static DottedAsNames of(ParseTreeNode node) {
        return new DottedAsNames(node);
    }

    private DottedAsNames(ParseTreeNode node) {
        super(RULE, node);
    }

    public DottedAsName dottedAsName() {
        return get(0, DottedAsName::of);
    }

    public List<DottedAsNames2> dottedAsNames() {
        return getList(1, DottedAsNames2::of);
    }

    /**
     * ',' 'dotted_as_name'
     */
    public static final class DottedAsNames2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("dotted_as_names:2", RuleType.Conjunction);

        public static DottedAsNames2 of(ParseTreeNode node) {
            return new DottedAsNames2(node);
        }

        private DottedAsNames2(ParseTreeNode node) {
            super(RULE, node);
        }

        public DottedAsName dottedAsName() {
            return get(1, DottedAsName::of);
        }
    }
}
