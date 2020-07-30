package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * builder:
 * *   | NAME simple_args ':' expr
 * *   | NAME [builder_hint] [builder_args] block_suite
 */
public final class Builder extends NodeWrapper {

    public Builder(ParseTreeNode node) {
        super(node);
    }

    public Builder1 builder1() {
        return new Builder1(get(0));
    }

    public boolean hasBuilder1() {
        return has(0);
    }

    public Builder2 builder2() {
        return new Builder2(get(1));
    }

    public boolean hasBuilder2() {
        return has(1);
    }

    /**
     * NAME simple_args ':' expr
     */
    public static final class Builder1 extends NodeWrapper {

        public Builder1(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public SimpleArgs simpleArgs() {
            return new SimpleArgs(get(1));
        }

        public Expr expr() {
            return new Expr(get(3));
        }
    }

    /**
     * NAME [builder_hint] [builder_args] block_suite
     */
    public static final class Builder2 extends NodeWrapper {

        public Builder2(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public BuilderHint builderHint() {
            return new BuilderHint(get(1));
        }

        public boolean hasBuilderHint() {
            return has(1);
        }

        public BuilderArgs builderArgs() {
            return new BuilderArgs(get(2));
        }

        public boolean hasBuilderArgs() {
            return has(2);
        }

        public BlockSuite blockSuite() {
            return new BlockSuite(get(3));
        }
    }
}
