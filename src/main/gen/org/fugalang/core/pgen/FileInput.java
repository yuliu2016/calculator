package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
 */
public final class FileInput extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("file_input", RuleType.Conjunction);

    public static FileInput of(ParseTreeNode node) {
        return new FileInput(node);
    }

    private FileInput(ParseTreeNode node) {
        super(RULE, node);
    }

    public List<FileInput1> fileInput1List() {
        return getList(0, FileInput1::of);
    }

    public String endmarker() {
        return getItemOfType(1, TokenType.ENDMARKER);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        parseFileInput1List(t, lv);
        r = t.consumeToken(TokenType.ENDMARKER);
        t.exit(r);
        return r;
    }

    private static void parseFileInput1List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!FileInput1.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'NEWLINE' | 'stmt'
     */
    public static final class FileInput1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("file_input:1", RuleType.Disjunction);

        public static FileInput1 of(ParseTreeNode node) {
            return new FileInput1(node);
        }

        private FileInput1(ParseTreeNode node) {
            super(RULE, node);
        }

        public String newline() {
            return getItemOfType(0, TokenType.NEWLINE);
        }

        public boolean hasNewline() {
            return hasItemOfType(0, TokenType.NEWLINE);
        }

        public Stmt stmt() {
            return Stmt.of(getItem(1));
        }

        public boolean hasStmt() {
            return hasItemOfRule(1, Stmt.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken(TokenType.NEWLINE);
            r = r || Stmt.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
