package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * dotted_as_name: 'dotted_name' ['as' 'NAME']
 */
public final class DottedAsName extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("dotted_as_name", RuleType.Conjunction);

    public static DottedAsName of(ParseTreeNode node) {
        return new DottedAsName(node);
    }

    private DottedAsName(ParseTreeNode node) {
        super(RULE, node);
    }

    public DottedName dottedName() {
        return get(0, DottedName::of);
    }

    public DottedAsName2 asName() {
        return get(1, DottedAsName2::of);
    }

    public boolean hasAsName() {
        return has(1, DottedAsName2.RULE);
    }

    /**
     * 'as' 'NAME'
     */
    public static final class DottedAsName2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("dotted_as_name:2", RuleType.Conjunction);

        public static DottedAsName2 of(ParseTreeNode node) {
            return new DottedAsName2(node);
        }

        private DottedAsName2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
