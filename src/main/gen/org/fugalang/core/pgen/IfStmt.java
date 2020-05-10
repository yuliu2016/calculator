package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * if_stmt: 'if' 'named_expr' 'suite' 'elif_stmt'* ['else_suite']
 */
public final class IfStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("if_stmt", RuleType.Conjunction, true);

    public static IfStmt of(ParseTreeNode node) {
        return new IfStmt(node);
    }

    private IfStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<ElifStmt> elifStmtList;

    @Override
    protected void buildRule() {
        addRequired(isTokenIf(), "if");
        addRequired(namedExpr());
        addRequired(suite());
        addRequired(elifStmtList());
        addOptional(elseSuiteOrNull());
    }

    public boolean isTokenIf() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public NamedExpr namedExpr() {
        var element = getItem(1);
        element.failIfAbsent(NamedExpr.RULE);
        return NamedExpr.of(element);
    }

    public Suite suite() {
        var element = getItem(2);
        element.failIfAbsent(Suite.RULE);
        return Suite.of(element);
    }

    public List<ElifStmt> elifStmtList() {
        if (elifStmtList != null) {
            return elifStmtList;
        }
        List<ElifStmt> result = null;
        var element = getItem(3);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(ElifStmt.of(node));
        }
        elifStmtList = result == null ? Collections.emptyList() : result;
        return elifStmtList;
    }

    public ElseSuite elseSuite() {
        var element = getItem(4);
        element.failIfAbsent(ElseSuite.RULE);
        return ElseSuite.of(element);
    }

    public ElseSuite elseSuiteOrNull() {
        var element = getItem(4);
        if (!element.isPresent(ElseSuite.RULE)) {
            return null;
        }
        return ElseSuite.of(element);
    }

    public boolean hasElseSuite() {
        var element = getItem(4);
        return element.isPresent(ElseSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("if");
        result = result && NamedExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        if (result) parseElifStmtList(parseTree, level + 1);
        if (result) ElseSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseElifStmtList(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!ElifStmt.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }
}
