package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;
import org.fugalang.core.parser.ParseTree;

/**
 * compound_stmt: 'if_stmt' | 'while_stmt' | 'for_stmt' | 'try_stmt' | 'with_stmt'
 */
public final class CompoundStmt extends DisjunctionRule {
    public static final String RULE_NAME = "compound_stmt";

    private final IfStmt ifStmt;
    private final WhileStmt whileStmt;
    private final ForStmt forStmt;
    private final TryStmt tryStmt;
    private final WithStmt withStmt;

    public CompoundStmt(
            IfStmt ifStmt,
            WhileStmt whileStmt,
            ForStmt forStmt,
            TryStmt tryStmt,
            WithStmt withStmt
    ) {
        this.ifStmt = ifStmt;
        this.whileStmt = whileStmt;
        this.forStmt = forStmt;
        this.tryStmt = tryStmt;
        this.withStmt = withStmt;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("ifStmt", ifStmt);
        addChoice("whileStmt", whileStmt);
        addChoice("forStmt", forStmt);
        addChoice("tryStmt", tryStmt);
        addChoice("withStmt", withStmt);
    }

    public IfStmt ifStmt() {
        return ifStmt;
    }

    public boolean hasIfStmt() {
        return ifStmt() != null;
    }

    public WhileStmt whileStmt() {
        return whileStmt;
    }

    public boolean hasWhileStmt() {
        return whileStmt() != null;
    }

    public ForStmt forStmt() {
        return forStmt;
    }

    public boolean hasForStmt() {
        return forStmt() != null;
    }

    public TryStmt tryStmt() {
        return tryStmt;
    }

    public boolean hasTryStmt() {
        return tryStmt() != null;
    }

    public WithStmt withStmt() {
        return withStmt;
    }

    public boolean hasWithStmt() {
        return withStmt() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = IfStmt.parse(parseTree, level + 1);
        result = result || WhileStmt.parse(parseTree, level + 1);
        result = result || ForStmt.parse(parseTree, level + 1);
        result = result || TryStmt.parse(parseTree, level + 1);
        result = result || WithStmt.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
