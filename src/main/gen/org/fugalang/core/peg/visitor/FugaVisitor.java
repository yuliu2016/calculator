package org.fugalang.core.peg.visitor;

import org.fugalang.core.peg.wrapper.*;

@SuppressWarnings("unused")
public interface FugaVisitor<T> {

    /**
     * single_input:
     * *   | NEWLINE
     * *   | simple_stmt
     * *   | compound_stmt NEWLINE
     */
    default T visitSingleInput(SingleInput singleInput) {
        return null;
    }

    /**
     * file_input:
     * *   | (NEWLINE | stmt)* ENDMARKER
     */
    default T visitFileInput(FileInput fileInput) {
        return null;
    }

    /**
     * eval_input:
     * *   | exprlist NEWLINE* ENDMARKER
     */
    default T visitEvalInput(EvalInput evalInput) {
        return null;
    }

    /**
     * stmt:
     * *   | (simple_stmt | compound_stmt) NEWLINE
     */
    default T visitStmt(Stmt stmt) {
        return null;
    }

    /**
     * simple_stmt:
     * *   | ';'.small_stmt+ [';']
     */
    default T visitSimpleStmt(SimpleStmt simpleStmt) {
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
    default T visitSmallStmt(SmallStmt smallStmt) {
        return null;
    }

    /**
     * del_stmt:
     * *   | 'del' targetlist
     */
    default T visitDelStmt(DelStmt delStmt) {
        return null;
    }

    /**
     * return_stmt:
     * *   | 'return' [exprlist_star]
     */
    default T visitReturnStmt(ReturnStmt returnStmt) {
        return null;
    }

    /**
     * raise_stmt:
     * *   | 'raise' expr ['from' expr]
     */
    default T visitRaiseStmt(RaiseStmt raiseStmt) {
        return null;
    }

    /**
     * nonlocal_stmt:
     * *   | 'nonlocal' ','.NAME+
     */
    default T visitNonlocalStmt(NonlocalStmt nonlocalStmt) {
        return null;
    }

    /**
     * assert_stmt:
     * *   | 'assert' expr [',' expr]
     */
    default T visitAssertStmt(AssertStmt assertStmt) {
        return null;
    }

    /**
     * star_expr:
     * *   | '*' bitwise_or
     */
    default T visitStarExpr(StarExpr starExpr) {
        return null;
    }

    /**
     * exprlist:
     * *   | ','.expr+ [',']
     */
    default T visitExprlist(Exprlist exprlist) {
        return null;
    }

    /**
     * target:
     * *   | NAME
     * *   | '(' targetlist ')'
     * *   | '*' primary
     * *   | primary
     */
    default T visitTarget(Target target) {
        return null;
    }

    /**
     * targetlist:
     * *   | ','.target+ [',']
     */
    default T visitTargetlist(Targetlist targetlist) {
        return null;
    }

    /**
     * expr_or_star:
     * *   | star_expr
     * *   | expr
     */
    default T visitExprOrStar(ExprOrStar exprOrStar) {
        return null;
    }

    /**
     * exprlist_star:
     * *   | ','.expr_or_star+ [',']
     */
    default T visitExprlistStar(ExprlistStar exprlistStar) {
        return null;
    }

    /**
     * named_expr_star:
     * *   | star_expr
     * *   | named_expr
     */
    default T visitNamedExprStar(NamedExprStar namedExprStar) {
        return null;
    }

    /**
     * named_expr_list:
     * *   | ','.named_expr_star+ [',']
     */
    default T visitNamedExprList(NamedExprList namedExprList) {
        return null;
    }

    /**
     * subscript:
     * *   | '[' slicelist ']'
     */
    default T visitSubscript(Subscript subscript) {
        return null;
    }

    /**
     * slicelist:
     * *   | ','.slice+ [',']
     */
    default T visitSlicelist(Slicelist slicelist) {
        return null;
    }

    /**
     * slice:
     * *   | [expr] slice_expr [slice_expr]
     * *   | expr
     */
    default T visitSlice(Slice slice) {
        return null;
    }

    /**
     * slice_expr:
     * *   | ':' [expr]
     */
    default T visitSliceExpr(SliceExpr sliceExpr) {
        return null;
    }

    /**
     * dict_item:
     * *   | expr ':' expr
     * *   | '**' bitwise_or
     */
    default T visitDictItem(DictItem dictItem) {
        return null;
    }

    /**
     * dict_items:
     * *   | ','.dict_item+ [',']
     */
    default T visitDictItems(DictItems dictItems) {
        return null;
    }

    /**
     * as_name:
     * *   | 'as' NAME
     */
    default T visitAsName(AsName asName) {
        return null;
    }

    /**
     * iter_for:
     * *   | 'for' targetlist 'in' disjunction [iter_if]
     */
    default T visitIterFor(IterFor iterFor) {
        return null;
    }

    /**
     * iter_if:
     * *   | 'if' named_expr
     */
    default T visitIterIf(IterIf iterIf) {
        return null;
    }

    /**
     * iterator:
     * *   | iter_for* 'for' targetlist [iter_if]
     */
    default T visitIterator(Iterator iterator) {
        return null;
    }

    /**
     * assignment:
     * *   | pubassign
     * *   | annassign
     * *   | augassign
     * *   | simple_assign
     */
    default T visitAssignment(Assignment assignment) {
        return null;
    }

    /**
     * pubassign:
     * *   | '/' NAME '=' exprlist
     */
    default T visitPubassign(Pubassign pubassign) {
        return null;
    }

    /**
     * annassign:
     * *   | target ':' expr ['=' exprlist]
     */
    default T visitAnnassign(Annassign annassign) {
        return null;
    }

    /**
     * augassign:
     * *   | target augassign_op exprlist
     */
    default T visitAugassign(Augassign augassign) {
        return null;
    }

    /**
     * simple_assign:
     * *   | (targetlist '=')* exprlist_star
     */
    default T visitSimpleAssign(SimpleAssign simpleAssign) {
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
    default T visitAugassignOp(AugassignOp augassignOp) {
        return null;
    }

    /**
     * import_name:
     * *   | 'import' dotted_as_names
     */
    default T visitImportName(ImportName importName) {
        return null;
    }

    /**
     * import_from:
     * *   | 'from' import_from_names 'import' import_from_items
     */
    default T visitImportFrom(ImportFrom importFrom) {
        return null;
    }

    /**
     * import_from_names:
     * *   | dotted_name
     * *   | '.'+ [dotted_name]
     */
    default T visitImportFromNames(ImportFromNames importFromNames) {
        return null;
    }

    /**
     * import_from_items:
     * *   | '*'
     * *   | '(' import_as_names [','] ')'
     * *   | import_as_names
     */
    default T visitImportFromItems(ImportFromItems importFromItems) {
        return null;
    }

    /**
     * import_as_name:
     * *   | NAME [as_name]
     */
    default T visitImportAsName(ImportAsName importAsName) {
        return null;
    }

    /**
     * dotted_as_name:
     * *   | dotted_name [as_name]
     */
    default T visitDottedAsName(DottedAsName dottedAsName) {
        return null;
    }

    /**
     * import_as_names:
     * *   | ','.import_as_name+
     */
    default T visitImportAsNames(ImportAsNames importAsNames) {
        return null;
    }

    /**
     * dotted_as_names:
     * *   | ','.dotted_as_name+
     */
    default T visitDottedAsNames(DottedAsNames dottedAsNames) {
        return null;
    }

    /**
     * dotted_name:
     * *   | '.'.NAME+
     */
    default T visitDottedName(DottedName dottedName) {
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
    default T visitCompoundStmt(CompoundStmt compoundStmt) {
        return null;
    }

    /**
     * if_stmt:
     * *   | 'if' named_expr suite elif_stmt* [else_suite]
     */
    default T visitIfStmt(IfStmt ifStmt) {
        return null;
    }

    /**
     * elif_stmt:
     * *   | 'elif' named_expr suite
     */
    default T visitElifStmt(ElifStmt elifStmt) {
        return null;
    }

    /**
     * while_stmt:
     * *   | 'while' named_expr suite [else_suite]
     */
    default T visitWhileStmt(WhileStmt whileStmt) {
        return null;
    }

    /**
     * for_stmt:
     * *   | 'for' targetlist 'in' exprlist suite [else_suite]
     */
    default T visitForStmt(ForStmt forStmt) {
        return null;
    }

    /**
     * try_stmt:
     * *   | 'try' suite (except_suite | finally_suite)
     */
    default T visitTryStmt(TryStmt tryStmt) {
        return null;
    }

    /**
     * with_stmt:
     * *   | 'with' ','.expr_as_name+ suite
     */
    default T visitWithStmt(WithStmt withStmt) {
        return null;
    }

    /**
     * expr_as_name:
     * *   | expr [as_name]
     */
    default T visitExprAsName(ExprAsName exprAsName) {
        return null;
    }

    /**
     * block_suite:
     * *   | '{' NEWLINE stmt+ '}'
     */
    default T visitBlockSuite(BlockSuite blockSuite) {
        return null;
    }

    /**
     * suite:
     * *   | ':' simple_stmt
     * *   | block_suite
     */
    default T visitSuite(Suite suite) {
        return null;
    }

    /**
     * else_suite:
     * *   | 'else' suite
     */
    default T visitElseSuite(ElseSuite elseSuite) {
        return null;
    }

    /**
     * finally_suite:
     * *   | 'finally' suite
     */
    default T visitFinallySuite(FinallySuite finallySuite) {
        return null;
    }

    /**
     * func_suite:
     * *   | ':' expr
     * *   | block_suite
     */
    default T visitFuncSuite(FuncSuite funcSuite) {
        return null;
    }

    /**
     * except_clause:
     * *   | 'except' [expr_as_name] suite
     */
    default T visitExceptClause(ExceptClause exceptClause) {
        return null;
    }

    /**
     * except_suite:
     * *   | except_clause+ [else_suite] [finally_suite]
     */
    default T visitExceptSuite(ExceptSuite exceptSuite) {
        return null;
    }

    /**
     * parameters:
     * *   | '(' [arglist] ')'
     */
    default T visitParameters(Parameters parameters) {
        return null;
    }

    /**
     * arglist:
     * *   | ','.argument+ [',']
     */
    default T visitArglist(Arglist arglist) {
        return null;
    }

    /**
     * argument:
     * *   | NAME ':=' expr
     * *   | NAME '=' expr
     * *   | '**' expr
     * *   | '*' expr
     * *   | expr
     */
    default T visitArgument(Argument argument) {
        return null;
    }

    /**
     * typed_arg_list:
     * *   | kwargs
     * *   | args_kwargs
     * *   | full_arg_list
     */
    default T visitTypedArgList(TypedArgList typedArgList) {
        return null;
    }

    /**
     * full_arg_list:
     * *   | ','.default_arg+ [',' [kwargs | args_kwargs]]
     */
    default T visitFullArgList(FullArgList fullArgList) {
        return null;
    }

    /**
     * args_kwargs:
     * *   | '*' [typed_arg] (',' default_arg)* [',' [kwargs]]
     */
    default T visitArgsKwargs(ArgsKwargs argsKwargs) {
        return null;
    }

    /**
     * kwargs:
     * *   | '**' typed_arg [',']
     */
    default T visitKwargs(Kwargs kwargs) {
        return null;
    }

    /**
     * default_arg:
     * *   | typed_arg ['=' expr]
     */
    default T visitDefaultArg(DefaultArg defaultArg) {
        return null;
    }

    /**
     * typed_arg:
     * *   | NAME [':' expr]
     */
    default T visitTypedArg(TypedArg typedArg) {
        return null;
    }

    /**
     * simple_arg:
     * *   | NAME ['=' expr]
     */
    default T visitSimpleArg(SimpleArg simpleArg) {
        return null;
    }

    /**
     * func_type_hint:
     * *   | '<' expr '>'
     */
    default T visitFuncTypeHint(FuncTypeHint funcTypeHint) {
        return null;
    }

    /**
     * func_args:
     * *   | simple_arg+
     * *   | '(' [typed_arg_list] ')'
     */
    default T visitFuncArgs(FuncArgs funcArgs) {
        return null;
    }

    /**
     * funcdef:
     * *   | 'def' [func_type_hint] [func_args] func_suite
     */
    default T visitFuncdef(Funcdef funcdef) {
        return null;
    }

    /**
     * named_expr:
     * *   | NAME ':=' expr
     * *   | expr
     */
    default T visitNamedExpr(NamedExpr namedExpr) {
        return null;
    }

    /**
     * conditional:
     * *   | 'if' disjunction '?' disjunction ':' expr
     */
    default T visitConditional(Conditional conditional) {
        return null;
    }

    /**
     * expr:
     * *   | conditional
     * *   | funcdef
     * *   | disjunction
     */
    default T visitExpr(Expr expr) {
        return null;
    }

    /**
     * disjunction:
     * *   | disjunction 'or' conjunction
     * *   | conjunction
     */
    default T visitDisjunction(Disjunction disjunction) {
        return null;
    }

    /**
     * conjunction:
     * *   | conjunction 'and' inversion
     * *   | inversion
     */
    default T visitConjunction(Conjunction conjunction) {
        return null;
    }

    /**
     * inversion:
     * *   | 'not' inversion
     * *   | comparison
     */
    default T visitInversion(Inversion inversion) {
        return null;
    }

    /**
     * comparison:
     * *   | bitwise_or (comp_op bitwise_or)+
     * *   | bitwise_or
     */
    default T visitComparison(Comparison comparison) {
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
    default T visitCompOp(CompOp compOp) {
        return null;
    }

    /**
     * bitwise_or:
     * *   | bitwise_or '|' bitwise_xor
     * *   | bitwise_xor
     */
    default T visitBitwiseOr(BitwiseOr bitwiseOr) {
        return null;
    }

    /**
     * bitwise_xor:
     * *   | bitwise_xor '^' bitwise_and
     * *   | bitwise_and
     */
    default T visitBitwiseXor(BitwiseXor bitwiseXor) {
        return null;
    }

    /**
     * bitwise_and:
     * *   | bitwise_and '&' shift_expr
     * *   | shift_expr
     */
    default T visitBitwiseAnd(BitwiseAnd bitwiseAnd) {
        return null;
    }

    /**
     * shift_expr:
     * *   | shift_expr '<<' sum
     * *   | shift_expr '>>' sum
     * *   | sum
     */
    default T visitShiftExpr(ShiftExpr shiftExpr) {
        return null;
    }

    /**
     * sum:
     * *   | sum '+' term
     * *   | sum '-' term
     * *   | term
     */
    default T visitSum(Sum sum) {
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
    default T visitTerm(Term term) {
        return null;
    }

    /**
     * pipe_expr:
     * *   | pipe_expr '->' factor
     * *   | factor
     */
    default T visitPipeExpr(PipeExpr pipeExpr) {
        return null;
    }

    /**
     * factor:
     * *   | '+' factor
     * *   | '-' factor
     * *   | '~' factor
     * *   | power
     */
    default T visitFactor(Factor factor) {
        return null;
    }

    /**
     * power:
     * *   | primary '**' factor
     * *   | primary
     */
    default T visitPower(Power power) {
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
    default T visitPrimary(Primary primary) {
        return null;
    }

    /**
     * tuple_atom:
     * *   | '(' [named_expr_list] ')'
     */
    default T visitTupleAtom(TupleAtom tupleAtom) {
        return null;
    }

    /**
     * list_iter:
     * *   | '[' expr_or_star iterator ']'
     */
    default T visitListIter(ListIter listIter) {
        return null;
    }

    /**
     * list_atom:
     * *   | '[' [named_expr_list] ']'
     */
    default T visitListAtom(ListAtom listAtom) {
        return null;
    }

    /**
     * set_atom:
     * *   | '{' [exprlist_star] '}'
     */
    default T visitSetAtom(SetAtom setAtom) {
        return null;
    }

    /**
     * dict_iter:
     * *   | '{' dict_item iterator '}'
     */
    default T visitDictIter(DictIter dictIter) {
        return null;
    }

    /**
     * dict_atom:
     * *   | '{' [dict_items] '}'
     */
    default T visitDictAtom(DictAtom dictAtom) {
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
    default T visitAtom(Atom atom) {
        return null;
    }
}
