package org.fugalang.core.peg.visitor;

import org.fugalang.core.peg.wrapper.*;

@SuppressWarnings("unused")
public interface FugaVisitor<T> {

    /**
     * single_input:
     * *   | NEWLINE
     * *   | ENDMARKER
     * *   | simple_stmt
     * *   | compound_stmt NEWLINE
     */
    default T visitSingleInput(SingleInput singleInput) {
        return null;
    }

    /**
     * file_input:
     * *   | [stmt_list] ENDMARKER
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
     * stmt_list:
     * *   | stmt+
     */
    default T visitStmtList(StmtList stmtList) {
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
     * *   | 'nonlocal' name_list
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
     * name_list:
     * *   | ','.NAME+
     */
    default T visitNameList(NameList nameList) {
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
     * *   | t_primary '.' NAME !t_lookahead
     * *   | t_primary subscript !t_lookahead
     * *   | NAME
     * *   | '(' targetlist_sp ')'
     */
    default T visitTarget(Target target) {
        return null;
    }

    /**
     * targetlist_sp (allow_whitespace=true):
     * *   | targetlist
     */
    default T visitTargetlistSp(TargetlistSp targetlistSp) {
        return null;
    }

    /**
     * t_primary (left_recursive):
     * *   | t_primary '.' NAME &t_lookahead
     * *   | t_primary invocation &t_lookahead
     * *   | t_primary subscript &t_lookahead
     * *   | atom &t_lookahead
     */
    default T visitTPrimary(TPrimary tPrimary) {
        return null;
    }

    /**
     * t_lookahead:
     * *   | '.'
     * *   | '('
     * *   | '['
     */
    default T visitTLookahead(TLookahead tLookahead) {
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
     * dict_items (allow_whitespace=true):
     * *   | ','.dict_item+ [',']
     */
    default T visitDictItems(DictItems dictItems) {
        return null;
    }

    /**
     * list_item:
     * *   | star_expr
     * *   | named_expr
     */
    default T visitListItem(ListItem listItem) {
        return null;
    }

    /**
     * list_items (allow_whitespace=true):
     * *   | ','.list_item+ [',']
     */
    default T visitListItems(ListItems listItems) {
        return null;
    }

    /**
     * set_items (allow_whitespace=true):
     * *   | exprlist_star
     */
    default T visitSetItems(SetItems setItems) {
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
     * list_iterator (allow_whitespace=true):
     * *   | expr_or_star iterator
     */
    default T visitListIterator(ListIterator listIterator) {
        return null;
    }

    /**
     * dict_iterator (allow_whitespace=true):
     * *   | dict_item iterator
     */
    default T visitDictIterator(DictIterator dictIterator) {
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
     * *   | import_as_names_sp
     * *   | import_as_names
     */
    default T visitImportFromItems(ImportFromItems importFromItems) {
        return null;
    }

    /**
     * import_as_names_sp (allow_whitespace=true):
     * *   | '(' import_as_names [','] ')'
     */
    default T visitImportAsNamesSp(ImportAsNamesSp importAsNamesSp) {
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
     * block_suite (allow_whitespace=false):
     * *   | '{' NEWLINE stmt_list '}'
     * *   | '{' [simple_stmt] '}'
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
     * invocation:
     * *   | '(' [call_arg_list] ')'
     */
    default T visitInvocation(Invocation invocation) {
        return null;
    }

    /**
     * call_arg_list (allow_whitespace=true):
     * *   | ','.call_arg+ [',']
     */
    default T visitCallArgList(CallArgList callArgList) {
        return null;
    }

    /**
     * call_arg:
     * *   | NAME ':=' expr
     * *   | NAME '=' expr
     * *   | '**' expr
     * *   | '*' expr
     * *   | expr
     */
    default T visitCallArg(CallArg callArg) {
        return null;
    }

    /**
     * typed_arg_list (allow_whitespace=true):
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
     * simple_args:
     * *   | ','.simple_arg+
     */
    default T visitSimpleArgs(SimpleArgs simpleArgs) {
        return null;
    }

    /**
     * builder_hint:
     * *   | '<' name_list '>'
     */
    default T visitBuilderHint(BuilderHint builderHint) {
        return null;
    }

    /**
     * builder_args:
     * *   | simple_args
     * *   | '(' [typed_arg_list] ')'
     */
    default T visitBuilderArgs(BuilderArgs builderArgs) {
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
     * *   | disjunction
     */
    default T visitExpr(Expr expr) {
        return null;
    }

    /**
     * disjunction (left_recursive):
     * *   | disjunction 'or' conjunction
     * *   | conjunction
     */
    default T visitDisjunction(Disjunction disjunction) {
        return null;
    }

    /**
     * conjunction (left_recursive):
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
     * bitwise_or (left_recursive):
     * *   | bitwise_or '|' bitwise_xor
     * *   | bitwise_xor
     */
    default T visitBitwiseOr(BitwiseOr bitwiseOr) {
        return null;
    }

    /**
     * bitwise_xor (left_recursive):
     * *   | bitwise_xor '^' bitwise_and
     * *   | bitwise_and
     */
    default T visitBitwiseXor(BitwiseXor bitwiseXor) {
        return null;
    }

    /**
     * bitwise_and (left_recursive):
     * *   | bitwise_and '&' shift_expr
     * *   | shift_expr
     */
    default T visitBitwiseAnd(BitwiseAnd bitwiseAnd) {
        return null;
    }

    /**
     * shift_expr (left_recursive):
     * *   | shift_expr '<<' sum
     * *   | shift_expr '>>' sum
     * *   | sum
     */
    default T visitShiftExpr(ShiftExpr shiftExpr) {
        return null;
    }

    /**
     * sum (left_recursive):
     * *   | sum '+' term
     * *   | sum '-' term
     * *   | term
     */
    default T visitSum(Sum sum) {
        return null;
    }

    /**
     * term (left_recursive):
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
     * pipe_expr (left_recursive):
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
     * primary (left_recursive):
     * *   | primary '.' NAME
     * *   | primary invocation
     * *   | primary subscript
     * *   | atom
     */
    default T visitPrimary(Primary primary) {
        return null;
    }

    /**
     * tuple_atom:
     * *   | '(' [list_items] ')'
     */
    default T visitTupleAtom(TupleAtom tupleAtom) {
        return null;
    }

    /**
     * list_iterable:
     * *   | '[' list_iterator ']'
     */
    default T visitListIterable(ListIterable listIterable) {
        return null;
    }

    /**
     * list_atom:
     * *   | '[' [list_items] ']'
     */
    default T visitListAtom(ListAtom listAtom) {
        return null;
    }

    /**
     * set_atom:
     * *   | '{' [set_items] '}'
     */
    default T visitSetAtom(SetAtom setAtom) {
        return null;
    }

    /**
     * dict_iterable:
     * *   | '{' dict_iterator '}'
     */
    default T visitDictIterable(DictIterable dictIterable) {
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
     * builder:
     * *   | NAME simple_args ':' expr
     * *   | NAME [builder_hint] [builder_args] block_suite
     */
    default T visitBuilder(Builder builder) {
        return null;
    }

    /**
     * atom:
     * *   | tuple_atom
     * *   | list_iterable
     * *   | list_atom
     * *   | set_atom
     * *   | dict_iterable
     * *   | dict_atom
     * *   | builder
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
