package org.fugalang.core.peg.parser;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.token.TokenType;

import static org.fugalang.core.peg.parser.FugaRules.*;

@SuppressWarnings("UnusedReturnValue")
public class FugaParser {

    /**
     * single_input:
     * *   | NEWLINE
     * *   | ENDMARKER
     * *   | simple_stmt
     * *   | compound_stmt NEWLINE
     */
    public static boolean single_input(ParseTree t) {
        var m = t.enter(SINGLE_INPUT);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NEWLINE);
        r = r || t.consume(TokenType.ENDMARKER);
        r = r || simple_stmt(t);
        r = r || single_input_4(t);
        t.exit(r);
        return r;
    }

    /**
     * compound_stmt NEWLINE
     */
    private static boolean single_input_4(ParseTree t) {
        var m = t.enter(SINGLE_INPUT_4);
        if (m != null) return m;
        boolean r;
        r = compound_stmt(t);
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    /**
     * file_input:
     * *   | [stmt_list] ENDMARKER
     */
    public static boolean file_input(ParseTree t) {
        var m = t.enter(FILE_INPUT);
        if (m != null) return m;
        boolean r;
        stmt_list(t);
        r = t.consume(TokenType.ENDMARKER);
        t.exit(r);
        return r;
    }

    /**
     * eval_input:
     * *   | exprlist NEWLINE* ENDMARKER
     */
    public static boolean eval_input(ParseTree t) {
        var m = t.enter(EVAL_INPUT);
        if (m != null) return m;
        boolean r;
        r = exprlist(t);
        if (r) eval_input_loop(t);
        r = r && t.consume(TokenType.ENDMARKER);
        t.exit(r);
        return r;
    }

    private static void eval_input_loop(ParseTree t) {
        t.enterLoop();
        while (true) {
            if (!t.consume(TokenType.NEWLINE)) break;
        }
        t.exitLoop();
    }

    /**
     * stmt_list:
     * *   | stmt+
     */
    public static boolean stmt_list(ParseTree t) {
        var m = t.enter(STMT_LIST);
        if (m != null) return m;
        boolean r;
        r = stmt_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean stmt_loop(ParseTree t) {
        t.enterLoop();
        var r = stmt(t);
        if (r) while (true) {
            if (!stmt(t)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * stmt:
     * *   | (simple_stmt | compound_stmt) NEWLINE
     */
    public static boolean stmt(ParseTree t) {
        var m = t.enter(STMT);
        if (m != null) return m;
        boolean r;
        r = stmt_1(t);
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    /**
     * simple_stmt | compound_stmt
     */
    private static boolean stmt_1(ParseTree t) {
        var m = t.enter(STMT_1);
        if (m != null) return m;
        boolean r;
        r = simple_stmt(t);
        r = r || compound_stmt(t);
        t.exit(r);
        return r;
    }

    /**
     * simple_stmt:
     * *   | ';'.small_stmt+ [';']
     */
    public static boolean simple_stmt(ParseTree t) {
        var m = t.enter(SIMPLE_STMT);
        if (m != null) return m;
        boolean r;
        r = small_stmt_loop(t);
        if (r) t.consume(";");
        t.exit(r);
        return r;
    }

    private static boolean small_stmt_loop(ParseTree t) {
        t.enterLoop();
        var r = small_stmt(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(";") && small_stmt(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
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
    public static boolean small_stmt(ParseTree t) {
        var m = t.enter(SMALL_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("pass");
        r = r || t.consume("break");
        r = r || t.consume("continue");
        r = r || return_stmt(t);
        r = r || raise_stmt(t);
        r = r || del_stmt(t);
        r = r || nonlocal_stmt(t);
        r = r || assert_stmt(t);
        r = r || import_name(t);
        r = r || import_from(t);
        r = r || assignment(t);
        t.exit(r);
        return r;
    }

    /**
     * del_stmt:
     * *   | 'del' targetlist
     */
    public static boolean del_stmt(ParseTree t) {
        var m = t.enter(DEL_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("del");
        r = r && targetlist(t);
        t.exit(r);
        return r;
    }

    /**
     * return_stmt:
     * *   | 'return' [exprlist_star]
     */
    public static boolean return_stmt(ParseTree t) {
        var m = t.enter(RETURN_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("return");
        if (r) exprlist_star(t);
        t.exit(r);
        return r;
    }

    /**
     * raise_stmt:
     * *   | 'raise' expr ['from' expr]
     */
    public static boolean raise_stmt(ParseTree t) {
        var m = t.enter(RAISE_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("raise");
        r = r && expr(t);
        if (r) raise_stmt_3(t);
        t.exit(r);
        return r;
    }

    /**
     * 'from' expr
     */
    private static boolean raise_stmt_3(ParseTree t) {
        var m = t.enter(RAISE_STMT_3);
        if (m != null) return m;
        boolean r;
        r = t.consume("from");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * nonlocal_stmt:
     * *   | 'nonlocal' name_list
     */
    public static boolean nonlocal_stmt(ParseTree t) {
        var m = t.enter(NONLOCAL_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("nonlocal");
        r = r && name_list(t);
        t.exit(r);
        return r;
    }

    /**
     * assert_stmt:
     * *   | 'assert' expr [',' expr]
     */
    public static boolean assert_stmt(ParseTree t) {
        var m = t.enter(ASSERT_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("assert");
        r = r && expr(t);
        if (r) assert_stmt_3(t);
        t.exit(r);
        return r;
    }

    /**
     * ',' expr
     */
    private static boolean assert_stmt_3(ParseTree t) {
        var m = t.enter(ASSERT_STMT_3);
        if (m != null) return m;
        boolean r;
        r = t.consume(",");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * name_list:
     * *   | ','.NAME+
     */
    public static boolean name_list(ParseTree t) {
        var m = t.enter(NAME_LIST);
        if (m != null) return m;
        boolean r;
        r = name_list_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean name_list_loop(ParseTree t) {
        t.enterLoop();
        var r = t.consume(TokenType.NAME);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && t.consume(TokenType.NAME)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * star_expr:
     * *   | '*' bitwise_or
     */
    public static boolean star_expr(ParseTree t) {
        var m = t.enter(STAR_EXPR);
        if (m != null) return m;
        boolean r;
        r = t.consume("*");
        r = r && bitwise_or(t);
        t.exit(r);
        return r;
    }

    /**
     * exprlist:
     * *   | ','.expr+ [',']
     */
    public static boolean exprlist(ParseTree t) {
        var m = t.enter(EXPRLIST);
        if (m != null) return m;
        boolean r;
        r = expr_loop(t);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static boolean expr_loop(ParseTree t) {
        t.enterLoop();
        var r = expr(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && expr(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * target:
     * *   | t_primary '.' NAME !t_lookahead
     * *   | t_primary subscript !t_lookahead
     * *   | NAME
     * *   | '(' targetlist_sp ')'
     */
    public static boolean target(ParseTree t) {
        var m = t.enter(TARGET);
        if (m != null) return m;
        boolean r;
        r = target_1(t);
        r = r || target_2(t);
        r = r || t.consume(TokenType.NAME);
        r = r || target_4(t);
        t.exit(r);
        return r;
    }

    /**
     * t_primary '.' NAME !t_lookahead
     */
    private static boolean target_1(ParseTree t) {
        var m = t.enter(TARGET_1);
        if (m != null) return m;
        boolean r;
        r = t_primary(t);
        r = r && t.consume(".");
        r = r && t.consume(TokenType.NAME);
        r = r && !t_lookahead(t.test());
        t.exit(r);
        return r;
    }

    /**
     * t_primary subscript !t_lookahead
     */
    private static boolean target_2(ParseTree t) {
        var m = t.enter(TARGET_2);
        if (m != null) return m;
        boolean r;
        r = t_primary(t);
        r = r && subscript(t);
        r = r && !t_lookahead(t.test());
        t.exit(r);
        return r;
    }

    /**
     * '(' targetlist_sp ')'
     */
    private static boolean target_4(ParseTree t) {
        var m = t.enter(TARGET_4);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        r = r && targetlist_sp(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * targetlist_sp (allow_whitespace=true):
     * *   | targetlist
     */
    public static boolean targetlist_sp(ParseTree t) {
        var m = t.enter(TARGETLIST_SP);
        if (m != null) return m;
        boolean r;
        r = targetlist(t);
        t.exit(r);
        return r;
    }

    /**
     * t_primary (left_recursive):
     * *   | t_primary '.' NAME &t_lookahead
     * *   | t_primary invocation &t_lookahead
     * *   | t_primary subscript &t_lookahead
     * *   | atom &t_lookahead
     */
    public static boolean t_primary(ParseTree t) {
        var m = t.enter(T_PRIMARY);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = t_primary_1(t);
            r = r || t_primary_2(t);
            r = r || t_primary_3(t);
            r = r || t_primary_4(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * t_primary '.' NAME &t_lookahead
     */
    private static boolean t_primary_1(ParseTree t) {
        var m = t.enter(T_PRIMARY_1);
        if (m != null) return m;
        boolean r;
        r = t_primary(t);
        r = r && t.consume(".");
        r = r && t.consume(TokenType.NAME);
        r = r && t_lookahead(t.test());
        t.exit(r);
        return r;
    }

    /**
     * t_primary invocation &t_lookahead
     */
    private static boolean t_primary_2(ParseTree t) {
        var m = t.enter(T_PRIMARY_2);
        if (m != null) return m;
        boolean r;
        r = t_primary(t);
        r = r && invocation(t);
        r = r && t_lookahead(t.test());
        t.exit(r);
        return r;
    }

    /**
     * t_primary subscript &t_lookahead
     */
    private static boolean t_primary_3(ParseTree t) {
        var m = t.enter(T_PRIMARY_3);
        if (m != null) return m;
        boolean r;
        r = t_primary(t);
        r = r && subscript(t);
        r = r && t_lookahead(t.test());
        t.exit(r);
        return r;
    }

    /**
     * atom &t_lookahead
     */
    private static boolean t_primary_4(ParseTree t) {
        var m = t.enter(T_PRIMARY_4);
        if (m != null) return m;
        boolean r;
        r = atom(t);
        r = r && t_lookahead(t.test());
        t.exit(r);
        return r;
    }

    /**
     * t_lookahead:
     * *   | '.'
     * *   | '('
     * *   | '['
     */
    public static boolean t_lookahead(ParseTree t) {
        var m = t.enter(T_LOOKAHEAD);
        if (m != null) return m;
        boolean r;
        r = t.consume(".");
        r = r || t.consume("(");
        r = r || t.consume("[");
        t.exit(r);
        return r;
    }

    /**
     * targetlist:
     * *   | ','.target+ [',']
     */
    public static boolean targetlist(ParseTree t) {
        var m = t.enter(TARGETLIST);
        if (m != null) return m;
        boolean r;
        r = target_loop(t);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static boolean target_loop(ParseTree t) {
        t.enterLoop();
        var r = target(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && target(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * expr_or_star:
     * *   | star_expr
     * *   | expr
     */
    public static boolean expr_or_star(ParseTree t) {
        var m = t.enter(EXPR_OR_STAR);
        if (m != null) return m;
        boolean r;
        r = star_expr(t);
        r = r || expr(t);
        t.exit(r);
        return r;
    }

    /**
     * exprlist_star:
     * *   | ','.expr_or_star+ [',']
     */
    public static boolean exprlist_star(ParseTree t) {
        var m = t.enter(EXPRLIST_STAR);
        if (m != null) return m;
        boolean r;
        r = expr_or_star_loop(t);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static boolean expr_or_star_loop(ParseTree t) {
        t.enterLoop();
        var r = expr_or_star(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && expr_or_star(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * subscript:
     * *   | '[' slicelist ']'
     */
    public static boolean subscript(ParseTree t) {
        var m = t.enter(SUBSCRIPT);
        if (m != null) return m;
        boolean r;
        r = t.consume("[");
        r = r && slicelist(t);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }

    /**
     * slicelist:
     * *   | ','.slice+ [',']
     */
    public static boolean slicelist(ParseTree t) {
        var m = t.enter(SLICELIST);
        if (m != null) return m;
        boolean r;
        r = slice_loop(t);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static boolean slice_loop(ParseTree t) {
        t.enterLoop();
        var r = slice(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && slice(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * slice:
     * *   | [expr] slice_expr [slice_expr]
     * *   | expr
     */
    public static boolean slice(ParseTree t) {
        var m = t.enter(SLICE);
        if (m != null) return m;
        boolean r;
        r = slice_1(t);
        r = r || expr(t);
        t.exit(r);
        return r;
    }

    /**
     * [expr] slice_expr [slice_expr]
     */
    private static boolean slice_1(ParseTree t) {
        var m = t.enter(SLICE_1);
        if (m != null) return m;
        boolean r;
        expr(t);
        r = slice_expr(t);
        if (r) slice_expr(t);
        t.exit(r);
        return r;
    }

    /**
     * slice_expr:
     * *   | ':' [expr]
     */
    public static boolean slice_expr(ParseTree t) {
        var m = t.enter(SLICE_EXPR);
        if (m != null) return m;
        boolean r;
        r = t.consume(":");
        if (r) expr(t);
        t.exit(r);
        return r;
    }

    /**
     * dict_item:
     * *   | expr ':' expr
     * *   | '**' bitwise_or
     */
    public static boolean dict_item(ParseTree t) {
        var m = t.enter(DICT_ITEM);
        if (m != null) return m;
        boolean r;
        r = dict_item_1(t);
        r = r || dict_item_2(t);
        t.exit(r);
        return r;
    }

    /**
     * expr ':' expr
     */
    private static boolean dict_item_1(ParseTree t) {
        var m = t.enter(DICT_ITEM_1);
        if (m != null) return m;
        boolean r;
        r = expr(t);
        r = r && t.consume(":");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * '**' bitwise_or
     */
    private static boolean dict_item_2(ParseTree t) {
        var m = t.enter(DICT_ITEM_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("**");
        r = r && bitwise_or(t);
        t.exit(r);
        return r;
    }

    /**
     * dict_items (allow_whitespace=true):
     * *   | ','.dict_item+ [',']
     */
    public static boolean dict_items(ParseTree t) {
        var m = t.enter(DICT_ITEMS);
        if (m != null) return m;
        boolean r;
        r = dict_item_loop(t);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static boolean dict_item_loop(ParseTree t) {
        t.enterLoop();
        var r = dict_item(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && dict_item(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * list_item:
     * *   | star_expr
     * *   | named_expr
     */
    public static boolean list_item(ParseTree t) {
        var m = t.enter(LIST_ITEM);
        if (m != null) return m;
        boolean r;
        r = star_expr(t);
        r = r || named_expr(t);
        t.exit(r);
        return r;
    }

    /**
     * list_items (allow_whitespace=true):
     * *   | ','.list_item+ [',']
     */
    public static boolean list_items(ParseTree t) {
        var m = t.enter(LIST_ITEMS);
        if (m != null) return m;
        boolean r;
        r = list_item_loop(t);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static boolean list_item_loop(ParseTree t) {
        t.enterLoop();
        var r = list_item(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && list_item(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * set_items (allow_whitespace=true):
     * *   | exprlist_star
     */
    public static boolean set_items(ParseTree t) {
        var m = t.enter(SET_ITEMS);
        if (m != null) return m;
        boolean r;
        r = exprlist_star(t);
        t.exit(r);
        return r;
    }

    /**
     * as_name:
     * *   | 'as' NAME
     */
    public static boolean as_name(ParseTree t) {
        var m = t.enter(AS_NAME);
        if (m != null) return m;
        boolean r;
        r = t.consume("as");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * iter_for:
     * *   | 'for' targetlist 'in' disjunction [iter_if]
     */
    public static boolean iter_for(ParseTree t) {
        var m = t.enter(ITER_FOR);
        if (m != null) return m;
        boolean r;
        r = t.consume("for");
        r = r && targetlist(t);
        r = r && t.consume("in");
        r = r && disjunction(t);
        if (r) iter_if(t);
        t.exit(r);
        return r;
    }

    /**
     * iter_if:
     * *   | 'if' named_expr
     */
    public static boolean iter_if(ParseTree t) {
        var m = t.enter(ITER_IF);
        if (m != null) return m;
        boolean r;
        r = t.consume("if");
        r = r && named_expr(t);
        t.exit(r);
        return r;
    }

    /**
     * iterator:
     * *   | iter_for* 'for' targetlist [iter_if]
     */
    public static boolean iterator(ParseTree t) {
        var m = t.enter(ITERATOR);
        if (m != null) return m;
        boolean r;
        iter_for_loop(t);
        r = t.consume("for");
        r = r && targetlist(t);
        if (r) iter_if(t);
        t.exit(r);
        return r;
    }

    private static void iter_for_loop(ParseTree t) {
        t.enterLoop();
        while (true) {
            if (!iter_for(t)) break;
        }
        t.exitLoop();
    }

    /**
     * list_iterator (allow_whitespace=true):
     * *   | expr_or_star iterator
     */
    public static boolean list_iterator(ParseTree t) {
        var m = t.enter(LIST_ITERATOR);
        if (m != null) return m;
        boolean r;
        r = expr_or_star(t);
        r = r && iterator(t);
        t.exit(r);
        return r;
    }

    /**
     * dict_iterator (allow_whitespace=true):
     * *   | dict_item iterator
     */
    public static boolean dict_iterator(ParseTree t) {
        var m = t.enter(DICT_ITERATOR);
        if (m != null) return m;
        boolean r;
        r = dict_item(t);
        r = r && iterator(t);
        t.exit(r);
        return r;
    }

    /**
     * assignment:
     * *   | pubassign
     * *   | annassign
     * *   | augassign
     * *   | simple_assign
     */
    public static boolean assignment(ParseTree t) {
        var m = t.enter(ASSIGNMENT);
        if (m != null) return m;
        boolean r;
        r = pubassign(t);
        r = r || annassign(t);
        r = r || augassign(t);
        r = r || simple_assign(t);
        t.exit(r);
        return r;
    }

    /**
     * pubassign:
     * *   | '/' NAME '=' exprlist
     */
    public static boolean pubassign(ParseTree t) {
        var m = t.enter(PUBASSIGN);
        if (m != null) return m;
        boolean r;
        r = t.consume("/");
        r = r && t.consume(TokenType.NAME);
        r = r && t.consume("=");
        r = r && exprlist(t);
        t.exit(r);
        return r;
    }

    /**
     * annassign:
     * *   | target ':' expr ['=' exprlist]
     */
    public static boolean annassign(ParseTree t) {
        var m = t.enter(ANNASSIGN);
        if (m != null) return m;
        boolean r;
        r = target(t);
        r = r && t.consume(":");
        r = r && expr(t);
        if (r) annassign_4(t);
        t.exit(r);
        return r;
    }

    /**
     * '=' exprlist
     */
    private static boolean annassign_4(ParseTree t) {
        var m = t.enter(ANNASSIGN_4);
        if (m != null) return m;
        boolean r;
        r = t.consume("=");
        r = r && exprlist(t);
        t.exit(r);
        return r;
    }

    /**
     * augassign:
     * *   | target augassign_op exprlist
     */
    public static boolean augassign(ParseTree t) {
        var m = t.enter(AUGASSIGN);
        if (m != null) return m;
        boolean r;
        r = target(t);
        r = r && augassign_op(t);
        r = r && exprlist(t);
        t.exit(r);
        return r;
    }

    /**
     * simple_assign:
     * *   | (targetlist '=')* exprlist_star
     */
    public static boolean simple_assign(ParseTree t) {
        var m = t.enter(SIMPLE_ASSIGN);
        if (m != null) return m;
        boolean r;
        simple_assign_1_loop(t);
        r = exprlist_star(t);
        t.exit(r);
        return r;
    }

    private static void simple_assign_1_loop(ParseTree t) {
        t.enterLoop();
        while (true) {
            if (!simple_assign_1(t)) break;
        }
        t.exitLoop();
    }

    /**
     * targetlist '='
     */
    private static boolean simple_assign_1(ParseTree t) {
        var m = t.enter(SIMPLE_ASSIGN_1);
        if (m != null) return m;
        boolean r;
        r = targetlist(t);
        r = r && t.consume("=");
        t.exit(r);
        return r;
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
    public static boolean augassign_op(ParseTree t) {
        var m = t.enter(AUGASSIGN_OP);
        if (m != null) return m;
        boolean r;
        r = t.consume("+=");
        r = r || t.consume("-=");
        r = r || t.consume("*=");
        r = r || t.consume("@=");
        r = r || t.consume("/=");
        r = r || t.consume("%=");
        r = r || t.consume("&=");
        r = r || t.consume("|=");
        r = r || t.consume("^=");
        r = r || t.consume("<<=");
        r = r || t.consume(">>=");
        r = r || t.consume("**=");
        r = r || t.consume("//=");
        t.exit(r);
        return r;
    }

    /**
     * import_name:
     * *   | 'import' dotted_as_names
     */
    public static boolean import_name(ParseTree t) {
        var m = t.enter(IMPORT_NAME);
        if (m != null) return m;
        boolean r;
        r = t.consume("import");
        r = r && dotted_as_names(t);
        t.exit(r);
        return r;
    }

    /**
     * import_from:
     * *   | 'from' import_from_names 'import' import_from_items
     */
    public static boolean import_from(ParseTree t) {
        var m = t.enter(IMPORT_FROM);
        if (m != null) return m;
        boolean r;
        r = t.consume("from");
        r = r && import_from_names(t);
        r = r && t.consume("import");
        r = r && import_from_items(t);
        t.exit(r);
        return r;
    }

    /**
     * import_from_names:
     * *   | dotted_name
     * *   | '.'+ [dotted_name]
     */
    public static boolean import_from_names(ParseTree t) {
        var m = t.enter(IMPORT_FROM_NAMES);
        if (m != null) return m;
        boolean r;
        r = dotted_name(t);
        r = r || import_from_names_2(t);
        t.exit(r);
        return r;
    }

    /**
     * '.'+ [dotted_name]
     */
    private static boolean import_from_names_2(ParseTree t) {
        var m = t.enter(IMPORT_FROM_NAMES_2);
        if (m != null) return m;
        boolean r;
        r = import_from_names_2_loop(t);
        if (r) dotted_name(t);
        t.exit(r);
        return r;
    }

    private static boolean import_from_names_2_loop(ParseTree t) {
        t.enterLoop();
        var r = t.consume(".");
        if (r) while (true) {
            if (!t.consume(".")) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * import_from_items:
     * *   | '*'
     * *   | import_as_names_sp
     * *   | import_as_names
     */
    public static boolean import_from_items(ParseTree t) {
        var m = t.enter(IMPORT_FROM_ITEMS);
        if (m != null) return m;
        boolean r;
        r = t.consume("*");
        r = r || import_as_names_sp(t);
        r = r || import_as_names(t);
        t.exit(r);
        return r;
    }

    /**
     * import_as_names_sp (allow_whitespace=true):
     * *   | '(' import_as_names [','] ')'
     */
    public static boolean import_as_names_sp(ParseTree t) {
        var m = t.enter(IMPORT_AS_NAMES_SP);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        r = r && import_as_names(t);
        if (r) t.consume(",");
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * import_as_name:
     * *   | NAME [as_name]
     */
    public static boolean import_as_name(ParseTree t) {
        var m = t.enter(IMPORT_AS_NAME);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) as_name(t);
        t.exit(r);
        return r;
    }

    /**
     * dotted_as_name:
     * *   | dotted_name [as_name]
     */
    public static boolean dotted_as_name(ParseTree t) {
        var m = t.enter(DOTTED_AS_NAME);
        if (m != null) return m;
        boolean r;
        r = dotted_name(t);
        if (r) as_name(t);
        t.exit(r);
        return r;
    }

    /**
     * import_as_names:
     * *   | ','.import_as_name+
     */
    public static boolean import_as_names(ParseTree t) {
        var m = t.enter(IMPORT_AS_NAMES);
        if (m != null) return m;
        boolean r;
        r = import_as_name_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean import_as_name_loop(ParseTree t) {
        t.enterLoop();
        var r = import_as_name(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && import_as_name(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * dotted_as_names:
     * *   | ','.dotted_as_name+
     */
    public static boolean dotted_as_names(ParseTree t) {
        var m = t.enter(DOTTED_AS_NAMES);
        if (m != null) return m;
        boolean r;
        r = dotted_as_name_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean dotted_as_name_loop(ParseTree t) {
        t.enterLoop();
        var r = dotted_as_name(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && dotted_as_name(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * dotted_name:
     * *   | '.'.NAME+
     */
    public static boolean dotted_name(ParseTree t) {
        var m = t.enter(DOTTED_NAME);
        if (m != null) return m;
        boolean r;
        r = dotted_name_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean dotted_name_loop(ParseTree t) {
        t.enterLoop();
        var r = t.consume(TokenType.NAME);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(".") && t.consume(TokenType.NAME)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * compound_stmt:
     * *   | if_stmt
     * *   | while_stmt
     * *   | for_stmt
     * *   | try_stmt
     * *   | with_stmt
     */
    public static boolean compound_stmt(ParseTree t) {
        var m = t.enter(COMPOUND_STMT);
        if (m != null) return m;
        boolean r;
        r = if_stmt(t);
        r = r || while_stmt(t);
        r = r || for_stmt(t);
        r = r || try_stmt(t);
        r = r || with_stmt(t);
        t.exit(r);
        return r;
    }

    /**
     * if_stmt:
     * *   | 'if' named_expr suite elif_stmt* [else_suite]
     */
    public static boolean if_stmt(ParseTree t) {
        var m = t.enter(IF_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("if");
        r = r && named_expr(t);
        r = r && suite(t);
        if (r) elif_stmt_loop(t);
        if (r) else_suite(t);
        t.exit(r);
        return r;
    }

    private static void elif_stmt_loop(ParseTree t) {
        t.enterLoop();
        while (true) {
            if (!elif_stmt(t)) break;
        }
        t.exitLoop();
    }

    /**
     * elif_stmt:
     * *   | 'elif' named_expr suite
     */
    public static boolean elif_stmt(ParseTree t) {
        var m = t.enter(ELIF_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("elif");
        r = r && named_expr(t);
        r = r && suite(t);
        t.exit(r);
        return r;
    }

    /**
     * while_stmt:
     * *   | 'while' named_expr suite [else_suite]
     */
    public static boolean while_stmt(ParseTree t) {
        var m = t.enter(WHILE_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("while");
        r = r && named_expr(t);
        r = r && suite(t);
        if (r) else_suite(t);
        t.exit(r);
        return r;
    }

    /**
     * for_stmt:
     * *   | 'for' targetlist 'in' exprlist suite [else_suite]
     */
    public static boolean for_stmt(ParseTree t) {
        var m = t.enter(FOR_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("for");
        r = r && targetlist(t);
        r = r && t.consume("in");
        r = r && exprlist(t);
        r = r && suite(t);
        if (r) else_suite(t);
        t.exit(r);
        return r;
    }

    /**
     * try_stmt:
     * *   | 'try' suite (except_suite | finally_suite)
     */
    public static boolean try_stmt(ParseTree t) {
        var m = t.enter(TRY_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("try");
        r = r && suite(t);
        r = r && try_stmt_3(t);
        t.exit(r);
        return r;
    }

    /**
     * except_suite | finally_suite
     */
    private static boolean try_stmt_3(ParseTree t) {
        var m = t.enter(TRY_STMT_3);
        if (m != null) return m;
        boolean r;
        r = except_suite(t);
        r = r || finally_suite(t);
        t.exit(r);
        return r;
    }

    /**
     * with_stmt:
     * *   | 'with' ','.expr_as_name+ suite
     */
    public static boolean with_stmt(ParseTree t) {
        var m = t.enter(WITH_STMT);
        if (m != null) return m;
        boolean r;
        r = t.consume("with");
        r = r && expr_as_name_loop(t);
        r = r && suite(t);
        t.exit(r);
        return r;
    }

    private static boolean expr_as_name_loop(ParseTree t) {
        t.enterLoop();
        var r = expr_as_name(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && expr_as_name(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * expr_as_name:
     * *   | expr [as_name]
     */
    public static boolean expr_as_name(ParseTree t) {
        var m = t.enter(EXPR_AS_NAME);
        if (m != null) return m;
        boolean r;
        r = expr(t);
        if (r) as_name(t);
        t.exit(r);
        return r;
    }

    /**
     * block_suite (allow_whitespace=false):
     * *   | '{' NEWLINE stmt_list '}'
     * *   | '{' [simple_stmt] '}'
     */
    public static boolean block_suite(ParseTree t) {
        var m = t.enter(BLOCK_SUITE);
        if (m != null) return m;
        boolean r;
        r = block_suite_1(t);
        r = r || block_suite_2(t);
        t.exit(r);
        return r;
    }

    /**
     * '{' NEWLINE stmt_list '}'
     */
    private static boolean block_suite_1(ParseTree t) {
        var m = t.enter(BLOCK_SUITE_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("{");
        r = r && t.consume(TokenType.NEWLINE);
        r = r && stmt_list(t);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    /**
     * '{' [simple_stmt] '}'
     */
    private static boolean block_suite_2(ParseTree t) {
        var m = t.enter(BLOCK_SUITE_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("{");
        if (r) simple_stmt(t);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    /**
     * suite:
     * *   | ':' simple_stmt
     * *   | block_suite
     */
    public static boolean suite(ParseTree t) {
        var m = t.enter(SUITE);
        if (m != null) return m;
        boolean r;
        r = suite_1(t);
        r = r || block_suite(t);
        t.exit(r);
        return r;
    }

    /**
     * ':' simple_stmt
     */
    private static boolean suite_1(ParseTree t) {
        var m = t.enter(SUITE_1);
        if (m != null) return m;
        boolean r;
        r = t.consume(":");
        r = r && simple_stmt(t);
        t.exit(r);
        return r;
    }

    /**
     * else_suite:
     * *   | 'else' suite
     */
    public static boolean else_suite(ParseTree t) {
        var m = t.enter(ELSE_SUITE);
        if (m != null) return m;
        boolean r;
        r = t.consume("else");
        r = r && suite(t);
        t.exit(r);
        return r;
    }

    /**
     * finally_suite:
     * *   | 'finally' suite
     */
    public static boolean finally_suite(ParseTree t) {
        var m = t.enter(FINALLY_SUITE);
        if (m != null) return m;
        boolean r;
        r = t.consume("finally");
        r = r && suite(t);
        t.exit(r);
        return r;
    }

    /**
     * except_clause:
     * *   | 'except' [expr_as_name] suite
     */
    public static boolean except_clause(ParseTree t) {
        var m = t.enter(EXCEPT_CLAUSE);
        if (m != null) return m;
        boolean r;
        r = t.consume("except");
        if (r) expr_as_name(t);
        r = r && suite(t);
        t.exit(r);
        return r;
    }

    /**
     * except_suite:
     * *   | except_clause+ [else_suite] [finally_suite]
     */
    public static boolean except_suite(ParseTree t) {
        var m = t.enter(EXCEPT_SUITE);
        if (m != null) return m;
        boolean r;
        r = except_clause_loop(t);
        if (r) else_suite(t);
        if (r) finally_suite(t);
        t.exit(r);
        return r;
    }

    private static boolean except_clause_loop(ParseTree t) {
        t.enterLoop();
        var r = except_clause(t);
        if (r) while (true) {
            if (!except_clause(t)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * invocation:
     * *   | '(' [call_arg_list] ')'
     */
    public static boolean invocation(ParseTree t) {
        var m = t.enter(INVOCATION);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        if (r) call_arg_list(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * call_arg_list (allow_whitespace=true):
     * *   | ','.call_arg+ [',']
     */
    public static boolean call_arg_list(ParseTree t) {
        var m = t.enter(CALL_ARG_LIST);
        if (m != null) return m;
        boolean r;
        r = call_arg_loop(t);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static boolean call_arg_loop(ParseTree t) {
        t.enterLoop();
        var r = call_arg(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && call_arg(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * call_arg:
     * *   | NAME ':=' expr
     * *   | NAME '=' expr
     * *   | '**' expr
     * *   | '*' expr
     * *   | expr
     */
    public static boolean call_arg(ParseTree t) {
        var m = t.enter(CALL_ARG);
        if (m != null) return m;
        boolean r;
        r = call_arg_1(t);
        r = r || call_arg_2(t);
        r = r || call_arg_3(t);
        r = r || call_arg_4(t);
        r = r || expr(t);
        t.exit(r);
        return r;
    }

    /**
     * NAME ':=' expr
     */
    private static boolean call_arg_1(ParseTree t) {
        var m = t.enter(CALL_ARG_1);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume(":=");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * NAME '=' expr
     */
    private static boolean call_arg_2(ParseTree t) {
        var m = t.enter(CALL_ARG_2);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume("=");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * '**' expr
     */
    private static boolean call_arg_3(ParseTree t) {
        var m = t.enter(CALL_ARG_3);
        if (m != null) return m;
        boolean r;
        r = t.consume("**");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * '*' expr
     */
    private static boolean call_arg_4(ParseTree t) {
        var m = t.enter(CALL_ARG_4);
        if (m != null) return m;
        boolean r;
        r = t.consume("*");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * typed_arg_list (allow_whitespace=true):
     * *   | kwargs
     * *   | args_kwargs
     * *   | full_arg_list
     */
    public static boolean typed_arg_list(ParseTree t) {
        var m = t.enter(TYPED_ARG_LIST);
        if (m != null) return m;
        boolean r;
        r = kwargs(t);
        r = r || args_kwargs(t);
        r = r || full_arg_list(t);
        t.exit(r);
        return r;
    }

    /**
     * full_arg_list:
     * *   | ','.default_arg+ [',' [kwargs | args_kwargs]]
     */
    public static boolean full_arg_list(ParseTree t) {
        var m = t.enter(FULL_ARG_LIST);
        if (m != null) return m;
        boolean r;
        r = default_arg_loop(t);
        if (r) full_arg_list_2(t);
        t.exit(r);
        return r;
    }

    private static boolean default_arg_loop(ParseTree t) {
        t.enterLoop();
        var r = default_arg(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && default_arg(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * ',' [kwargs | args_kwargs]
     */
    private static boolean full_arg_list_2(ParseTree t) {
        var m = t.enter(FULL_ARG_LIST_2);
        if (m != null) return m;
        boolean r;
        r = t.consume(",");
        if (r) full_arg_list_2_2(t);
        t.exit(r);
        return r;
    }

    /**
     * kwargs | args_kwargs
     */
    private static boolean full_arg_list_2_2(ParseTree t) {
        var m = t.enter(FULL_ARG_LIST_2_2);
        if (m != null) return m;
        boolean r;
        r = kwargs(t);
        r = r || args_kwargs(t);
        t.exit(r);
        return r;
    }

    /**
     * args_kwargs:
     * *   | '*' [typed_arg] (',' default_arg)* [',' [kwargs]]
     */
    public static boolean args_kwargs(ParseTree t) {
        var m = t.enter(ARGS_KWARGS);
        if (m != null) return m;
        boolean r;
        r = t.consume("*");
        if (r) typed_arg(t);
        if (r) args_kwargs_3_loop(t);
        if (r) args_kwargs_4(t);
        t.exit(r);
        return r;
    }

    private static void args_kwargs_3_loop(ParseTree t) {
        t.enterLoop();
        while (true) {
            if (!args_kwargs_3(t)) break;
        }
        t.exitLoop();
    }

    /**
     * ',' default_arg
     */
    private static boolean args_kwargs_3(ParseTree t) {
        var m = t.enter(ARGS_KWARGS_3);
        if (m != null) return m;
        boolean r;
        r = t.consume(",");
        r = r && default_arg(t);
        t.exit(r);
        return r;
    }

    /**
     * ',' [kwargs]
     */
    private static boolean args_kwargs_4(ParseTree t) {
        var m = t.enter(ARGS_KWARGS_4);
        if (m != null) return m;
        boolean r;
        r = t.consume(",");
        if (r) kwargs(t);
        t.exit(r);
        return r;
    }

    /**
     * kwargs:
     * *   | '**' typed_arg [',']
     */
    public static boolean kwargs(ParseTree t) {
        var m = t.enter(KWARGS);
        if (m != null) return m;
        boolean r;
        r = t.consume("**");
        r = r && typed_arg(t);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    /**
     * default_arg:
     * *   | typed_arg ['=' expr]
     */
    public static boolean default_arg(ParseTree t) {
        var m = t.enter(DEFAULT_ARG);
        if (m != null) return m;
        boolean r;
        r = typed_arg(t);
        if (r) default_arg_2(t);
        t.exit(r);
        return r;
    }

    /**
     * '=' expr
     */
    private static boolean default_arg_2(ParseTree t) {
        var m = t.enter(DEFAULT_ARG_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("=");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * typed_arg:
     * *   | NAME [':' expr]
     */
    public static boolean typed_arg(ParseTree t) {
        var m = t.enter(TYPED_ARG);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) typed_arg_2(t);
        t.exit(r);
        return r;
    }

    /**
     * ':' expr
     */
    private static boolean typed_arg_2(ParseTree t) {
        var m = t.enter(TYPED_ARG_2);
        if (m != null) return m;
        boolean r;
        r = t.consume(":");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * simple_arg:
     * *   | NAME ['=' expr]
     */
    public static boolean simple_arg(ParseTree t) {
        var m = t.enter(SIMPLE_ARG);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) simple_arg_2(t);
        t.exit(r);
        return r;
    }

    /**
     * '=' expr
     */
    private static boolean simple_arg_2(ParseTree t) {
        var m = t.enter(SIMPLE_ARG_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("=");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * simple_args:
     * *   | ','.simple_arg+
     */
    public static boolean simple_args(ParseTree t) {
        var m = t.enter(SIMPLE_ARGS);
        if (m != null) return m;
        boolean r;
        r = simple_arg_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean simple_arg_loop(ParseTree t) {
        t.enterLoop();
        var r = simple_arg(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && simple_arg(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * builder_hint:
     * *   | '<' name_list '>'
     */
    public static boolean builder_hint(ParseTree t) {
        var m = t.enter(BUILDER_HINT);
        if (m != null) return m;
        boolean r;
        r = t.consume("<");
        r = r && name_list(t);
        r = r && t.consume(">");
        t.exit(r);
        return r;
    }

    /**
     * builder_args:
     * *   | simple_args
     * *   | '(' [typed_arg_list] ')'
     */
    public static boolean builder_args(ParseTree t) {
        var m = t.enter(BUILDER_ARGS);
        if (m != null) return m;
        boolean r;
        r = simple_args(t);
        r = r || builder_args_2(t);
        t.exit(r);
        return r;
    }

    /**
     * '(' [typed_arg_list] ')'
     */
    private static boolean builder_args_2(ParseTree t) {
        var m = t.enter(BUILDER_ARGS_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        if (r) typed_arg_list(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * named_expr:
     * *   | NAME ':=' expr
     * *   | expr
     */
    public static boolean named_expr(ParseTree t) {
        var m = t.enter(NAMED_EXPR);
        if (m != null) return m;
        boolean r;
        r = named_expr_1(t);
        r = r || expr(t);
        t.exit(r);
        return r;
    }

    /**
     * NAME ':=' expr
     */
    private static boolean named_expr_1(ParseTree t) {
        var m = t.enter(NAMED_EXPR_1);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume(":=");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * conditional:
     * *   | 'if' disjunction '?' disjunction ':' expr
     */
    public static boolean conditional(ParseTree t) {
        var m = t.enter(CONDITIONAL);
        if (m != null) return m;
        boolean r;
        r = t.consume("if");
        r = r && disjunction(t);
        r = r && t.consume("?");
        r = r && disjunction(t);
        r = r && t.consume(":");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * expr:
     * *   | conditional
     * *   | disjunction
     */
    public static boolean expr(ParseTree t) {
        var m = t.enter(EXPR);
        if (m != null) return m;
        boolean r;
        r = conditional(t);
        r = r || disjunction(t);
        t.exit(r);
        return r;
    }

    /**
     * disjunction (left_recursive):
     * *   | disjunction 'or' conjunction
     * *   | conjunction
     */
    public static boolean disjunction(ParseTree t) {
        var m = t.enter(DISJUNCTION);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = disjunction_1(t);
            r = r || conjunction(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * disjunction 'or' conjunction
     */
    private static boolean disjunction_1(ParseTree t) {
        var m = t.enter(DISJUNCTION_1);
        if (m != null) return m;
        boolean r;
        r = disjunction(t);
        r = r && t.consume("or");
        r = r && conjunction(t);
        t.exit(r);
        return r;
    }

    /**
     * conjunction (left_recursive):
     * *   | conjunction 'and' inversion
     * *   | inversion
     */
    public static boolean conjunction(ParseTree t) {
        var m = t.enter(CONJUNCTION);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = conjunction_1(t);
            r = r || inversion(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * conjunction 'and' inversion
     */
    private static boolean conjunction_1(ParseTree t) {
        var m = t.enter(CONJUNCTION_1);
        if (m != null) return m;
        boolean r;
        r = conjunction(t);
        r = r && t.consume("and");
        r = r && inversion(t);
        t.exit(r);
        return r;
    }

    /**
     * inversion:
     * *   | 'not' inversion
     * *   | comparison
     */
    public static boolean inversion(ParseTree t) {
        var m = t.enter(INVERSION);
        if (m != null) return m;
        boolean r;
        r = inversion_1(t);
        r = r || comparison(t);
        t.exit(r);
        return r;
    }

    /**
     * 'not' inversion
     */
    private static boolean inversion_1(ParseTree t) {
        var m = t.enter(INVERSION_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("not");
        r = r && inversion(t);
        t.exit(r);
        return r;
    }

    /**
     * comparison:
     * *   | bitwise_or (comp_op bitwise_or)+
     * *   | bitwise_or
     */
    public static boolean comparison(ParseTree t) {
        var m = t.enter(COMPARISON);
        if (m != null) return m;
        boolean r;
        r = comparison_1(t);
        r = r || bitwise_or(t);
        t.exit(r);
        return r;
    }

    /**
     * bitwise_or (comp_op bitwise_or)+
     */
    private static boolean comparison_1(ParseTree t) {
        var m = t.enter(COMPARISON_1);
        if (m != null) return m;
        boolean r;
        r = bitwise_or(t);
        r = r && comparison_1_2_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean comparison_1_2_loop(ParseTree t) {
        t.enterLoop();
        var r = comparison_1_2(t);
        if (r) while (true) {
            if (!comparison_1_2(t)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * comp_op bitwise_or
     */
    private static boolean comparison_1_2(ParseTree t) {
        var m = t.enter(COMPARISON_1_2);
        if (m != null) return m;
        boolean r;
        r = comp_op(t);
        r = r && bitwise_or(t);
        t.exit(r);
        return r;
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
    public static boolean comp_op(ParseTree t) {
        var m = t.enter(COMP_OP);
        if (m != null) return m;
        boolean r;
        r = t.consume("<");
        r = r || t.consume(">");
        r = r || t.consume("==");
        r = r || t.consume(">=");
        r = r || t.consume("<=");
        r = r || t.consume("!=");
        r = r || t.consume("in");
        r = r || comp_op_8(t);
        r = r || t.consume("is");
        r = r || comp_op_10(t);
        t.exit(r);
        return r;
    }

    /**
     * 'not' 'in'
     */
    private static boolean comp_op_8(ParseTree t) {
        var m = t.enter(COMP_OP_8);
        if (m != null) return m;
        boolean r;
        r = t.consume("not");
        r = r && t.consume("in");
        t.exit(r);
        return r;
    }

    /**
     * 'is' 'not'
     */
    private static boolean comp_op_10(ParseTree t) {
        var m = t.enter(COMP_OP_10);
        if (m != null) return m;
        boolean r;
        r = t.consume("is");
        r = r && t.consume("not");
        t.exit(r);
        return r;
    }

    /**
     * bitwise_or (left_recursive):
     * *   | bitwise_or '|' bitwise_xor
     * *   | bitwise_xor
     */
    public static boolean bitwise_or(ParseTree t) {
        var m = t.enter(BITWISE_OR);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = bitwise_or_1(t);
            r = r || bitwise_xor(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * bitwise_or '|' bitwise_xor
     */
    private static boolean bitwise_or_1(ParseTree t) {
        var m = t.enter(BITWISE_OR_1);
        if (m != null) return m;
        boolean r;
        r = bitwise_or(t);
        r = r && t.consume("|");
        r = r && bitwise_xor(t);
        t.exit(r);
        return r;
    }

    /**
     * bitwise_xor (left_recursive):
     * *   | bitwise_xor '^' bitwise_and
     * *   | bitwise_and
     */
    public static boolean bitwise_xor(ParseTree t) {
        var m = t.enter(BITWISE_XOR);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = bitwise_xor_1(t);
            r = r || bitwise_and(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * bitwise_xor '^' bitwise_and
     */
    private static boolean bitwise_xor_1(ParseTree t) {
        var m = t.enter(BITWISE_XOR_1);
        if (m != null) return m;
        boolean r;
        r = bitwise_xor(t);
        r = r && t.consume("^");
        r = r && bitwise_and(t);
        t.exit(r);
        return r;
    }

    /**
     * bitwise_and (left_recursive):
     * *   | bitwise_and '&' shift_expr
     * *   | shift_expr
     */
    public static boolean bitwise_and(ParseTree t) {
        var m = t.enter(BITWISE_AND);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = bitwise_and_1(t);
            r = r || shift_expr(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * bitwise_and '&' shift_expr
     */
    private static boolean bitwise_and_1(ParseTree t) {
        var m = t.enter(BITWISE_AND_1);
        if (m != null) return m;
        boolean r;
        r = bitwise_and(t);
        r = r && t.consume("&");
        r = r && shift_expr(t);
        t.exit(r);
        return r;
    }

    /**
     * shift_expr (left_recursive):
     * *   | shift_expr '<<' sum
     * *   | shift_expr '>>' sum
     * *   | sum
     */
    public static boolean shift_expr(ParseTree t) {
        var m = t.enter(SHIFT_EXPR);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = shift_expr_1(t);
            r = r || shift_expr_2(t);
            r = r || sum(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * shift_expr '<<' sum
     */
    private static boolean shift_expr_1(ParseTree t) {
        var m = t.enter(SHIFT_EXPR_1);
        if (m != null) return m;
        boolean r;
        r = shift_expr(t);
        r = r && t.consume("<<");
        r = r && sum(t);
        t.exit(r);
        return r;
    }

    /**
     * shift_expr '>>' sum
     */
    private static boolean shift_expr_2(ParseTree t) {
        var m = t.enter(SHIFT_EXPR_2);
        if (m != null) return m;
        boolean r;
        r = shift_expr(t);
        r = r && t.consume(">>");
        r = r && sum(t);
        t.exit(r);
        return r;
    }

    /**
     * sum (left_recursive):
     * *   | sum '+' term
     * *   | sum '-' term
     * *   | term
     */
    public static boolean sum(ParseTree t) {
        var m = t.enter(SUM);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = sum_1(t);
            r = r || sum_2(t);
            r = r || term(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * sum '+' term
     */
    private static boolean sum_1(ParseTree t) {
        var m = t.enter(SUM_1);
        if (m != null) return m;
        boolean r;
        r = sum(t);
        r = r && t.consume("+");
        r = r && term(t);
        t.exit(r);
        return r;
    }

    /**
     * sum '-' term
     */
    private static boolean sum_2(ParseTree t) {
        var m = t.enter(SUM_2);
        if (m != null) return m;
        boolean r;
        r = sum(t);
        r = r && t.consume("-");
        r = r && term(t);
        t.exit(r);
        return r;
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
    public static boolean term(ParseTree t) {
        var m = t.enter(TERM);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = term_1(t);
            r = r || term_2(t);
            r = r || term_3(t);
            r = r || term_4(t);
            r = r || term_5(t);
            r = r || pipe_expr(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * term '*' pipe_expr
     */
    private static boolean term_1(ParseTree t) {
        var m = t.enter(TERM_1);
        if (m != null) return m;
        boolean r;
        r = term(t);
        r = r && t.consume("*");
        r = r && pipe_expr(t);
        t.exit(r);
        return r;
    }

    /**
     * term '/' pipe_expr
     */
    private static boolean term_2(ParseTree t) {
        var m = t.enter(TERM_2);
        if (m != null) return m;
        boolean r;
        r = term(t);
        r = r && t.consume("/");
        r = r && pipe_expr(t);
        t.exit(r);
        return r;
    }

    /**
     * term '%' pipe_expr
     */
    private static boolean term_3(ParseTree t) {
        var m = t.enter(TERM_3);
        if (m != null) return m;
        boolean r;
        r = term(t);
        r = r && t.consume("%");
        r = r && pipe_expr(t);
        t.exit(r);
        return r;
    }

    /**
     * term '//' pipe_expr
     */
    private static boolean term_4(ParseTree t) {
        var m = t.enter(TERM_4);
        if (m != null) return m;
        boolean r;
        r = term(t);
        r = r && t.consume("//");
        r = r && pipe_expr(t);
        t.exit(r);
        return r;
    }

    /**
     * term '@' pipe_expr
     */
    private static boolean term_5(ParseTree t) {
        var m = t.enter(TERM_5);
        if (m != null) return m;
        boolean r;
        r = term(t);
        r = r && t.consume("@");
        r = r && pipe_expr(t);
        t.exit(r);
        return r;
    }

    /**
     * pipe_expr (left_recursive):
     * *   | pipe_expr '->' factor
     * *   | factor
     */
    public static boolean pipe_expr(ParseTree t) {
        var m = t.enter(PIPE_EXPR);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = pipe_expr_1(t);
            r = r || factor(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * pipe_expr '->' factor
     */
    private static boolean pipe_expr_1(ParseTree t) {
        var m = t.enter(PIPE_EXPR_1);
        if (m != null) return m;
        boolean r;
        r = pipe_expr(t);
        r = r && t.consume("->");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * factor:
     * *   | '+' factor
     * *   | '-' factor
     * *   | '~' factor
     * *   | power
     */
    public static boolean factor(ParseTree t) {
        var m = t.enter(FACTOR);
        if (m != null) return m;
        boolean r;
        r = factor_1(t);
        r = r || factor_2(t);
        r = r || factor_3(t);
        r = r || power(t);
        t.exit(r);
        return r;
    }

    /**
     * '+' factor
     */
    private static boolean factor_1(ParseTree t) {
        var m = t.enter(FACTOR_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("+");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * '-' factor
     */
    private static boolean factor_2(ParseTree t) {
        var m = t.enter(FACTOR_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("-");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * '~' factor
     */
    private static boolean factor_3(ParseTree t) {
        var m = t.enter(FACTOR_3);
        if (m != null) return m;
        boolean r;
        r = t.consume("~");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * power:
     * *   | primary '**' factor
     * *   | primary
     */
    public static boolean power(ParseTree t) {
        var m = t.enter(POWER);
        if (m != null) return m;
        boolean r;
        r = power_1(t);
        r = r || primary(t);
        t.exit(r);
        return r;
    }

    /**
     * primary '**' factor
     */
    private static boolean power_1(ParseTree t) {
        var m = t.enter(POWER_1);
        if (m != null) return m;
        boolean r;
        r = primary(t);
        r = r && t.consume("**");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * primary (left_recursive):
     * *   | primary '.' NAME
     * *   | primary invocation
     * *   | primary subscript
     * *   | atom
     */
    public static boolean primary(ParseTree t) {
        var m = t.enter(PRIMARY);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = primary_1(t);
            r = r || primary_2(t);
            r = r || primary_3(t);
            r = r || atom(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * primary '.' NAME
     */
    private static boolean primary_1(ParseTree t) {
        var m = t.enter(PRIMARY_1);
        if (m != null) return m;
        boolean r;
        r = primary(t);
        r = r && t.consume(".");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * primary invocation
     */
    private static boolean primary_2(ParseTree t) {
        var m = t.enter(PRIMARY_2);
        if (m != null) return m;
        boolean r;
        r = primary(t);
        r = r && invocation(t);
        t.exit(r);
        return r;
    }

    /**
     * primary subscript
     */
    private static boolean primary_3(ParseTree t) {
        var m = t.enter(PRIMARY_3);
        if (m != null) return m;
        boolean r;
        r = primary(t);
        r = r && subscript(t);
        t.exit(r);
        return r;
    }

    /**
     * tuple_atom:
     * *   | '(' [list_items] ')'
     */
    public static boolean tuple_atom(ParseTree t) {
        var m = t.enter(TUPLE_ATOM);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        if (r) list_items(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * list_iterable:
     * *   | '[' list_iterator ']'
     */
    public static boolean list_iterable(ParseTree t) {
        var m = t.enter(LIST_ITERABLE);
        if (m != null) return m;
        boolean r;
        r = t.consume("[");
        r = r && list_iterator(t);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }

    /**
     * list_atom:
     * *   | '[' [list_items] ']'
     */
    public static boolean list_atom(ParseTree t) {
        var m = t.enter(LIST_ATOM);
        if (m != null) return m;
        boolean r;
        r = t.consume("[");
        if (r) list_items(t);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }

    /**
     * set_atom:
     * *   | '{' [set_items] '}'
     */
    public static boolean set_atom(ParseTree t) {
        var m = t.enter(SET_ATOM);
        if (m != null) return m;
        boolean r;
        r = t.consume("{");
        if (r) set_items(t);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    /**
     * dict_iterable:
     * *   | '{' dict_iterator '}'
     */
    public static boolean dict_iterable(ParseTree t) {
        var m = t.enter(DICT_ITERABLE);
        if (m != null) return m;
        boolean r;
        r = t.consume("{");
        r = r && dict_iterator(t);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    /**
     * dict_atom:
     * *   | '{' [dict_items] '}'
     */
    public static boolean dict_atom(ParseTree t) {
        var m = t.enter(DICT_ATOM);
        if (m != null) return m;
        boolean r;
        r = t.consume("{");
        if (r) dict_items(t);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    /**
     * builder:
     * *   | NAME simple_args ':' expr
     * *   | NAME [builder_hint] [builder_args] block_suite
     */
    public static boolean builder(ParseTree t) {
        var m = t.enter(BUILDER);
        if (m != null) return m;
        boolean r;
        r = builder_1(t);
        r = r || builder_2(t);
        t.exit(r);
        return r;
    }

    /**
     * NAME simple_args ':' expr
     */
    private static boolean builder_1(ParseTree t) {
        var m = t.enter(BUILDER_1);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && simple_args(t);
        r = r && t.consume(":");
        r = r && expr(t);
        t.exit(r);
        return r;
    }

    /**
     * NAME [builder_hint] [builder_args] block_suite
     */
    private static boolean builder_2(ParseTree t) {
        var m = t.enter(BUILDER_2);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) builder_hint(t);
        if (r) builder_args(t);
        r = r && block_suite(t);
        t.exit(r);
        return r;
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
    public static boolean atom(ParseTree t) {
        var m = t.enter(ATOM);
        if (m != null) return m;
        boolean r;
        r = tuple_atom(t);
        r = r || list_iterable(t);
        r = r || list_atom(t);
        r = r || set_atom(t);
        r = r || dict_iterable(t);
        r = r || dict_atom(t);
        r = r || builder(t);
        r = r || t.consume(TokenType.NAME);
        r = r || t.consume(TokenType.NUMBER);
        r = r || t.consume(TokenType.STRING);
        r = r || t.consume("None");
        r = r || t.consume("True");
        r = r || t.consume("False");
        t.exit(r);
        return r;
    }
}
