package org.fugalang.core.eval;

import org.fugalang.core.peg.visitor.FugaVisitor;
import org.fugalang.core.peg.wrapper.*;

@SuppressWarnings("unused")
public class Interpretor implements FugaVisitor<Object> {

    /**
     * single_input:
     * *   | NEWLINE
     * *   | simple_stmt
     * *   | compound_stmt NEWLINE
     */
    @Override
    public Object visitSingleInput(SingleInput singleInput) {
        if (singleInput.hasSimpleStmt()) {
            return visitSimpleStmt(singleInput.simpleStmt());
        }
        if (singleInput.hasCompoundStmtNewline()) {
            return visitCompoundStmt(singleInput.compoundStmtNewline().compoundStmt());
        }
        return null;
    }

    /**
     * file_input:
     * *   | (NEWLINE | stmt)* ENDMARKER
     */
    @Override
    public Object visitFileInput(FileInput fileInput) {
        return null;
    }

    /**
     * eval_input:
     * *   | exprlist NEWLINE* ENDMARKER
     */
    @Override
    public Object visitEvalInput(EvalInput evalInput) {
        return null;
    }

    /**
     * stmt:
     * *   | (simple_stmt | compound_stmt) NEWLINE
     */
    @Override
    public Object visitStmt(Stmt stmt) {
        var s1 = stmt.simpleStmtOrCompoundStmt();
        if (s1.hasSimpleStmt()) {
            return visitSimpleStmt(s1.simpleStmt());
        }
        if (s1.hasCompoundStmt()) {
            return visitCompoundStmt(s1.compoundStmt());
        }
        return null;
    }

    /**
     * simple_stmt:
     * *   | ';'.small_stmt+ [';']
     */
    @Override
    public Object visitSimpleStmt(SimpleStmt simpleStmt) {
        return null;
    }

    /**
     * small_stmt:
     * *   | 'pass'
     * *   | 'break'
     * *   | 'continue'
     * *   | return_stmt
     * *   | raise_stmt
     * *   | del_stmt
     * *   | nonlocal_stmt
     * *   | assert_stmt
     * *   | import_name
     * *   | import_from
     * *   | assignment
     */
    @Override
    public Object visitSmallStmt(SmallStmt smallStmt) {
        if (smallStmt.isPass()) {
            return null;
        }
        if (smallStmt.isBreak()) {
            return null;
        }
        if (smallStmt.isContinue()) {
            return null;
        }
        if (smallStmt.hasReturnStmt()) {
            return visitReturnStmt(smallStmt.returnStmt());
        }
        if (smallStmt.hasRaiseStmt()) {
            return visitRaiseStmt(smallStmt.raiseStmt());
        }
        if (smallStmt.hasDelStmt()) {
            return visitDelStmt(smallStmt.delStmt());
        }
        if (smallStmt.hasNonlocalStmt()) {
            return visitNonlocalStmt(smallStmt.nonlocalStmt());
        }
        if (smallStmt.hasAssertStmt()) {
            return visitAssertStmt(smallStmt.assertStmt());
        }
        if (smallStmt.hasImportName()) {
            return visitImportName(smallStmt.importName());
        }
        if (smallStmt.hasImportFrom()) {
            return visitImportFrom(smallStmt.importFrom());
        }
        if (smallStmt.hasAssignment()) {
            return visitAssignment(smallStmt.assignment());
        }
        return null;
    }

    /**
     * del_stmt:
     * *   | 'del' targetlist
     */
    @Override
    public Object visitDelStmt(DelStmt delStmt) {
        return null;
    }

    /**
     * return_stmt:
     * *   | 'return' [exprlist_star]
     */
    @Override
    public Object visitReturnStmt(ReturnStmt returnStmt) {
        return null;
    }

    /**
     * raise_stmt:
     * *   | 'raise' expr ['from' expr]
     */
    @Override
    public Object visitRaiseStmt(RaiseStmt raiseStmt) {
        return null;
    }

    /**
     * nonlocal_stmt:
     * *   | 'nonlocal' ','.NAME+
     */
    @Override
    public Object visitNonlocalStmt(NonlocalStmt nonlocalStmt) {
        return null;
    }

