package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * small_stmt: 'pass' | 'break' | 'continue' | 'del_stmt' | 'return_stmt' | 'raise_stmt' | 'nonlocal_stmt' | 'assert_stmt' | 'import_name' | 'import_from' | 'assignment'
 */
public final class SmallStmt extends NodeWrapper {

    public SmallStmt(ParseTreeNode node) {
        super(ParserRules.SMALL_STMT, node);
    }

    public boolean isPass() {
        return is(0);
    }

    public boolean isBreak() {
        return is(1);
    }

    public boolean isContinue() {
        return is(2);
    }

    public DelStmt delStmt() {
        return get(3, DelStmt::new);
    }

    public boolean hasDelStmt() {
        return has(3, ParserRules.DEL_STMT);
    }

    public ReturnStmt returnStmt() {
        return get(4, ReturnStmt::new);
    }

    public boolean hasReturnStmt() {
        return has(4, ParserRules.RETURN_STMT);
    }

    public RaiseStmt raiseStmt() {
        return get(5, RaiseStmt::new);
    }

    public boolean hasRaiseStmt() {
        return has(5, ParserRules.RAISE_STMT);
    }

    public NonlocalStmt nonlocalStmt() {
        return get(6, NonlocalStmt::new);
    }

    public boolean hasNonlocalStmt() {
        return has(6, ParserRules.NONLOCAL_STMT);
    }

    public AssertStmt assertStmt() {
        return get(7, AssertStmt::new);
    }

    public boolean hasAssertStmt() {
        return has(7, ParserRules.ASSERT_STMT);
    }

    public ImportName importName() {
        return get(8, ImportName::new);
    }

    public boolean hasImportName() {
        return has(8, ParserRules.IMPORT_NAME);
    }

    public ImportFrom importFrom() {
        return get(9, ImportFrom::new);
    }

    public boolean hasImportFrom() {
        return has(9, ParserRules.IMPORT_FROM);
    }

    public Assignment assignment() {
        return get(10, Assignment::new);
    }

    public boolean hasAssignment() {
        return has(10, ParserRules.ASSIGNMENT);
    }
}
