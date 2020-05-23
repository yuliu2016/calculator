package org.fugalang.core.pgen.parser;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.token.TokenType;

import static org.fugalang.core.pgen.parser.FugaRules.*;

@SuppressWarnings("UnusedReturnValue")
public class FugaParser {

    /**
     * single_input: 'NEWLINE' | 'simple_stmt' | 'compound_stmt' 'NEWLINE'
     */
    public static boolean single_input(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SINGLE_INPUT);
        boolean r;
        r = t.consume(TokenType.NEWLINE);
        r = r || simple_stmt(t, lv + 1);
        r = r || single_input_3(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'compound_stmt' 'NEWLINE'
     */
    public static boolean single_input_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SINGLE_INPUT_3);
        boolean r;
        r = compound_stmt(t, lv + 1);
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    /**
     * file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
     */
    public static boolean file_input(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FILE_INPUT);
        boolean r;
        file_input_1_loop(t, lv);
        r = t.consume(TokenType.ENDMARKER);
        t.exit(r);
        return r;
    }

    private static void file_input_1_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!file_input_1(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'NEWLINE' | 'stmt'
     */
    public static boolean file_input_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FILE_INPUT_1);
        boolean r;
        r = t.consume(TokenType.NEWLINE);
        r = r || stmt(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * eval_input: 'exprlist' 'NEWLINE'* 'ENDMARKER'
     */
    public static boolean eval_input(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EVAL_INPUT);
        boolean r;
        r = exprlist(t, lv + 1);
        if (r) eval_input_newline_loop(t, lv);
        r = r && t.consume(TokenType.ENDMARKER);
        t.exit(r);
        return r;
    }

    private static void eval_input_newline_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!t.consume(TokenType.NEWLINE) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * stmt: ('simple_stmt' | 'compound_stmt') 'NEWLINE'
     */
    public static boolean stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, STMT);
        boolean r;
        r = stmt_1(t, lv + 1);
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    /**
     * 'simple_stmt' | 'compound_stmt'
     */
    public static boolean stmt_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, STMT_1);
        boolean r;
        r = simple_stmt(t, lv + 1);
        r = r || compound_stmt(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
     */
    public static boolean simple_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SIMPLE_STMT);
        boolean r;
        r = small_stmt(t, lv + 1);
        if (r) simple_stmt_2_loop(t, lv);
        if (r) t.consume(";");
        t.exit(r);
        return r;
    }

    private static void simple_stmt_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!simple_stmt_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ';' 'small_stmt'
     */
    public static boolean simple_stmt_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SIMPLE_STMT_2);
        boolean r;
        r = t.consume(";");
        r = r && small_stmt(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * small_stmt: 'pass' | 'break' | 'continue' | 'del_stmt' | 'return_stmt' | 'raise_stmt' | 'nonlocal_stmt' | 'assert_stmt' | 'import_name' | 'import_from' | 'assignment'
     */
    public static boolean small_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SMALL_STMT);
        boolean r;
        r = t.consume("pass");
        r = r || t.consume("break");
        r = r || t.consume("continue");
        r = r || del_stmt(t, lv + 1);
        r = r || return_stmt(t, lv + 1);
        r = r || raise_stmt(t, lv + 1);
        r = r || nonlocal_stmt(t, lv + 1);
        r = r || assert_stmt(t, lv + 1);
        r = r || import_name(t, lv + 1);
        r = r || import_from(t, lv + 1);
        r = r || assignment(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * del_stmt: 'del' 'targetlist'
     */
    public static boolean del_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DEL_STMT);
        boolean r;
        r = t.consume("del");
        r = r && targetlist(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * return_stmt: 'return' ['exprlist_star']
     */
    public static boolean return_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RETURN_STMT);
        boolean r;
        r = t.consume("return");
        if (r) exprlist_star(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * raise_stmt: 'raise' ['expr' ['from' 'expr']]
     */
    public static boolean raise_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RAISE_STMT);
        boolean r;
        r = t.consume("raise");
        if (r) raise_stmt_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'expr' ['from' 'expr']
     */
    public static boolean raise_stmt_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RAISE_STMT_2);
        boolean r;
        r = expr(t, lv + 1);
        if (r) raise_stmt_2_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'from' 'expr'
     */
    public static boolean raise_stmt_2_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RAISE_STMT_2_2);
        boolean r;
        r = t.consume("from");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * nonlocal_stmt: 'nonlocal' 'NAME' (',' 'NAME')*
     */
    public static boolean nonlocal_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, NONLOCAL_STMT);
        boolean r;
        r = t.consume("nonlocal");
        r = r && t.consume(TokenType.NAME);
        if (r) nonlocal_stmt_3_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void nonlocal_stmt_3_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!nonlocal_stmt_3(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'NAME'
     */
    public static boolean nonlocal_stmt_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, NONLOCAL_STMT_3);
        boolean r;
        r = t.consume(",");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * assert_stmt: 'assert' 'expr' [',' 'expr']
     */
    public static boolean assert_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ASSERT_STMT);
        boolean r;
        r = t.consume("assert");
        r = r && expr(t, lv + 1);
        if (r) assert_stmt_3(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ',' 'expr'
     */
    public static boolean assert_stmt_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ASSERT_STMT_3);
        boolean r;
        r = t.consume(",");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * expr_or_star: 'star_expr' | 'expr'
     */
    public static boolean expr_or_star(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXPR_OR_STAR);
        boolean r;
        r = star_expr(t, lv + 1);
        r = r || expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * star_expr: '*' 'bitwise_or'
     */
    public static boolean star_expr(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, STAR_EXPR);
        boolean r;
        r = t.consume("*");
        r = r && bitwise_or(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * exprlist_star: 'expr_or_star' (',' 'expr_or_star')* [',']
     */
    public static boolean exprlist_star(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXPRLIST_STAR);
        boolean r;
        r = expr_or_star(t, lv + 1);
        if (r) exprlist_star_2_loop(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void exprlist_star_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!exprlist_star_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'expr_or_star'
     */
    public static boolean exprlist_star_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXPRLIST_STAR_2);
        boolean r;
        r = t.consume(",");
        r = r && expr_or_star(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * assignment: ['/'] 'exprlist_star' ['annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist']
     */
    public static boolean assignment(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ASSIGNMENT);
        boolean r;
        t.consume("/");
        r = exprlist_star(t, lv + 1);
        if (r) assignment_3(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist'
     */
    public static boolean assignment_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ASSIGNMENT_3);
        boolean r;
        r = annassign(t, lv + 1);
        r = r || assignment_3_2_loop(t, lv);
        r = r || assignment_3_3(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static boolean assignment_3_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        var r = assignment_3_2(t, lv + 1);
        if (r) while (true) {
            var p = t.position();
            if (!assignment_3_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
        return r;
    }

    /**
     * '=' 'exprlist_star'
     */
    public static boolean assignment_3_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ASSIGNMENT_3_2);
        boolean r;
        r = t.consume("=");
        r = r && exprlist_star(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'augassign' 'exprlist'
     */
    public static boolean assignment_3_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ASSIGNMENT_3_3);
        boolean r;
        r = augassign(t, lv + 1);
        r = r && exprlist(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * augassign: '+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' | '|=' | '^=' | '<<=' | '>>=' | '**=' | '//='
     */
    public static boolean augassign(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, AUGASSIGN);
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
     * annassign: ':' 'expr' ['=' 'exprlist_star']
     */
    public static boolean annassign(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ANNASSIGN);
        boolean r;
        r = t.consume(":");
        r = r && expr(t, lv + 1);
        if (r) annassign_3(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '=' 'exprlist_star'
     */
    public static boolean annassign_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ANNASSIGN_3);
        boolean r;
        r = t.consume("=");
        r = r && exprlist_star(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * import_name: 'import' 'dotted_as_names'
     */
    public static boolean import_name(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_NAME);
        boolean r;
        r = t.consume("import");
        r = r && dotted_as_names(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * import_from: 'from' 'import_from_names' 'import' ('*' | '(' 'import_as_names' [','] ')' | 'import_as_names')
     */
    public static boolean import_from(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_FROM);
        boolean r;
        r = t.consume("from");
        r = r && import_from_names(t, lv + 1);
        r = r && t.consume("import");
        r = r && import_from_4(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '*' | '(' 'import_as_names' [','] ')' | 'import_as_names'
     */
    public static boolean import_from_4(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_FROM_4);
        boolean r;
        r = t.consume("*");
        r = r || import_from_4_2(t, lv + 1);
        r = r || import_as_names(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '(' 'import_as_names' [','] ')'
     */
    public static boolean import_from_4_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_FROM_4_2);
        boolean r;
        r = t.consume("(");
        r = r && import_as_names(t, lv + 1);
        if (r) t.consume(",");
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * import_from_names: '.'* 'dotted_name' | '.'+
     */
    public static boolean import_from_names(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_FROM_NAMES);
        boolean r;
        r = import_from_names_1(t, lv + 1);
        r = r || import_from_names_dot_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static boolean import_from_names_dot_loop(ParseTree t, int lv) {
        t.enterCollection();
        var r = t.consume(".");
        if (r) while (true) {
            var p = t.position();
            if (!t.consume(".") || t.loopGuard(p)) break;
        }
        t.exitCollection();
        return r;
    }

    /**
     * '.'* 'dotted_name'
     */
    public static boolean import_from_names_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_FROM_NAMES_1);
        boolean r;
        import_from_names_1_dot_loop(t, lv);
        r = dotted_name(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void import_from_names_1_dot_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!t.consume(".") || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * import_as_name: 'NAME' ['as' 'NAME']
     */
    public static boolean import_as_name(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_AS_NAME);
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) import_as_name_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'as' 'NAME'
     */
    public static boolean import_as_name_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_AS_NAME_2);
        boolean r;
        r = t.consume("as");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * dotted_as_name: 'dotted_name' ['as' 'NAME']
     */
    public static boolean dotted_as_name(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DOTTED_AS_NAME);
        boolean r;
        r = dotted_name(t, lv + 1);
        if (r) dotted_as_name_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'as' 'NAME'
     */
    public static boolean dotted_as_name_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DOTTED_AS_NAME_2);
        boolean r;
        r = t.consume("as");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * import_as_names: 'import_as_name' (',' 'import_as_name')*
     */
    public static boolean import_as_names(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_AS_NAMES);
        boolean r;
        r = import_as_name(t, lv + 1);
        if (r) import_as_names_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void import_as_names_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!import_as_names_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'import_as_name'
     */
    public static boolean import_as_names_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IMPORT_AS_NAMES_2);
        boolean r;
        r = t.consume(",");
        r = r && import_as_name(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')*
     */
    public static boolean dotted_as_names(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DOTTED_AS_NAMES);
        boolean r;
        r = dotted_as_name(t, lv + 1);
        if (r) dotted_as_names_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void dotted_as_names_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!dotted_as_names_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'dotted_as_name'
     */
    public static boolean dotted_as_names_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DOTTED_AS_NAMES_2);
        boolean r;
        r = t.consume(",");
        r = r && dotted_as_name(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * dotted_name: 'NAME' ('.' 'NAME')*
     */
    public static boolean dotted_name(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DOTTED_NAME);
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) dotted_name_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void dotted_name_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!dotted_name_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '.' 'NAME'
     */
    public static boolean dotted_name_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DOTTED_NAME_2);
        boolean r;
        r = t.consume(".");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * compound_stmt: 'if_stmt' | 'while_stmt' | 'for_stmt' | 'try_stmt' | 'with_stmt'
     */
    public static boolean compound_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, COMPOUND_STMT);
        boolean r;
        r = if_stmt(t, lv + 1);
        r = r || while_stmt(t, lv + 1);
        r = r || for_stmt(t, lv + 1);
        r = r || try_stmt(t, lv + 1);
        r = r || with_stmt(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * if_stmt: 'if' 'named_expr' 'suite' 'elif_stmt'* ['else_suite']
     */
    public static boolean if_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, IF_STMT);
        boolean r;
        r = t.consume("if");
        r = r && named_expr(t, lv + 1);
        r = r && suite(t, lv + 1);
        if (r) elif_stmt_loop(t, lv);
        if (r) else_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void elif_stmt_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!elif_stmt(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * elif_stmt: 'elif' 'named_expr' 'suite'
     */
    public static boolean elif_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ELIF_STMT);
        boolean r;
        r = t.consume("elif");
        r = r && named_expr(t, lv + 1);
        r = r && suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * while_stmt: 'while' 'named_expr' 'suite' ['else_suite']
     */
    public static boolean while_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, WHILE_STMT);
        boolean r;
        r = t.consume("while");
        r = r && named_expr(t, lv + 1);
        r = r && suite(t, lv + 1);
        if (r) else_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * for_stmt: 'for' 'targetlist' 'in' 'exprlist' 'suite' ['else_suite']
     */
    public static boolean for_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FOR_STMT);
        boolean r;
        r = t.consume("for");
        r = r && targetlist(t, lv + 1);
        r = r && t.consume("in");
        r = r && exprlist(t, lv + 1);
        r = r && suite(t, lv + 1);
        if (r) else_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * try_stmt: 'try' 'suite' ('except_suite' | 'finally_suite')
     */
    public static boolean try_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TRY_STMT);
        boolean r;
        r = t.consume("try");
        r = r && suite(t, lv + 1);
        r = r && try_stmt_3(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'except_suite' | 'finally_suite'
     */
    public static boolean try_stmt_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TRY_STMT_3);
        boolean r;
        r = except_suite(t, lv + 1);
        r = r || finally_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
     */
    public static boolean with_stmt(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, WITH_STMT);
        boolean r;
        r = t.consume("with");
        r = r && with_item(t, lv + 1);
        if (r) with_stmt_3_loop(t, lv);
        r = r && suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void with_stmt_3_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!with_stmt_3(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'with_item'
     */
    public static boolean with_stmt_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, WITH_STMT_3);
        boolean r;
        r = t.consume(",");
        r = r && with_item(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * with_item: 'expr' ['as' 'NAME']
     */
    public static boolean with_item(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, WITH_ITEM);
        boolean r;
        r = expr(t, lv + 1);
        if (r) with_item_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'as' 'NAME'
     */
    public static boolean with_item_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, WITH_ITEM_2);
        boolean r;
        r = t.consume("as");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * except_clause: 'except' ['expr' ['as' 'NAME']]
     */
    public static boolean except_clause(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXCEPT_CLAUSE);
        boolean r;
        r = t.consume("except");
        if (r) except_clause_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'expr' ['as' 'NAME']
     */
    public static boolean except_clause_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXCEPT_CLAUSE_2);
        boolean r;
        r = expr(t, lv + 1);
        if (r) except_clause_2_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'as' 'NAME'
     */
    public static boolean except_clause_2_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXCEPT_CLAUSE_2_2);
        boolean r;
        r = t.consume("as");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * block_suite: '{' 'simple_stmt' '}' | '{' 'NEWLINE' 'stmt'+ '}'
     */
    public static boolean block_suite(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, BLOCK_SUITE);
        boolean r;
        r = block_suite_1(t, lv + 1);
        r = r || block_suite_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '{' 'simple_stmt' '}'
     */
    public static boolean block_suite_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, BLOCK_SUITE_1);
        boolean r;
        r = t.consume("{");
        r = r && simple_stmt(t, lv + 1);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    /**
     * '{' 'NEWLINE' 'stmt'+ '}'
     */
    public static boolean block_suite_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, BLOCK_SUITE_2);
        boolean r;
        r = t.consume("{");
        r = r && t.consume(TokenType.NEWLINE);
        r = r && stmt_loop(t, lv);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    private static boolean stmt_loop(ParseTree t, int lv) {
        t.enterCollection();
        var r = stmt(t, lv + 1);
        if (r) while (true) {
            var p = t.position();
            if (!stmt(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
        return r;
    }

    /**
     * suite: ':' 'simple_stmt' | 'block_suite'
     */
    public static boolean suite(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SUITE);
        boolean r;
        r = suite_1(t, lv + 1);
        r = r || block_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ':' 'simple_stmt'
     */
    public static boolean suite_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SUITE_1);
        boolean r;
        r = t.consume(":");
        r = r && simple_stmt(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * else_suite: 'else' 'suite'
     */
    public static boolean else_suite(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ELSE_SUITE);
        boolean r;
        r = t.consume("else");
        r = r && suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * finally_suite: 'finally' 'suite'
     */
    public static boolean finally_suite(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FINALLY_SUITE);
        boolean r;
        r = t.consume("finally");
        r = r && suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * func_suite: ':' 'expr' | 'block_suite'
     */
    public static boolean func_suite(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FUNC_SUITE);
        boolean r;
        r = func_suite_1(t, lv + 1);
        r = r || block_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ':' 'expr'
     */
    public static boolean func_suite_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FUNC_SUITE_1);
        boolean r;
        r = t.consume(":");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * except_suite: ('except_clause' 'suite')+ ['else_suite'] ['finally_suite']
     */
    public static boolean except_suite(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXCEPT_SUITE);
        boolean r;
        r = except_suite_1_loop(t, lv);
        if (r) else_suite(t, lv + 1);
        if (r) finally_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static boolean except_suite_1_loop(ParseTree t, int lv) {
        t.enterCollection();
        var r = except_suite_1(t, lv + 1);
        if (r) while (true) {
            var p = t.position();
            if (!except_suite_1(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
        return r;
    }

    /**
     * 'except_clause' 'suite'
     */
    public static boolean except_suite_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXCEPT_SUITE_1);
        boolean r;
        r = except_clause(t, lv + 1);
        r = r && suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * typed_arg_list: 'kwargs' | 'args_kwargs' | 'full_arg_list'
     */
    public static boolean typed_arg_list(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TYPED_ARG_LIST);
        boolean r;
        r = kwargs(t, lv + 1);
        r = r || args_kwargs(t, lv + 1);
        r = r || full_arg_list(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * full_arg_list: 'default_arg' (',' 'default_arg')* [',' ['kwargs' | 'args_kwargs']]
     */
    public static boolean full_arg_list(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FULL_ARG_LIST);
        boolean r;
        r = default_arg(t, lv + 1);
        if (r) full_arg_list_2_loop(t, lv);
        if (r) full_arg_list_3(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void full_arg_list_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!full_arg_list_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'default_arg'
     */
    public static boolean full_arg_list_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FULL_ARG_LIST_2);
        boolean r;
        r = t.consume(",");
        r = r && default_arg(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ',' ['kwargs' | 'args_kwargs']
     */
    public static boolean full_arg_list_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FULL_ARG_LIST_3);
        boolean r;
        r = t.consume(",");
        if (r) full_arg_list_3_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'kwargs' | 'args_kwargs'
     */
    public static boolean full_arg_list_3_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FULL_ARG_LIST_3_2);
        boolean r;
        r = kwargs(t, lv + 1);
        r = r || args_kwargs(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * args_kwargs: '*' ['typed_arg'] (',' 'default_arg')* [',' ['kwargs']]
     */
    public static boolean args_kwargs(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGS_KWARGS);
        boolean r;
        r = t.consume("*");
        if (r) typed_arg(t, lv + 1);
        if (r) args_kwargs_3_loop(t, lv);
        if (r) args_kwargs_4(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void args_kwargs_3_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!args_kwargs_3(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'default_arg'
     */
    public static boolean args_kwargs_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGS_KWARGS_3);
        boolean r;
        r = t.consume(",");
        r = r && default_arg(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ',' ['kwargs']
     */
    public static boolean args_kwargs_4(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGS_KWARGS_4);
        boolean r;
        r = t.consume(",");
        if (r) kwargs(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * kwargs: '**' 'typed_arg' [',']
     */
    public static boolean kwargs(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, KWARGS);
        boolean r;
        r = t.consume("**");
        r = r && typed_arg(t, lv + 1);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    /**
     * default_arg: 'typed_arg' ['=' 'expr']
     */
    public static boolean default_arg(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DEFAULT_ARG);
        boolean r;
        r = typed_arg(t, lv + 1);
        if (r) default_arg_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '=' 'expr'
     */
    public static boolean default_arg_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DEFAULT_ARG_2);
        boolean r;
        r = t.consume("=");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * typed_arg: 'NAME' [':' 'expr']
     */
    public static boolean typed_arg(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TYPED_ARG);
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) typed_arg_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ':' 'expr'
     */
    public static boolean typed_arg_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TYPED_ARG_2);
        boolean r;
        r = t.consume(":");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * simple_arg_list: 'simple_arg' ('simple_arg')*
     */
    public static boolean simple_arg_list(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SIMPLE_ARG_LIST);
        boolean r;
        r = simple_arg(t, lv + 1);
        if (r) simple_arg_list_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void simple_arg_list_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!simple_arg_list_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'simple_arg'
     */
    public static boolean simple_arg_list_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SIMPLE_ARG_LIST_2);
        boolean r;
        r = simple_arg(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * simple_arg: 'NAME' ['=' 'expr']
     */
    public static boolean simple_arg(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SIMPLE_ARG);
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) simple_arg_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '=' 'expr'
     */
    public static boolean simple_arg_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SIMPLE_ARG_2);
        boolean r;
        r = t.consume("=");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * func_type_hint: '<' 'expr' '>'
     */
    public static boolean func_type_hint(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FUNC_TYPE_HINT);
        boolean r;
        r = t.consume("<");
        r = r && expr(t, lv + 1);
        r = r && t.consume(">");
        t.exit(r);
        return r;
    }

    /**
     * func_args: 'simple_arg_list' | '(' ['typed_arg_list'] ')'
     */
    public static boolean func_args(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FUNC_ARGS);
        boolean r;
        r = simple_arg_list(t, lv + 1);
        r = r || func_args_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '(' ['typed_arg_list'] ')'
     */
    public static boolean func_args_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FUNC_ARGS_2);
        boolean r;
        r = t.consume("(");
        if (r) typed_arg_list(t, lv + 1);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * funcdef: 'def' ['func_type_hint'] ['func_args'] 'func_suite'
     */
    public static boolean funcdef(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FUNCDEF);
        boolean r;
        r = t.consume("def");
        if (r) func_type_hint(t, lv + 1);
        if (r) func_args(t, lv + 1);
        r = r && func_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * named_expr: 'NAME' ':=' 'expr' | 'expr'
     */
    public static boolean named_expr(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, NAMED_EXPR);
        boolean r;
        r = named_expr_1(t, lv + 1);
        r = r || expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'NAME' ':=' 'expr'
     */
    public static boolean named_expr_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, NAMED_EXPR_1);
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume(":=");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * conditional: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
     */
    public static boolean conditional(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, CONDITIONAL);
        boolean r;
        r = t.consume("if");
        r = r && disjunction(t, lv + 1);
        r = r && t.consume("?");
        r = r && disjunction(t, lv + 1);
        r = r && t.consume("else");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * expr: 'conditional' | 'funcdef' | 'disjunction'
     */
    public static boolean expr(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXPR);
        boolean r;
        r = conditional(t, lv + 1);
        r = r || funcdef(t, lv + 1);
        r = r || disjunction(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * disjunction: 'conjunction' ('or' 'conjunction')*
     */
    public static boolean disjunction(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DISJUNCTION);
        boolean r;
        r = conjunction(t, lv + 1);
        if (r) disjunction_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void disjunction_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!disjunction_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'or' 'conjunction'
     */
    public static boolean disjunction_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DISJUNCTION_2);
        boolean r;
        r = t.consume("or");
        r = r && conjunction(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * conjunction: 'inversion' ('and' 'inversion')*
     */
    public static boolean conjunction(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, CONJUNCTION);
        boolean r;
        r = inversion(t, lv + 1);
        if (r) conjunction_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void conjunction_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!conjunction_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'and' 'inversion'
     */
    public static boolean conjunction_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, CONJUNCTION_2);
        boolean r;
        r = t.consume("and");
        r = r && inversion(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * inversion: 'not' 'inversion' | 'comparison'
     */
    public static boolean inversion(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, INVERSION);
        boolean r;
        r = inversion_1(t, lv + 1);
        r = r || comparison(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'not' 'inversion'
     */
    public static boolean inversion_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, INVERSION_1);
        boolean r;
        r = t.consume("not");
        r = r && inversion(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
     */
    public static boolean comparison(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, COMPARISON);
        boolean r;
        r = bitwise_or(t, lv + 1);
        if (r) comparison_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void comparison_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!comparison_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'comp_op' 'bitwise_or'
     */
    public static boolean comparison_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, COMPARISON_2);
        boolean r;
        r = comp_op(t, lv + 1);
        r = r && bitwise_or(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * comp_op: '<' | '>' | '==' | '>=' | '<=' | '!=' | 'in' | 'not' 'in' | 'is' | 'is' 'not'
     */
    public static boolean comp_op(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, COMP_OP);
        boolean r;
        r = t.consume("<");
        r = r || t.consume(">");
        r = r || t.consume("==");
        r = r || t.consume(">=");
        r = r || t.consume("<=");
        r = r || t.consume("!=");
        r = r || t.consume("in");
        r = r || comp_op_8(t, lv + 1);
        r = r || t.consume("is");
        r = r || comp_op_10(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'not' 'in'
     */
    public static boolean comp_op_8(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, COMP_OP_8);
        boolean r;
        r = t.consume("not");
        r = r && t.consume("in");
        t.exit(r);
        return r;
    }

    /**
     * 'is' 'not'
     */
    public static boolean comp_op_10(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, COMP_OP_10);
        boolean r;
        r = t.consume("is");
        r = r && t.consume("not");
        t.exit(r);
        return r;
    }

    /**
     * bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
     */
    public static boolean bitwise_or(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, BITWISE_OR);
        boolean r;
        r = bitwise_xor(t, lv + 1);
        if (r) bitwise_or_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void bitwise_or_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!bitwise_or_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '|' 'bitwise_xor'
     */
    public static boolean bitwise_or_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, BITWISE_OR_2);
        boolean r;
        r = t.consume("|");
        r = r && bitwise_xor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * bitwise_xor: 'bitwise_and' ('^' 'bitwise_and')*
     */
    public static boolean bitwise_xor(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, BITWISE_XOR);
        boolean r;
        r = bitwise_and(t, lv + 1);
        if (r) bitwise_xor_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void bitwise_xor_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!bitwise_xor_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '^' 'bitwise_and'
     */
    public static boolean bitwise_xor_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, BITWISE_XOR_2);
        boolean r;
        r = t.consume("^");
        r = r && bitwise_and(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * bitwise_and: 'shift_expr' ('&' 'shift_expr')*
     */
    public static boolean bitwise_and(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, BITWISE_AND);
        boolean r;
        r = shift_expr(t, lv + 1);
        if (r) bitwise_and_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void bitwise_and_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!bitwise_and_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '&' 'shift_expr'
     */
    public static boolean bitwise_and_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, BITWISE_AND_2);
        boolean r;
        r = t.consume("&");
        r = r && shift_expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * shift_op: '<<' | '>>'
     */
    public static boolean shift_op(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SHIFT_OP);
        boolean r;
        r = t.consume("<<");
        r = r || t.consume(">>");
        t.exit(r);
        return r;
    }

    /**
     * shift_expr: 'sum' ('shift_op' 'sum')*
     */
    public static boolean shift_expr(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SHIFT_EXPR);
        boolean r;
        r = sum(t, lv + 1);
        if (r) shift_expr_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void shift_expr_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!shift_expr_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'shift_op' 'sum'
     */
    public static boolean shift_expr_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SHIFT_EXPR_2);
        boolean r;
        r = shift_op(t, lv + 1);
        r = r && sum(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * sum_op: '+' | '-'
     */
    public static boolean sum_op(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SUM_OP);
        boolean r;
        r = t.consume("+");
        r = r || t.consume("-");
        t.exit(r);
        return r;
    }

    /**
     * sum: 'term' ('sum_op' 'term')*
     */
    public static boolean sum(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SUM);
        boolean r;
        r = term(t, lv + 1);
        if (r) sum_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void sum_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!sum_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'sum_op' 'term'
     */
    public static boolean sum_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SUM_2);
        boolean r;
        r = sum_op(t, lv + 1);
        r = r && term(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * term_op: '*' | '@' | '/' | '%' | '//'
     */
    public static boolean term_op(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TERM_OP);
        boolean r;
        r = t.consume("*");
        r = r || t.consume("@");
        r = r || t.consume("/");
        r = r || t.consume("%");
        r = r || t.consume("//");
        t.exit(r);
        return r;
    }

    /**
     * term: 'pipeline' ('term_op' 'pipeline')*
     */
    public static boolean term(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TERM);
        boolean r;
        r = pipeline(t, lv + 1);
        if (r) term_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void term_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!term_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'term_op' 'pipeline'
     */
    public static boolean term_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TERM_2);
        boolean r;
        r = term_op(t, lv + 1);
        r = r && pipeline(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * pipeline: 'factor' ('->' 'pipe_expr')*
     */
    public static boolean pipeline(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, PIPELINE);
        boolean r;
        r = factor(t, lv + 1);
        if (r) pipeline_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void pipeline_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!pipeline_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '->' 'pipe_expr'
     */
    public static boolean pipeline_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, PIPELINE_2);
        boolean r;
        r = t.consume("->");
        r = r && pipe_expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * pipe_expr: 'pipe_for' | 'factor'
     */
    public static boolean pipe_expr(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, PIPE_EXPR);
        boolean r;
        r = pipe_for(t, lv + 1);
        r = r || factor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * pipe_for: ['comp_for'] 'for' 'targetlist' ['if' 'named_expr'] ['parameters' | 'block_suite']
     */
    public static boolean pipe_for(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, PIPE_FOR);
        boolean r;
        comp_for(t, lv + 1);
        r = t.consume("for");
        r = r && targetlist(t, lv + 1);
        if (r) pipe_for_4(t, lv + 1);
        if (r) pipe_for_5(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'if' 'named_expr'
     */
    public static boolean pipe_for_4(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, PIPE_FOR_4);
        boolean r;
        r = t.consume("if");
        r = r && named_expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'parameters' | 'block_suite'
     */
    public static boolean pipe_for_5(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, PIPE_FOR_5);
        boolean r;
        r = parameters(t, lv + 1);
        r = r || block_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * comp_for: 'for' 'targetlist' 'in' 'disjunction' ['comp_iter']
     */
    public static boolean comp_for(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, COMP_FOR);
        boolean r;
        r = t.consume("for");
        r = r && targetlist(t, lv + 1);
        r = r && t.consume("in");
        r = r && disjunction(t, lv + 1);
        if (r) comp_iter(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * comp_if: 'if' 'named_expr' ['comp_iter']
     */
    public static boolean comp_if(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, COMP_IF);
        boolean r;
        r = t.consume("if");
        r = r && named_expr(t, lv + 1);
        if (r) comp_iter(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * comp_iter: 'comp_for' | 'comp_if'
     */
    public static boolean comp_iter(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, COMP_ITER);
        boolean r;
        r = comp_for(t, lv + 1);
        r = r || comp_if(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * factor: ('+' | '-' | '~') 'factor' | 'power'
     */
    public static boolean factor(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FACTOR);
        boolean r;
        r = factor_1(t, lv + 1);
        r = r || power(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ('+' | '-' | '~') 'factor'
     */
    public static boolean factor_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FACTOR_1);
        boolean r;
        r = factor_1_1(t, lv + 1);
        r = r && factor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '+' | '-' | '~'
     */
    public static boolean factor_1_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FACTOR_1_1);
        boolean r;
        r = t.consume("+");
        r = r || t.consume("-");
        r = r || t.consume("~");
        t.exit(r);
        return r;
    }

    /**
     * power: 'primary' ['**' 'factor']
     */
    public static boolean power(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, POWER);
        boolean r;
        r = primary(t, lv + 1);
        if (r) power_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '**' 'factor'
     */
    public static boolean power_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, POWER_2);
        boolean r;
        r = t.consume("**");
        r = r && factor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * primary: 'atom' 'trailer'* ['block_suite']
     */
    public static boolean primary(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, PRIMARY);
        boolean r;
        r = atom(t, lv + 1);
        if (r) trailer_loop(t, lv);
        if (r) block_suite(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void trailer_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!trailer(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * atom: 'tuple_atom' | 'list_atom' | 'dict_or_set' | 'NAME' | 'NUMBER' | 'STRING' | 'None' | 'True' | 'False'
     */
    public static boolean atom(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ATOM);
        boolean r;
        r = tuple_atom(t, lv + 1);
        r = r || list_atom(t, lv + 1);
        r = r || dict_or_set(t, lv + 1);
        r = r || t.consume(TokenType.NAME);
        r = r || t.consume(TokenType.NUMBER);
        r = r || t.consume(TokenType.STRING);
        r = r || t.consume("None");
        r = r || t.consume("True");
        r = r || t.consume("False");
        t.exit(r);
        return r;
    }

    /**
     * tuple_atom: '(' ['named_expr_list'] ')'
     */
    public static boolean tuple_atom(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TUPLE_ATOM);
        boolean r;
        r = t.consume("(");
        if (r) named_expr_list(t, lv + 1);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * list_atom: '[' ['named_expr_list'] ']'
     */
    public static boolean list_atom(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, LIST_ATOM);
        boolean r;
        r = t.consume("[");
        if (r) named_expr_list(t, lv + 1);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }

    /**
     * dict_or_set: '{' ['dict_maker' | 'set_maker'] '}'
     */
    public static boolean dict_or_set(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DICT_OR_SET);
        boolean r;
        r = t.consume("{");
        if (r) dict_or_set_2(t, lv + 1);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    /**
     * 'dict_maker' | 'set_maker'
     */
    public static boolean dict_or_set_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DICT_OR_SET_2);
        boolean r;
        r = dict_maker(t, lv + 1);
        r = r || set_maker(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * named_expr_star: 'star_expr' | 'named_expr'
     */
    public static boolean named_expr_star(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, NAMED_EXPR_STAR);
        boolean r;
        r = star_expr(t, lv + 1);
        r = r || named_expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * named_expr_list: 'named_expr_star' (',' 'named_expr_star')* [',']
     */
    public static boolean named_expr_list(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, NAMED_EXPR_LIST);
        boolean r;
        r = named_expr_star(t, lv + 1);
        if (r) named_expr_list_2_loop(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void named_expr_list_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!named_expr_list_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'named_expr_star'
     */
    public static boolean named_expr_list_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, NAMED_EXPR_LIST_2);
        boolean r;
        r = t.consume(",");
        r = r && named_expr_star(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * trailer: '.' 'NAME' | 'parameters' | 'subscript'
     */
    public static boolean trailer(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TRAILER);
        boolean r;
        r = trailer_1(t, lv + 1);
        r = r || parameters(t, lv + 1);
        r = r || subscript(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '.' 'NAME'
     */
    public static boolean trailer_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TRAILER_1);
        boolean r;
        r = t.consume(".");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * subscript: '[' 'slicelist' ']'
     */
    public static boolean subscript(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SUBSCRIPT);
        boolean r;
        r = t.consume("[");
        r = r && slicelist(t, lv + 1);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }

    /**
     * slicelist: 'slice' (',' 'slice')* [',']
     */
    public static boolean slicelist(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SLICELIST);
        boolean r;
        r = slice(t, lv + 1);
        if (r) slicelist_2_loop(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void slicelist_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!slicelist_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'slice'
     */
    public static boolean slicelist_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SLICELIST_2);
        boolean r;
        r = t.consume(",");
        r = r && slice(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * slice: ['expr'] 'slice_expr' ['slice_expr'] | 'expr'
     */
    public static boolean slice(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SLICE);
        boolean r;
        r = slice_1(t, lv + 1);
        r = r || expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ['expr'] 'slice_expr' ['slice_expr']
     */
    public static boolean slice_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SLICE_1);
        boolean r;
        expr(t, lv + 1);
        r = slice_expr(t, lv + 1);
        if (r) slice_expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * slice_expr: ':' ['expr']
     */
    public static boolean slice_expr(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SLICE_EXPR);
        boolean r;
        r = t.consume(":");
        if (r) expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * parameters: '(' ['arglist'] ')'
     */
    public static boolean parameters(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, PARAMETERS);
        boolean r;
        r = t.consume("(");
        if (r) arglist(t, lv + 1);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * target: 'NAME' | '(' 'targetlist' ')' | '*' 'target' | 'primary'
     */
    public static boolean target(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TARGET);
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r || target_2(t, lv + 1);
        r = r || target_3(t, lv + 1);
        r = r || primary(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '(' 'targetlist' ')'
     */
    public static boolean target_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TARGET_2);
        boolean r;
        r = t.consume("(");
        r = r && targetlist(t, lv + 1);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * '*' 'target'
     */
    public static boolean target_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TARGET_3);
        boolean r;
        r = t.consume("*");
        r = r && target(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * targetlist: 'target' (',' 'target')* [',']
     */
    public static boolean targetlist(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TARGETLIST);
        boolean r;
        r = target(t, lv + 1);
        if (r) targetlist_2_loop(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void targetlist_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!targetlist_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'target'
     */
    public static boolean targetlist_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TARGETLIST_2);
        boolean r;
        r = t.consume(",");
        r = r && target(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * exprlist: 'expr' (',' 'expr')* [',']
     */
    public static boolean exprlist(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXPRLIST);
        boolean r;
        r = expr(t, lv + 1);
        if (r) exprlist_2_loop(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void exprlist_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!exprlist_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'expr'
     */
    public static boolean exprlist_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, EXPRLIST_2);
        boolean r;
        r = t.consume(",");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * dict_item: 'expr' ':' 'expr' | '**' 'bitwise_or'
     */
    public static boolean dict_item(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DICT_ITEM);
        boolean r;
        r = dict_item_1(t, lv + 1);
        r = r || dict_item_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'expr' ':' 'expr'
     */
    public static boolean dict_item_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DICT_ITEM_1);
        boolean r;
        r = expr(t, lv + 1);
        r = r && t.consume(":");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '**' 'bitwise_or'
     */
    public static boolean dict_item_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DICT_ITEM_2);
        boolean r;
        r = t.consume("**");
        r = r && bitwise_or(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * dict_maker: 'dict_item' (',' 'dict_item')* [',']
     */
    public static boolean dict_maker(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DICT_MAKER);
        boolean r;
        r = dict_item(t, lv + 1);
        if (r) dict_maker_2_loop(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void dict_maker_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!dict_maker_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'dict_item'
     */
    public static boolean dict_maker_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, DICT_MAKER_2);
        boolean r;
        r = t.consume(",");
        r = r && dict_item(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * set_maker: 'expr_or_star' (',' 'expr_or_star')* [',']
     */
    public static boolean set_maker(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SET_MAKER);
        boolean r;
        r = expr_or_star(t, lv + 1);
        if (r) set_maker_2_loop(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void set_maker_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!set_maker_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'expr_or_star'
     */
    public static boolean set_maker_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SET_MAKER_2);
        boolean r;
        r = t.consume(",");
        r = r && expr_or_star(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * arglist: 'argument' (',' 'argument')* [',']
     */
    public static boolean arglist(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGLIST);
        boolean r;
        r = argument(t, lv + 1);
        if (r) arglist_2_loop(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void arglist_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!arglist_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'argument'
     */
    public static boolean arglist_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGLIST_2);
        boolean r;
        r = t.consume(",");
        r = r && argument(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * argument: 'NAME' ':=' 'expr' | 'NAME' '=' 'expr' | '**' 'expr' | '*' 'expr' | 'expr'
     */
    public static boolean argument(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGUMENT);
        boolean r;
        r = argument_1(t, lv + 1);
        r = r || argument_2(t, lv + 1);
        r = r || argument_3(t, lv + 1);
        r = r || argument_4(t, lv + 1);
        r = r || expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'NAME' ':=' 'expr'
     */
    public static boolean argument_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGUMENT_1);
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume(":=");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'NAME' '=' 'expr'
     */
    public static boolean argument_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGUMENT_2);
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume("=");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '**' 'expr'
     */
    public static boolean argument_3(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGUMENT_3);
        boolean r;
        r = t.consume("**");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '*' 'expr'
     */
    public static boolean argument_4(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ARGUMENT_4);
        boolean r;
        r = t.consume("*");
        r = r && expr(t, lv + 1);
        t.exit(r);
        return r;
    }
}