    /**
     * assert_stmt:
     * *   | 'assert' expr [',' expr]
     */
    @Override
    public Object visitAssertStmt(AssertStmt assertStmt) {
        return null;
    }

    /**
     * star_expr:
     * *   | '*' bitwise_or
     */
    @Override
    public Object visitStarExpr(StarExpr starExpr) {
        return null;
    }

    /**
     * exprlist:
     * *   | ','.expr+ [',']
     */
    @Override
    public Object visitExprlist(Exprlist exprlist) {
        return null;
    }

    /**
     * target:
     * *   | t_primary '.' NAME !t_lookahead
     * *   | t_primary subscript !t_lookahead
     * *   | NAME
     * *   | '(' targetlist ')'
     */
    @Override
    public Object visitTarget(Target target) {
        return null;
    }

    /**
     * t_primary:
     * *   | t_primary '.' NAME &t_lookahead
     * *   | t_primary parameters &t_lookahead
     * *   | t_primary subscript &t_lookahead
     * *   | atom &t_lookahead
     */
    @Override
    public Object visitTPrimary(TPrimary tPrimary) {
        return null;
    }

    /**
     * t_lookahead:
     * *   | '.'
     * *   | '('
     * *   | '['
     */
    @Override
    public Object visitTLookahead(TLookahead tLookahead) {
        return null;
    }

    /**
     * targetlist:
     * *   | ','.target+ [',']
     */
    @Override
    public Object visitTargetlist(Targetlist targetlist) {
        return null;
    }

    /**
     * expr_or_star:
     * *   | star_expr
     * *   | expr
     */
    @Override
    public Object visitExprOrStar(ExprOrStar exprOrStar) {
        return null;
    }

    /**
     * exprlist_star:
     * *   | ','.expr_or_star+ [',']
     */
    @Override
    public Object visitExprlistStar(ExprlistStar exprlistStar) {
        return null;
    }


    /**
     * subscript:
     * *   | '[' slicelist ']'
     */
    @Override
    public Object visitSubscript(Subscript subscript) {
        return null;
    }

    /**
     * slicelist:
     * *   | ','.slice+ [',']
     */
    @Override
    public Object visitSlicelist(Slicelist slicelist) {
        return null;
    }

    /**
     * slice:
     * *   | [expr] slice_expr [slice_expr]
     * *   | expr
     */
    @Override
    public Object visitSlice(Slice slice) {
        return null;
    }

    /**
     * slice_expr:
     * *   | ':' [expr]
     */
    @Override
    public Object visitSliceExpr(SliceExpr sliceExpr) {
        return null;
    }

    /**
     * dict_item:
     * *   | expr ':' expr
     * *   | '**' bitwise_or
     */
    @Override
    public Object visitDictItem(DictItem dictItem) {
        return null;
    }

    /**
     * dict_items:
     * *   | ','.dict_item+ [',']
     */
    @Override
    public Object visitDictItems(DictItems dictItems) {
        return null;
    }

    /**
     * as_name:
     * *   | 'as' NAME
     */
    @Override
    public Object visitAsName(AsName asName) {
        return null;
    }

    /**
     * iter_for:
     * *   | 'for' targetlist 'in' disjunction [iter_if]
     */
    @Override
    public Object visitIterFor(IterFor iterFor) {
        return null;
    }

    /**
     * iter_if:
     * *   | 'if' named_expr
     */
    @Override
    public Object visitIterIf(IterIf iterIf) {
        return null;
    }

    /**
     * iterator:
     * *   | iter_for* 'for' targetlist [iter_if]
     */
    @Override
    public Object visitIterator(Iterator iterator) {
        return null;
    }

    /**
     * assignment:
     * *   | pubassign
     * *   | annassign
     * *   | augassign
     * *   | simple_assign
     */
    @Override
    public Object visitAssignment(Assignment assignment) {
        if (assignment.hasPubassign()) {
            return visitPubassign(assignment.pubassign());
        }
        if (assignment.hasAnnassign()) {
            return visitAnnassign(assignment.annassign());
        }
        if (assignment.hasAugassign()) {
            return visitAugassign(assignment.augassign());
        }
        if (assignment.hasSimpleAssign()) {
            return visitSimpleAssign(assignment.simpleAssign());
        }
        return null;
    }

