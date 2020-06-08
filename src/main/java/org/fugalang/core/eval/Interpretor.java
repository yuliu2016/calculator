package org.fugalang.core.eval;

import org.fugalang.core.peg.visitor.FugaVisitor;
import org.fugalang.core.peg.wrapper.*;

public class Interpretor implements FugaVisitor<Object> {
    @Override
    public Object visitSingleInput(SingleInput singleInput) {
        return null;
    }

    @Override
    public Object visitFileInput(FileInput fileInput) {
        return null;
    }

    @Override
    public Object visitEvalInput(EvalInput evalInput) {
        return null;
    }

    @Override
    public Object visitStmt(Stmt stmt) {
        return null;
    }

    @Override
    public Object visitSimpleStmt(SimpleStmt simpleStmt) {
        return null;
    }

    @Override
    public Object visitSmallStmt(SmallStmt smallStmt) {
        return null;
    }

    @Override
    public Object visitDelStmt(DelStmt delStmt) {
        return null;
    }

    @Override
    public Object visitReturnStmt(ReturnStmt returnStmt) {
        return null;
    }

    @Override
    public Object visitRaiseStmt(RaiseStmt raiseStmt) {
        return null;
    }

    @Override
    public Object visitNonlocalStmt(NonlocalStmt nonlocalStmt) {
        return null;
    }

    @Override
    public Object visitAssertStmt(AssertStmt assertStmt) {
        return null;
    }

    @Override
    public Object visitStarExpr(StarExpr starExpr) {
        return null;
    }

    @Override
    public Object visitExprlist(Exprlist exprlist) {
        return null;
    }

    @Override
    public Object visitTarget(Target target) {
        return null;
    }

    @Override
    public Object visitTargetlist(Targetlist targetlist) {
        return null;
    }

    @Override
    public Object visitExprOrStar(ExprOrStar exprOrStar) {
        return null;
    }

    @Override
    public Object visitExprlistStar(ExprlistStar exprlistStar) {
        return null;
    }

    @Override
    public Object visitNamedExprStar(NamedExprStar namedExprStar) {
        return null;
    }

    @Override
    public Object visitNamedExprList(NamedExprList namedExprList) {
        return null;
    }

    @Override
    public Object visitSubscript(Subscript subscript) {
        return null;
    }

    @Override
    public Object visitSlicelist(Slicelist slicelist) {
        return null;
    }

    @Override
    public Object visitSlice(Slice slice) {
        return null;
    }

    @Override
    public Object visitSliceExpr(SliceExpr sliceExpr) {
        return null;
    }

    @Override
    public Object visitDictItem(DictItem dictItem) {
        return null;
    }

    @Override
    public Object visitDictItems(DictItems dictItems) {
        return null;
    }

    @Override
    public Object visitAsName(AsName asName) {
        return null;
    }

    @Override
    public Object visitIterFor(IterFor iterFor) {
        return null;
    }

    @Override
    public Object visitIterIf(IterIf iterIf) {
        return null;
    }

    @Override
    public Object visitIterator(Iterator iterator) {
        return null;
    }

    @Override
    public Object visitAssignment(Assignment assignment) {
        return null;
    }

    @Override
    public Object visitExpassign(Expassign expassign) {
        return null;
    }

    @Override
    public Object visitAnnassign(Annassign annassign) {
        return null;
    }

    @Override
    public Object visitAugassign(Augassign augassign) {
        return null;
    }

    @Override
    public Object visitAugassignOp(AugassignOp augassignOp) {
        return null;
    }

    @Override
    public Object visitImportName(ImportName importName) {
        return null;
    }

    @Override
    public Object visitImportFrom(ImportFrom importFrom) {
        return null;
    }

    @Override
    public Object visitImportFromNames(ImportFromNames importFromNames) {
        return null;
    }

    @Override
    public Object visitImportFromItems(ImportFromItems importFromItems) {
        return null;
    }

    @Override
    public Object visitImportAsName(ImportAsName importAsName) {
        return null;
    }

    @Override
    public Object visitDottedAsName(DottedAsName dottedAsName) {
        return null;
    }

    @Override
    public Object visitImportAsNames(ImportAsNames importAsNames) {
        return null;
    }

    @Override
    public Object visitDottedAsNames(DottedAsNames dottedAsNames) {
        return null;
    }

    @Override
    public Object visitDottedName(DottedName dottedName) {
        return null;
    }

    @Override
    public Object visitCompoundStmt(CompoundStmt compoundStmt) {
        return null;
    }

    @Override
    public Object visitIfStmt(IfStmt ifStmt) {
        return null;
    }

    @Override
    public Object visitElifStmt(ElifStmt elifStmt) {
        return null;
    }

