package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * targetlist: target (',' target)* [',']
 */
public final class Targetlist extends NodeWrapper {

    public Targetlist(ParseTreeNode node) {
        super(node);
    }

    public Target target() {
        return get(0, Target.class);
    }

    public List<Targetlist2> targets() {
        return getList(1, Targetlist2.class);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' target
     */
    public static final class Targetlist2 extends NodeWrapper {

        public Targetlist2(ParseTreeNode node) {
            super(node);
        }

        public Target target() {
            return get(1, Target.class);
        }
    }
}
