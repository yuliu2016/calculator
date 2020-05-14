package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
 */
public final class FileInput extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("file_input", RuleType.Conjunction, true);

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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        parseFileInput1List(parseTree, level);
        result = parseTree.consumeToken(TokenType.ENDMARKER);

        parseTree.exit(result);
        return result;
    }

    private static void parseFileInput1List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!FileInput1.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * 'NEWLINE' | 'stmt'
     */
    public static final class FileInput1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("file_input:1", RuleType.Disjunction, false);

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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(TokenType.NEWLINE);
            result = result || Stmt.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}