    /**
     * pubassign:
     * *   | '/' NAME '=' exprlist
     */
    @Override
    public Object visitPubassign(Pubassign expassign) {
        return null;
    }

    /**
     * annassign:
     * *   | target ':' expr ['=' exprlist]
     */
    @Override
    public Object visitAnnassign(Annassign annassign) {
        return null;
    }

    /**
     * augassign:
     * *   | target augassign_op exprlist
     */
    @Override
    public Object visitAugassign(Augassign augassign) {
        return null;
    }

    /**
     * simple_assign:
     * *   | (targetlist '=')* exprlist_star
     */
    @Override
    public Object visitSimpleAssign(SimpleAssign simpleAssign) {
        return null;
    }

    /**
     * augassign_op:
     * *   | '+='
     * *   | '-='
     * *   | '*='
     * *   | '@='
     * *   | '/='
     * *   | '%='
     * *   | '&='
     * *   | '|='
     * *   | '^='
     * *   | '<<='
     * *   | '>>='
     * *   | '**='
     * *   | '//='
     */
    @Override
    public Object visitAugassignOp(AugassignOp augassignOp) {
        return null;
    }

    /**
     * import_name:
     * *   | 'import' dotted_as_names
     */
    @Override
    public Object visitImportName(ImportName importName) {
        return null;
    }

    /**
     * import_from:
     * *   | 'from' import_from_names 'import' import_from_items
     */
    @Override
    public Object visitImportFrom(ImportFrom importFrom) {
        return null;
    }

    /**
     * import_from_names:
     * *   | dotted_name
     * *   | '.'+ [dotted_name]
     */
    @Override
    public Object visitImportFromNames(ImportFromNames importFromNames) {
        return null;
    }

    /**
     * import_from_items:
     * *   | '*'
     * *   | '(' import_as_names [','] ')'
     * *   | import_as_names
     */
    @Override
    public Object visitImportFromItems(ImportFromItems importFromItems) {
        return null;
    }

    /**
     * import_as_name:
     * *   | NAME [as_name]
     */
    @Override
    public Object visitImportAsName(ImportAsName importAsName) {
        return null;
    }

    /**
     * dotted_as_name:
     * *   | dotted_name [as_name]
     */
    @Override
    public Object visitDottedAsName(DottedAsName dottedAsName) {
        return null;
    }

    /**
     * import_as_names:
     * *   | ','.import_as_name+
     */
    @Override
    public Object visitImportAsNames(ImportAsNames importAsNames) {
        return null;
    }

    /**
     * dotted_as_names:
     * *   | ','.dotted_as_name+
     */
    @Override
    public Object visitDottedAsNames(DottedAsNames dottedAsNames) {
        return null;
    }

    /**
     * dotted_name:
     * *   | '.'.NAME+
     */
    @Override
    public Object visitDottedName(DottedName dottedName) {
        return null;
    }

    /**
     * compound_stmt:
     * *   | if_stmt
     * *   | while_stmt
     * *   | for_stmt
     * *   | try_stmt
     * *   | with_stmt
     */
    @Override
    public Object visitCompoundStmt(CompoundStmt compoundStmt) {
        return null;
    }

    /**
     * if_stmt:
     * *   | 'if' named_expr suite elif_stmt* [else_suite]
     */
    @Override
    public Object visitIfStmt(IfStmt ifStmt) {
        return null;
    }

    /**
     * elif_stmt:
     * *   | 'elif' named_expr suite
     */
    @Override
    public Object visitElifStmt(ElifStmt elifStmt) {
        return null;
    }

    /**
     * while_stmt:
     * *   | 'while' named_expr suite [else_suite]
     */
    @Override
    public Object visitWhileStmt(WhileStmt whileStmt) {
        return null;
    }

    /**
     * for_stmt:
     * *   | 'for' targetlist 'in' exprlist suite [else_suite]
     */
    @Override
    public Object visitForStmt(ForStmt forStmt) {
        return null;
    }

