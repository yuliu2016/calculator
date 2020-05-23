package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * arglist: argument (',' argument)* [',']
 */
public final class Arglist extends NodeWrapper {

    public Arglist(ParseTreeNode node) {
        super(node);
    }

    public Argument argument() {
        return get(0, Argument.class);
    }

    public List<Arglist2> arguments() {
        return getList(1, Arglist2.class);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' argument
     */
    public static final class Arglist2 extends NodeWrapper {

        public Arglist2(ParseTreeNode node) {
            super(node);
        }

        public Argument argument() {
            return get(1, Argument.class);
        }
    }
}
