package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * simple_arg_list: 'simple_arg' ('simple_arg')*
 */
public final class SimpleArgList extends NodeWrapper {

    public SimpleArgList(ParseTreeNode node) {
        super(ParserRules.SIMPLE_ARG_LIST, node);
    }

    public SimpleArg simpleArg() {
        return get(0, SimpleArg::new);
    }

    public List<SimpleArgList2> simpleArgs() {
        return getList(1, SimpleArgList2::new);
    }

    /**
     * 'simple_arg'
     */
    public static final class SimpleArgList2 extends NodeWrapper {

        public SimpleArgList2(ParseTreeNode node) {
            super(ParserRules.SIMPLE_ARG_LIST_2, node);
        }

        public SimpleArg simpleArg() {
            return get(0, SimpleArg::new);
        }
    }
}
