package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * dict_or_set: '{' ['dict_maker' | 'set_maker'] '}'
 */
public final class DictOrSet extends NodeWrapper {

    public DictOrSet(ParseTreeNode node) {
        super(ParserRules.DICT_OR_SET, node);
    }

    public DictOrSet2 dictMakerOrSetMaker() {
        return get(1, DictOrSet2::new);
    }

    public boolean hasDictMakerOrSetMaker() {
        return has(1);
    }

    /**
     * 'dict_maker' | 'set_maker'
     */
    public static final class DictOrSet2 extends NodeWrapper {

        public DictOrSet2(ParseTreeNode node) {
            super(ParserRules.DICT_OR_SET_2, node);
        }

        public DictMaker dictMaker() {
            return get(0, DictMaker::new);
        }

        public boolean hasDictMaker() {
            return has(0);
        }

        public SetMaker setMaker() {
            return get(1, SetMaker::new);
        }

        public boolean hasSetMaker() {
            return has(1);
        }
    }
}
