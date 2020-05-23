package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * dict_or_set: '{' [dict_maker | set_maker] '}'
 */
public final class DictOrSet extends NodeWrapper {

    public DictOrSet(ParseTreeNode node) {
        super(node);
    }

    public DictOrSet2 dictMakerOrSetMaker() {
        return get(1, DictOrSet2.class);
    }

    public boolean hasDictMakerOrSetMaker() {
        return has(1);
    }

    /**
     * dict_maker | set_maker
     */
    public static final class DictOrSet2 extends NodeWrapper {

        public DictOrSet2(ParseTreeNode node) {
            super(node);
        }

        public DictMaker dictMaker() {
            return get(0, DictMaker.class);
        }

        public boolean hasDictMaker() {
            return has(0);
        }

        public SetMaker setMaker() {
            return get(1, SetMaker.class);
        }

        public boolean hasSetMaker() {
            return has(1);
        }
    }
}