    /**
     * try_stmt:
     * *   | 'try' suite (except_suite | finally_suite)
     */
    @Override
    public Object visitTryStmt(TryStmt tryStmt) {
        return null;
    }

    /**
     * with_stmt:
     * *   | 'with' ','.expr_as_name+ suite
     */
    @Override
    public Object visitWithStmt(WithStmt withStmt) {
        return null;
    }

    /**
     * expr_as_name:
     * *   | expr [as_name]
     */
    @Override
    public Object visitExprAsName(ExprAsName exprAsName) {
        return null;
    }

    /**
     * block_suite:
     * *   | '{' NEWLINE stmt+ '}'
     */
    @Override
    public Object visitBlockSuite(BlockSuite blockSuite) {
        return null;
    }

    /**
     * suite:
     * *   | ':' simple_stmt
     * *   | block_suite
     */
    @Override
    public Object visitSuite(Suite suite) {
        return null;
    }

    /**
     * else_suite:
     * *   | 'else' suite
     */
    @Override
    public Object visitElseSuite(ElseSuite elseSuite) {
        return null;
    }

    /**
     * finally_suite:
     * *   | 'finally' suite
     */
    @Override
    public Object visitFinallySuite(FinallySuite finallySuite) {
        return null;
    }

    /**
     * except_clause:
     * *   | 'except' [expr_as_name] suite
     */
    @Override
    public Object visitExceptClause(ExceptClause exceptClause) {
        return null;
    }

    /**
     * except_suite:
     * *   | except_clause+ [else_suite] [finally_suite]
     */
    @Override
    public Object visitExceptSuite(ExceptSuite exceptSuite) {
        return null;
    }

    /**
     * typed_arg_list:
     * *   | kwargs
     * *   | args_kwargs
     * *   | full_arg_list
     */
    @Override
    public Object visitTypedArgList(TypedArgList typedArgList) {
        return null;
    }

    /**
     * full_arg_list:
     * *   | ','.default_arg+ [',' [kwargs | args_kwargs]]
     */
    @Override
    public Object visitFullArgList(FullArgList fullArgList) {
        return null;
    }

    /**
     * args_kwargs:
     * *   | '*' [typed_arg] (',' default_arg)* [',' [kwargs]]
     */
    @Override
    public Object visitArgsKwargs(ArgsKwargs argsKwargs) {
        return null;
    }

    /**
     * kwargs:
     * *   | '**' typed_arg [',']
     */
    @Override
    public Object visitKwargs(Kwargs kwargs) {
        return null;
    }

    /**
     * default_arg:
     * *   | typed_arg ['=' expr]
     */
    @Override
    public Object visitDefaultArg(DefaultArg defaultArg) {
        return null;
    }

    /**
     * typed_arg:
     * *   | NAME [':' expr]
     */
    @Override
    public Object visitTypedArg(TypedArg typedArg) {
        return null;
    }

    /**
     * simple_arg:
     * *   | NAME ['=' expr]
     */
    @Override
    public Object visitSimpleArg(SimpleArg simpleArg) {
        return null;
    }

    /**
     * named_expr:
     * *   | NAME ':=' expr
     * *   | expr
     */
    @Override
    public Object visitNamedExpr(NamedExpr namedExpr) {
        return null;
    }

    /**
     * conditional:
     * *   | 'if' disjunction '?' disjunction ':' expr
     */
    @Override
    public Object visitConditional(Conditional conditional) {
        return null;
    }

    /**
     * expr:
     * *   | conditional
     * *   | funcdef
     * *   | disjunction
     */
    @Override
    public Object visitExpr(Expr expr) {
        return null;
    }

    /**
     * disjunction:
     * *   | disjunction 'or' conjunction
     * *   | conjunction
     */
    @Override
    public Object visitDisjunction(Disjunction disjunction) {
        return null;
    }

    /**
     * conjunction:
     * *   | conjunction 'and' inversion
     * *   | inversion
     */
    @Override
    public Object visitConjunction(Conjunction conjunction) {
        return null;
    }

    /**
     * inversion:
     * *   | 'not' inversion
     * *   | comparison
     */
    @Override
    public Object visitInversion(Inversion inversion) {
        return null;
    }

