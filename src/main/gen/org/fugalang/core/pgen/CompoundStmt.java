package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * compound_stmt: 'if_stmt' | 'while_stmt' | 'for_stmt' | 'try_stmt' | 'with_stmt'
 */
public final class CompoundStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("compound_stmt", RuleType.Disjunction, true);

    public static CompoundStmt of(ParseTreeNode node) {
        return new CompoundStmt(node);
    }

    private CompoundStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(ifStmt());
        addChoice(whileStmt());
        addChoice(forStmt());
        addChoice(tryStmt());
        addChoice(withStmt());
    }

    public IfStmt ifStmt() {
        var element = getItem(0);
        if (!element.isPresent(IfStmt.RULE)) {
            return null;
        }
        return IfStmt.of(element);
    }

    public boolean hasIfStmt() {
        var element = getItem(0);
        return element.isPresent(IfStmt.RULE);
    }

    public WhileStmt whileStmt() {
        var element = getItem(1);
        if (!element.isPresent(WhileStmt.RULE)) {
            return null;
        }
        return WhileStmt.of(element);
    }

    public boolean hasWhileStmt() {
        var element = getItem(1);
        return element.isPresent(WhileStmt.RULE);
    }

    public ForStmt forStmt() {
        var element = getItem(2);
        if (!element.isPresent(ForStmt.RULE)) {
            return null;
        }
        return ForStmt.of(element);
    }

    public boolean hasForStmt() {
        var element = getItem(2);
        return element.isPresent(ForStmt.RULE);
    }

    public TryStmt tryStmt() {
        var element = getItem(3);
        if (!element.isPresent(TryStmt.RULE)) {
            return null;
        }
        return TryStmt.of(element);
    }

    public boolean hasTryStmt() {
        var element = getItem(3);
        return element.isPresent(TryStmt.RULE);
    }

    public WithStmt withStmt() {
        var element = getItem(4);
        if (!element.isPresent(WithStmt.RULE)) {
            return null;
        }
        return WithStmt.of(element);
    }

    public boolean hasWithStmt() {
        var element = getItem(4);
        return element.isPresent(WithStmt.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
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
