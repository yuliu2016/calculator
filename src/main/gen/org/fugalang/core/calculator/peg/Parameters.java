package org.fugalang.core.calculator.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * parameters: sum (',' sum)* [',']
 */
public final class Parameters extends NodeWrapper {

    public Parameters(ParseTreeNode node) {
        super(node);
    }

    public Sum sum() {
        return get(0, Sum.class);
    }

    public List<Parameters2> commaSums() {
        return getList(1, Parameters2.class);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' sum
     */
    public static final class Parameters2 extends NodeWrapper {

        public Parameters2(ParseTreeNode node) {
            super(node);
        }

        public Sum sum() {
            return get(1, Sum.class);
        }
    }
}