    /**
     * comparison:
     * *   | bitwise_or (comp_op bitwise_or)+
     * *   | bitwise_or
     */
    @Override
    public Object visitComparison(Comparison comparison) {
        return null;
    }

    /**
     * comp_op:
     * *   | '<'
     * *   | '>'
     * *   | '=='
     * *   | '>='
     * *   | '<='
     * *   | '!='
     * *   | 'in'
     * *   | 'not' 'in'
     * *   | 'is'
     * *   | 'is' 'not'
     */
    @Override
    public Object visitCompOp(CompOp compOp) {
        return null;
    }

    /**
     * bitwise_or:
     * *   | bitwise_or '|' bitwise_xor
     * *   | bitwise_xor
     */
    @Override
    public Object visitBitwiseOr(BitwiseOr bitwiseOr) {
        return null;
    }

    /**
     * bitwise_xor:
     * *   | bitwise_xor '^' bitwise_and
     * *   | bitwise_and
     */
    @Override
    public Object visitBitwiseXor(BitwiseXor bitwiseXor) {
        return null;
    }

    /**
     * bitwise_and:
     * *   | bitwise_and '&' shift_expr
     * *   | shift_expr
     */
    @Override
    public Object visitBitwiseAnd(BitwiseAnd bitwiseAnd) {
        return null;
    }

    /**
     * shift_expr:
     * *   | shift_expr '<<' sum
     * *   | shift_expr '>>' sum
     * *   | sum
     */
    @Override
    public Object visitShiftExpr(ShiftExpr shiftExpr) {
        return null;
    }

    /**
     * sum:
     * *   | sum '+' term
     * *   | sum '-' term
     * *   | term
     */
    @Override
    public Object visitSum(Sum sum) {
        return null;
    }

    /**
     * term:
     * *   | term '*' pipe_expr
     * *   | term '/' pipe_expr
     * *   | term '%' pipe_expr
     * *   | term '//' pipe_expr
     * *   | term '@' pipe_expr
     * *   | pipe_expr
     */
    @Override
    public Object visitTerm(Term term) {
        return null;
    }

    /**
     * pipe_expr:
     * *   | pipe_expr '->' factor
     * *   | factor
     */
    @Override
    public Object visitPipeExpr(PipeExpr pipeExpr) {
        return null;
    }

    /**
     * factor:
     * *   | '+' factor
     * *   | '-' factor
     * *   | '~' factor
     * *   | power
     */
    @Override
    public Object visitFactor(Factor factor) {
        return null;
    }

    /**
     * power:
     * *   | primary '**' factor
     * *   | primary
     */
    @Override
    public Object visitPower(Power power) {
        return null;
    }

    /**
     * primary:
     * *   | primary '.' NAME
     * *   | primary parameters
     * *   | primary subscript
     * *   | primary block_suite
     * *   | atom
     */
    @Override
    public Object visitPrimary(Primary primary) {
        return null;
    }

    /**
     * tuple_atom:
     * *   | '(' [named_expr_list] ')'
     */
    @Override
    public Object visitTupleAtom(TupleAtom tupleAtom) {
        return null;
    }


    /**
     * list_atom:
     * *   | '[' [named_expr_list] ']'
     */
    @Override
    public Object visitListAtom(ListAtom listAtom) {
        return null;
    }

    /**
     * set_atom:
     * *   | '{' [exprlist_star] '}'
     */
    @Override
    public Object visitSetAtom(SetAtom setAtom) {
        return null;
    }


    /**
     * dict_atom:
     * *   | '{' [dict_items] '}'
     */
    @Override
    public Object visitDictAtom(DictAtom dictAtom) {
        return null;
    }

    /**
     * atom:
     * *   | tuple_atom
     * *   | list_iter
     * *   | list_atom
     * *   | set_atom
     * *   | dict_iter
     * *   | dict_atom
     * *   | NAME
     * *   | NUMBER
     * *   | STRING
     * *   | 'None'
     * *   | 'True'
     * *   | 'False'
     */
    @Override
    public Object visitAtom(Atom atom) {
        return null;
    }
}
