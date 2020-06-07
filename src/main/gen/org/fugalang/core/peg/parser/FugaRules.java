package org.fugalang.core.peg.parser;

import org.fugalang.core.parser.ParserRule;

import static org.fugalang.core.parser.ParserRule.and_rule;
import static org.fugalang.core.parser.ParserRule.or_rule;

public class FugaRules {
    public static final ParserRule SINGLE_INPUT = or_rule("single_input");
    public static final ParserRule SINGLE_INPUT_3 = and_rule("single_input:3");
    public static final ParserRule FILE_INPUT = and_rule("file_input");
    public static final ParserRule FILE_INPUT_1 = or_rule("file_input:1");
    public static final ParserRule EVAL_INPUT = and_rule("eval_input");
    public static final ParserRule STMT = and_rule("stmt");
    public static final ParserRule STMT_1 = or_rule("stmt:1");
    public static final ParserRule SIMPLE_STMT = and_rule("simple_stmt");
    public static final ParserRule SMALL_STMT = or_rule("small_stmt");
    public static final ParserRule FLOW_STMT = or_rule("flow_stmt");
    public static final ParserRule DEL_STMT = and_rule("del_stmt");
    public static final ParserRule RETURN_STMT = and_rule("return_stmt");
    public static final ParserRule RAISE_STMT = and_rule("raise_stmt");
    public static final ParserRule RAISE_STMT_2 = and_rule("raise_stmt:2");
    public static final ParserRule RAISE_STMT_2_2 = and_rule("raise_stmt:2:2");
    public static final ParserRule NONLOCAL_STMT = and_rule("nonlocal_stmt");
    public static final ParserRule ASSERT_STMT = and_rule("assert_stmt");
    public static final ParserRule ASSERT_STMT_3 = and_rule("assert_stmt:3");
    public static final ParserRule STAR_EXPR = and_rule("star_expr");
    public static final ParserRule EXPRLIST = and_rule("exprlist");
    public static final ParserRule TARGET = or_rule("target");
    public static final ParserRule TARGET_2 = and_rule("target:2");
    public static final ParserRule TARGET_3 = and_rule("target:3");
    public static final ParserRule TARGETLIST = and_rule("targetlist");
    public static final ParserRule EXPR_OR_STAR = or_rule("expr_or_star");
    public static final ParserRule EXPRLIST_STAR = and_rule("exprlist_star");
    public static final ParserRule NAMED_EXPR_STAR = or_rule("named_expr_star");
    public static final ParserRule NAMED_EXPR_LIST = and_rule("named_expr_list");
    public static final ParserRule SUBSCRIPT = and_rule("subscript");
    public static final ParserRule SLICELIST = and_rule("slicelist");
    public static final ParserRule SLICE = or_rule("slice");
    public static final ParserRule SLICE_1 = and_rule("slice:1");
    public static final ParserRule SLICE_EXPR = and_rule("slice_expr");
    public static final ParserRule DICT_ITEM = or_rule("dict_item");
    public static final ParserRule DICT_ITEM_1 = and_rule("dict_item:1");
    public static final ParserRule DICT_ITEM_2 = and_rule("dict_item:2");
    public static final ParserRule DICT_ITEMS = and_rule("dict_items");
    public static final ParserRule AS_NAME = and_rule("as_name");
    public static final ParserRule ITER_FOR = and_rule("iter_for");
    public static final ParserRule ITER_IF = and_rule("iter_if");
    public static final ParserRule ITERATOR = and_rule("iterator");
    public static final ParserRule ASSIGNMENT = and_rule("assignment");
    public static final ParserRule ASSIGNMENT_3 = or_rule("assignment:3");
    public static final ParserRule ASSIGNMENT_3_2 = and_rule("assignment:3:2");
    public static final ParserRule ASSIGNMENT_3_3 = and_rule("assignment:3:3");
    public static final ParserRule AUGASSIGN = or_rule("augassign");
    public static final ParserRule ANNASSIGN = and_rule("annassign");
    public static final ParserRule ANNASSIGN_3 = and_rule("annassign:3");
    public static final ParserRule IMPORT_NAME = and_rule("import_name");
    public static final ParserRule IMPORT_FROM = and_rule("import_from");
    public static final ParserRule IMPORT_FROM_NAMES = or_rule("import_from_names");
    public static final ParserRule IMPORT_FROM_NAMES_2 = and_rule("import_from_names:2");
    public static final ParserRule IMPORT_FROM_ITEMS = or_rule("import_from_items");
    public static final ParserRule IMPORT_FROM_ITEMS_2 = and_rule("import_from_items:2");
    public static final ParserRule IMPORT_AS_NAME = and_rule("import_as_name");
    public static final ParserRule DOTTED_AS_NAME = and_rule("dotted_as_name");
    public static final ParserRule IMPORT_AS_NAMES = and_rule("import_as_names");
    public static final ParserRule DOTTED_AS_NAMES = and_rule("dotted_as_names");
    public static final ParserRule DOTTED_NAME = and_rule("dotted_name");
    public static final ParserRule COMPOUND_STMT = or_rule("compound_stmt");
    public static final ParserRule IF_STMT = and_rule("if_stmt");
    public static final ParserRule ELIF_STMT = and_rule("elif_stmt");
    public static final ParserRule WHILE_STMT = and_rule("while_stmt");
    public static final ParserRule FOR_STMT = and_rule("for_stmt");
    public static final ParserRule TRY_STMT = and_rule("try_stmt");
    public static final ParserRule TRY_STMT_3 = or_rule("try_stmt:3");
    public static final ParserRule WITH_STMT = and_rule("with_stmt");
    public static final ParserRule EXPR_AS_NAME = and_rule("expr_as_name");
    public static final ParserRule BLOCK_SUITE = and_rule("block_suite");
    public static final ParserRule SUITE = or_rule("suite");
    public static final ParserRule SUITE_1 = and_rule("suite:1");
    public static final ParserRule ELSE_SUITE = and_rule("else_suite");
    public static final ParserRule FINALLY_SUITE = and_rule("finally_suite");
    public static final ParserRule FUNC_SUITE = or_rule("func_suite");
    public static final ParserRule FUNC_SUITE_1 = and_rule("func_suite:1");
    public static final ParserRule EXCEPT_CLAUSE = and_rule("except_clause");
    public static final ParserRule EXCEPT_SUITE = and_rule("except_suite");
    public static final ParserRule PARAMETERS = and_rule("parameters");
    public static final ParserRule ARGLIST = and_rule("arglist");
    public static final ParserRule ARGUMENT = or_rule("argument");
    public static final ParserRule ARGUMENT_1 = and_rule("argument:1");
    public static final ParserRule ARGUMENT_2 = and_rule("argument:2");
    public static final ParserRule ARGUMENT_3 = and_rule("argument:3");
    public static final ParserRule ARGUMENT_4 = and_rule("argument:4");
    public static final ParserRule TYPED_ARG_LIST = or_rule("typed_arg_list");
    public static final ParserRule FULL_ARG_LIST = and_rule("full_arg_list");
    public static final ParserRule FULL_ARG_LIST_2 = and_rule("full_arg_list:2");
    public static final ParserRule FULL_ARG_LIST_2_2 = or_rule("full_arg_list:2:2");
    public static final ParserRule ARGS_KWARGS = and_rule("args_kwargs");
    public static final ParserRule ARGS_KWARGS_3 = and_rule("args_kwargs:3");
    public static final ParserRule ARGS_KWARGS_4 = and_rule("args_kwargs:4");
    public static final ParserRule KWARGS = and_rule("kwargs");
    public static final ParserRule DEFAULT_ARG = and_rule("default_arg");
    public static final ParserRule DEFAULT_ARG_2 = and_rule("default_arg:2");
    public static final ParserRule TYPED_ARG = and_rule("typed_arg");
    public static final ParserRule TYPED_ARG_2 = and_rule("typed_arg:2");
    public static final ParserRule SIMPLE_ARG = and_rule("simple_arg");
    public static final ParserRule SIMPLE_ARG_2 = and_rule("simple_arg:2");
    public static final ParserRule FUNC_TYPE_HINT = and_rule("func_type_hint");
    public static final ParserRule FUNC_ARGS = or_rule("func_args");
    public static final ParserRule FUNC_ARGS_2 = and_rule("func_args:2");
    public static final ParserRule FUNCDEF = and_rule("funcdef");
    public static final ParserRule NAMED_EXPR = or_rule("named_expr");
    public static final ParserRule NAMED_EXPR_1 = and_rule("named_expr:1");
    public static final ParserRule CONDITIONAL = and_rule("conditional");
    public static final ParserRule EXPR = or_rule("expr");
    public static final ParserRule DISJUNCTION = or_rule("disjunction");
    public static final ParserRule DISJUNCTION_1 = and_rule("disjunction:1");
    public static final ParserRule CONJUNCTION = or_rule("conjunction");
    public static final ParserRule CONJUNCTION_1 = and_rule("conjunction:1");
    public static final ParserRule INVERSION = or_rule("inversion");
    public static final ParserRule INVERSION_1 = and_rule("inversion:1");
    public static final ParserRule COMPARISON = or_rule("comparison");
    public static final ParserRule COMPARISON_1 = and_rule("comparison:1");
    public static final ParserRule COMPARISON_1_2 = and_rule("comparison:1:2");
    public static final ParserRule COMP_OP = or_rule("comp_op");
    public static final ParserRule COMP_OP_8 = and_rule("comp_op:8");
    public static final ParserRule COMP_OP_10 = and_rule("comp_op:10");
    public static final ParserRule BITWISE_OR = or_rule("bitwise_or");
    public static final ParserRule BITWISE_OR_1 = and_rule("bitwise_or:1");
    public static final ParserRule BITWISE_XOR = or_rule("bitwise_xor");
    public static final ParserRule BITWISE_XOR_1 = and_rule("bitwise_xor:1");
    public static final ParserRule BITWISE_AND = or_rule("bitwise_and");
    public static final ParserRule BITWISE_AND_1 = and_rule("bitwise_and:1");
    public static final ParserRule SHIFT_EXPR = or_rule("shift_expr");
    public static final ParserRule SHIFT_EXPR_1 = and_rule("shift_expr:1");
    public static final ParserRule SHIFT_EXPR_2 = and_rule("shift_expr:2");
    public static final ParserRule SUM = or_rule("sum");
    public static final ParserRule SUM_1 = and_rule("sum:1");
    public static final ParserRule SUM_2 = and_rule("sum:2");
    public static final ParserRule TERM = or_rule("term");
    public static final ParserRule TERM_1 = and_rule("term:1");
    public static final ParserRule TERM_2 = and_rule("term:2");
    public static final ParserRule TERM_3 = and_rule("term:3");
    public static final ParserRule TERM_4 = and_rule("term:4");
    public static final ParserRule TERM_5 = and_rule("term:5");
    public static final ParserRule PIPE_EXPR = or_rule("pipe_expr");
    public static final ParserRule PIPE_EXPR_1 = and_rule("pipe_expr:1");
    public static final ParserRule FACTOR = or_rule("factor");
    public static final ParserRule FACTOR_1 = and_rule("factor:1");
    public static final ParserRule FACTOR_2 = and_rule("factor:2");
    public static final ParserRule FACTOR_3 = and_rule("factor:3");
    public static final ParserRule POWER = or_rule("power");
    public static final ParserRule POWER_1 = and_rule("power:1");
    public static final ParserRule PRIMARY = or_rule("primary");
    public static final ParserRule PRIMARY_1 = and_rule("primary:1");
    public static final ParserRule PRIMARY_2 = and_rule("primary:2");
    public static final ParserRule PRIMARY_3 = and_rule("primary:3");
    public static final ParserRule PRIMARY_4 = and_rule("primary:4");
    public static final ParserRule TUPLE_ATOM = and_rule("tuple_atom");
    public static final ParserRule LIST_ITER = and_rule("list_iter");
    public static final ParserRule LIST_ATOM = and_rule("list_atom");
    public static final ParserRule SET_ATOM = and_rule("set_atom");
    public static final ParserRule DICT_ITER = and_rule("dict_iter");
    public static final ParserRule DICT_ATOM = and_rule("dict_atom");
    public static final ParserRule COLLECTION = or_rule("collection");
    public static final ParserRule ATOM = or_rule("atom");
}
