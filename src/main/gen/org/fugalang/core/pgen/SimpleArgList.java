package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * simple_arg_list: 'simple_arg' ('simple_arg')*
 */
public final class SimpleArgList extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("simple_arg_list", RuleType.Conjunction);

    public static SimpleArgList of(ParseTreeNode node) {
        return new SimpleArgList(node);
    }

    private SimpleArgList(ParseTreeNode node) {
        super(RULE, node);
    }

    public SimpleArg simpleArg() {
        return get(0, SimpleArg::of);
    }

    public List<SimpleArgList2> simpleArgs() {
        return getList(1, SimpleArgList2::of);
    }

    /**
     * 'simple_arg'
     */
    public static final class SimpleArgList2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("simple_arg_list:2", RuleType.Conjunction);

        public static SimpleArgList2 of(ParseTreeNode node) {
            return new SimpleArgList2(node);
        }

        private SimpleArgList2(ParseTreeNode node) {
            super(RULE, node);
        }

        public SimpleArg simpleArg() {
            return get(0, SimpleArg::of);
        }
    }
}
