package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * args_kwargs: '*' ['typed_arg'] (',' 'default_arg')* [',' ['kwargs']]
 */
public final class ArgsKwargs extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("args_kwargs", RuleType.Conjunction);

    public static ArgsKwargs of(ParseTreeNode node) {
        return new ArgsKwargs(node);
    }

    private ArgsKwargs(ParseTreeNode node) {
        super(RULE, node);
    }

    public TypedArg typedArg() {
        return get(1, TypedArg::of);
    }

    public boolean hasTypedArg() {
        return has(1, TypedArg.RULE);
    }

    public List<ArgsKwargs3> defaultArgs() {
        return getList(2, ArgsKwargs3::of);
    }

    public ArgsKwargs4 argsKwargs4() {
        return get(3, ArgsKwargs4::of);
    }

    public boolean hasArgsKwargs4() {
        return has(3, ArgsKwargs4.RULE);
    }

    /**
     * ',' 'default_arg'
     */
    public static final class ArgsKwargs3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("args_kwargs:3", RuleType.Conjunction);

        public static ArgsKwargs3 of(ParseTreeNode node) {
            return new ArgsKwargs3(node);
        }

        private ArgsKwargs3(ParseTreeNode node) {
            super(RULE, node);
        }

        public DefaultArg defaultArg() {
            return get(1, DefaultArg::of);
        }
    }

    /**
     * ',' ['kwargs']
     */
    public static final class ArgsKwargs4 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("args_kwargs:4", RuleType.Conjunction);

        public static ArgsKwargs4 of(ParseTreeNode node) {
            return new ArgsKwargs4(node);
        }

        private ArgsKwargs4(ParseTreeNode node) {
            super(RULE, node);
        }

        public Kwargs kwargs() {
            return get(1, Kwargs::of);
        }

        public boolean hasKwargs() {
            return has(1, Kwargs.RULE);
        }
    }
}
