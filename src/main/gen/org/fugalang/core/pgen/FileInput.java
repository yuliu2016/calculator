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

    public List<FileInput1> newlineOrStmts() {
        return getList(0, FileInput1::of);
    }

    public String endmarker() {
        return get(1, TokenType.ENDMARKER);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        parseNewlineOrStmts(t, lv);
        r = t.consume(TokenType.ENDMARKER);
        t.exit(r);
        return r;
    }

    private static void parseNewlineOrStmts(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!FileInput1.parse(t, lv + 1) || t.loopGuard(p)) break;
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
            return get(0, TokenType.NEWLINE);
        }

        public boolean hasNewline() {
            return has(0, TokenType.NEWLINE);
        }

        public Stmt stmt() {
            return get(1, Stmt::of);
        }

        public boolean hasStmt() {
            return has(1, Stmt.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(TokenType.NEWLINE);
            r = r || Stmt.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
