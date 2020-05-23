package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * star_expr: '*' bitwise_or
 */
public final class StarExpr extends NodeWrapper {

    public StarExpr(ParseTreeNode node) {
        super(node);
    }

    public BitwiseOr bitwiseOr() {
        return get(1, BitwiseOr.class);
    }
}
