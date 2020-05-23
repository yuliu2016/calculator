package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * import_from_names: '.'* 'dotted_name' | '.'+
 */
public final class ImportFromNames extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("import_from_names", RuleType.Disjunction);

    public static ImportFromNames of(ParseTreeNode node) {
        return new ImportFromNames(node);
    }

    private ImportFromNames(ParseTreeNode node) {
        super(RULE, node);
    }

    public ImportFromNames1 importFromNames1() {
        return get(0, ImportFromNames1::of);
    }

    public boolean hasImportFromNames1() {
        return has(0, ImportFromNames1.RULE);
    }

    public List<Boolean> isDots() {
        return getList(1, ParseTreeNode::asBoolean);
    }

    /**
     * '.'* 'dotted_name'
     */
    public static final class ImportFromNames1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("import_from_names:1", RuleType.Conjunction);

        public static ImportFromNames1 of(ParseTreeNode node) {
            return new ImportFromNames1(node);
        }

        private ImportFromNames1(ParseTreeNode node) {
            super(RULE, node);
        }

        public List<Boolean> isDots() {
            return getList(0, ParseTreeNode::asBoolean);
        }

        public DottedName dottedName() {
            return get(1, DottedName::of);
        }
    }
}
