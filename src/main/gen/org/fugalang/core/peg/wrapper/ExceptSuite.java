package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * except_suite:
 * *   | except_clause+ [else_suite] [finally_suite]
 */
public final class ExceptSuite extends NodeWrapper {

    public ExceptSuite(ParseTreeNode node) {
        super(node);
    }

    public List<ExceptClause> exceptClauses() {
        return getList(0, ExceptClause::new);
    }

    public ElseSuite elseSuite() {
        return new ElseSuite(get(1));
    }

    public boolean hasElseSuite() {
        return has(1);
    }

    public FinallySuite finallySuite() {
        return new FinallySuite(get(2));
    }

    public boolean hasFinallySuite() {
        return has(2);
    }
}
