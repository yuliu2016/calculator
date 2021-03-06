package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * builder_args:
 * *   | simple_args
 * *   | '(' [typed_arg_list] ')'
 */
public final class BuilderArgs extends NodeWrapper {

    public BuilderArgs(ParseTreeNode node) {
        super(node);
    }

    public SimpleArgs simpleArgs() {
        return new SimpleArgs(get(0));
    }

    public boolean hasSimpleArgs() {
        return has(0);
    }

    public BuilderArgs2 builderArgs2() {
        return new BuilderArgs2(get(1));
    }

    public boolean hasBuilderArgs2() {
        return has(1);
    }

    /**
     * '(' [typed_arg_list] ')'
     */
    public static final class BuilderArgs2 extends NodeWrapper {

        public BuilderArgs2(ParseTreeNode node) {
            super(node);
        }

        public TypedArgList typedArgList() {
            return new TypedArgList(get(1));
        }

        public boolean hasTypedArgList() {
            return has(1);
        }
    }
}