    @Override
    public Object visitWhileStmt(WhileStmt whileStmt) {
        return null;
    }

    @Override
    public Object visitForStmt(ForStmt forStmt) {
        return null;
    }

    @Override
    public Object visitTryStmt(TryStmt tryStmt) {
        return null;
    }

    @Override
    public Object visitWithStmt(WithStmt withStmt) {
        return null;
    }

    @Override
    public Object visitExprAsName(ExprAsName exprAsName) {
        return null;
    }

    @Override
    public Object visitBlockSuite(BlockSuite blockSuite) {
        return null;
    }

    @Override
    public Object visitSuite(Suite suite) {
        return null;
    }

    @Override
    public Object visitElseSuite(ElseSuite elseSuite) {
        return null;
    }

    @Override
    public Object visitFinallySuite(FinallySuite finallySuite) {
        return null;
    }

    @Override
    public Object visitFuncSuite(FuncSuite funcSuite) {
        return null;
    }

    @Override
    public Object visitExceptClause(ExceptClause exceptClause) {
        return null;
    }

    @Override
    public Object visitExceptSuite(ExceptSuite exceptSuite) {
        return null;
    }

    @Override
    public Object visitParameters(Parameters parameters) {
        return null;
    }

    @Override
    public Object visitArglist(Arglist arglist) {
        return null;
    }

    @Override
    public Object visitArgument(Argument argument) {
        return null;
    }

    @Override
    public Object visitTypedArgList(TypedArgList typedArgList) {
        return null;
    }

    @Override
    public Object visitFullArgList(FullArgList fullArgList) {
        return null;
    }

    @Override
    public Object visitArgsKwargs(ArgsKwargs argsKwargs) {
        return null;
    }

    @Override
    public Object visitKwargs(Kwargs kwargs) {
        return null;
    }

    @Override
    public Object visitDefaultArg(DefaultArg defaultArg) {
        return null;
    }

    @Override
    public Object visitTypedArg(TypedArg typedArg) {
        return null;
    }

    @Override
    public Object visitSimpleArg(SimpleArg simpleArg) {
        return null;
    }

    @Override
    public Object visitFuncTypeHint(FuncTypeHint funcTypeHint) {
        return null;
    }

    @Override
    public Object visitFuncArgs(FuncArgs funcArgs) {
        return null;
    }

    @Override
    public Object visitFuncdef(Funcdef funcdef) {
        return null;
    }

    @Override
    public Object visitNamedExpr(NamedExpr namedExpr) {
        return null;
    }

    @Override
    public Object visitConditional(Conditional conditional) {
        return null;
    }

    @Override
    public Object visitExpr(Expr expr) {
        return null;
    }

    @Override
    public Object visitDisjunction(Disjunction disjunction) {
        return null;
    }

    @Override
    public Object visitConjunction(Conjunction conjunction) {
        return null;
    }

    @Override
    public Object visitInversion(Inversion inversion) {
        return null;
    }

    @Override
    public Object visitComparison(Comparison comparison) {
        return null;
    }

    @Override
    public Object visitCompOp(CompOp compOp) {
        return null;
    }

    @Override
    public Object visitBitwiseOr(BitwiseOr bitwiseOr) {
        return null;
    }

    @Override
    public Object visitBitwiseXor(BitwiseXor bitwiseXor) {
        return null;
    }

    @Override
    public Object visitBitwiseAnd(BitwiseAnd bitwiseAnd) {
        return null;
    }

    @Override
    public Object visitShiftExpr(ShiftExpr shiftExpr) {
        return null;
    }

    @Override
    public Object visitSum(Sum sum) {
        return null;
    }

    @Override
    public Object visitTerm(Term term) {
        return null;
    }

    @Override
    public Object visitPipeExpr(PipeExpr pipeExpr) {
        return null;
    }

    @Override
    public Object visitFactor(Factor factor) {
        return null;
    }

    @Override
    public Object visitPower(Power power) {
        return null;
    }

    @Override
    public Object visitPrimary(Primary primary) {
        return null;
    }

    @Override
    public Object visitTupleAtom(TupleAtom tupleAtom) {
        return null;
    }

    @Override
    public Object visitListIter(ListIter listIter) {
        return null;
    }

    @Override
    public Object visitListAtom(ListAtom listAtom) {
        return null;
    }

    @Override
    public Object visitSetAtom(SetAtom setAtom) {
        return null;
    }

    @Override
    public Object visitDictIter(DictIter dictIter) {
        return null;
    }

    @Override
    public Object visitDictAtom(DictAtom dictAtom) {
        return null;
    }

    @Override
    public Object visitAtom(Atom atom) {
        return null;
    }
}
