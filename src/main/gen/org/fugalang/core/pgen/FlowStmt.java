package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.DisjunctionRule;

// flow_stmt: 'break_stmt' | 'continue_stmt' | 'return_stmt' | 'raise_stmt'
public final class FlowStmt extends DisjunctionRule {
    public static final String RULE_NAME = "flow_stmt";

    private final BreakStmt breakStmt;
    private final ContinueStmt continueStmt;
    private final ReturnStmt returnStmt;
    private final RaiseStmt raiseStmt;

    public FlowStmt(
            BreakStmt breakStmt,
            ContinueStmt continueStmt,
            ReturnStmt returnStmt,
            RaiseStmt raiseStmt
    ) {
        this.breakStmt = breakStmt;
        this.continueStmt = continueStmt;
        this.returnStmt = returnStmt;
        this.raiseStmt = raiseStmt;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("breakStmt", breakStmt);
        addChoice("continueStmt", continueStmt);
        addChoice("returnStmt", returnStmt);
        addChoice("raiseStmt", raiseStmt);
    }

    public BreakStmt breakStmt() {
        return breakStmt;
    }

    public ContinueStmt continueStmt() {
        return continueStmt;
    }

    public ReturnStmt returnStmt() {
        return returnStmt;
    }

    public RaiseStmt raiseStmt() {
        return raiseStmt;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = BreakStmt.parse(parseTree, level + 1);
        result = result || ContinueStmt.parse(parseTree, level + 1);
        result = result || ReturnStmt.parse(parseTree, level + 1);
        result = result || RaiseStmt.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
