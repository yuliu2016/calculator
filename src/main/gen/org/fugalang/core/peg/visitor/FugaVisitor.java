package org.fugalang.core.peg.visitor;

import org.fugalang.core.peg.*;

@SuppressWarnings("unused")
public interface FugaVisitor<T> {
    T visitSingleInput(SingleInput singleInput);

    T visitFileInput(FileInput fileInput);

    T visitEvalInput(EvalInput evalInput);

    T visitStmt(Stmt stmt);

    T visitSimpleStmt(SimpleStmt simpleStmt);

    T visitSmallStmt(SmallStmt smallStmt);

    T visitFlowStmt(FlowStmt flowStmt);

    T visitDelStmt(DelStmt delStmt);

    T visitReturnStmt(ReturnStmt returnStmt);

    T visitRaiseStmt(RaiseStmt raiseStmt);

    T visitNonlocalStmt(NonlocalStmt nonlocalStmt);

    T visitAssertStmt(AssertStmt assertStmt);

    T visitStarExpr(StarExpr starExpr);

    T visitExprlist(Exprlist exprlist);

    T visitTarget(Target target);

    T visitTargetlist(Targetlist targetlist);

    T visitExprOrStar(ExprOrStar exprOrStar);

    T visitExprlistStar(ExprlistStar exprlistStar);

    T visitNamedExprStar(NamedExprStar namedExprStar);

    T visitNamedExprList(NamedExprList namedExprList);

    T visitSubscript(Subscript subscript);

    T visitSlicelist(Slicelist slicelist);

    T visitSlice(Slice slice);

    T visitSliceExpr(SliceExpr sliceExpr);

    T visitDictItem(DictItem dictItem);

    T visitDictMaker(DictMaker dictMaker);

    T visitAsName(AsName asName);

    T visitAssignment(Assignment assignment);

    T visitAugassign(Augassign augassign);

    T visitAnnassign(Annassign annassign);

    T visitImportName(ImportName importName);

    T visitImportFrom(ImportFrom importFrom);

    T visitImportFromNames(ImportFromNames importFromNames);

    T visitImportFromItems(ImportFromItems importFromItems);

    T visitImportAsName(ImportAsName importAsName);

    T visitDottedAsName(DottedAsName dottedAsName);

    T visitImportAsNames(ImportAsNames importAsNames);

    T visitDottedAsNames(DottedAsNames dottedAsNames);

    T visitDottedName(DottedName dottedName);

    T visitCompoundStmt(CompoundStmt compoundStmt);

    T visitIfStmt(IfStmt ifStmt);

    T visitElifStmt(ElifStmt elifStmt);

    T visitWhileStmt(WhileStmt whileStmt);

    T visitForStmt(ForStmt forStmt);

    T visitTryStmt(TryStmt tryStmt);

    T visitWithStmt(WithStmt withStmt);

    T visitExprAsName(ExprAsName exprAsName);

    T visitBlockSuite(BlockSuite blockSuite);

    T visitSuite(Suite suite);

    T visitElseSuite(ElseSuite elseSuite);

    T visitFinallySuite(FinallySuite finallySuite);

    T visitFuncSuite(FuncSuite funcSuite);

    T visitExceptClause(ExceptClause exceptClause);

    T visitExceptSuite(ExceptSuite exceptSuite);

    T visitParameters(Parameters parameters);

    T visitArglist(Arglist arglist);

    T visitArgument(Argument argument);

    T visitTypedArgList(TypedArgList typedArgList);

    T visitFullArgList(FullArgList fullArgList);

    T visitArgsKwargs(ArgsKwargs argsKwargs);

    T visitKwargs(Kwargs kwargs);

    T visitDefaultArg(DefaultArg defaultArg);

    T visitTypedArg(TypedArg typedArg);

    T visitSimpleArg(SimpleArg simpleArg);

    T visitFuncTypeHint(FuncTypeHint funcTypeHint);

    T visitFuncArgs(FuncArgs funcArgs);

    T visitFuncdef(Funcdef funcdef);

    T visitNamedExpr(NamedExpr namedExpr);

    T visitConditional(Conditional conditional);

    T visitExpr(Expr expr);

    T visitDisjunction(Disjunction disjunction);

    T visitConjunction(Conjunction conjunction);

    T visitInversion(Inversion inversion);

    T visitComparison(Comparison comparison);

    T visitCompOp(CompOp compOp);

    T visitBitwiseOr(BitwiseOr bitwiseOr);

    T visitBitwiseXor(BitwiseXor bitwiseXor);

    T visitBitwiseAnd(BitwiseAnd bitwiseAnd);

    T visitShiftExpr(ShiftExpr shiftExpr);

    T visitSum(Sum sum);

    T visitTerm(Term term);

    T visitPipe(Pipe pipe);

    T visitPipeExpr(PipeExpr pipeExpr);

    T visitPipeFor(PipeFor pipeFor);

    T visitCompFor(CompFor compFor);

    T visitCompIf(CompIf compIf);

    T visitCompIter(CompIter compIter);

    T visitFactor(Factor factor);

    T visitPower(Power power);

    T visitPrimary(Primary primary);

    T visitTupleAtom(TupleAtom tupleAtom);

    T visitListAtom(ListAtom listAtom);

    T visitDictOrSet(DictOrSet dictOrSet);

    T visitAtom(Atom atom);
}
