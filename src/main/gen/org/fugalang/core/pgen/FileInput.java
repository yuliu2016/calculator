package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<FileInput1> fileInput1List;

    @Override
    protected void buildRule() {
        addRequired(fileInput1List());
        addRequired(endmarker());
    }

    public List<FileInput1> fileInput1List() {
        if (fileInput1List != null) {
            return fileInput1List;
        }
        List<FileInput1> result = null;
        var element = getItem(0);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(FileInput1.of(node));
        }
        fileInput1List = result == null ? Collections.emptyList() : result;
        return fileInput1List;
    }

    public String endmarker() {
        var element = getItem(1);
        element.failIfAbsent(TokenType.ENDMARKER);
        return element.asString();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        parseFileInput1List(parseTree, level + 1);
        result = parseTree.consumeToken(TokenType.ENDMARKER);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseFileInput1List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!FileInput1.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
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

        @Override
        protected void buildRule() {
            addChoice(newlineOrNull());
            addChoice(stmtOrNull());
        }

        public String newline() {
            var element = getItem(0);
            element.failIfAbsent(TokenType.NEWLINE);
            return element.asString();
        }

        public String newlineOrNull() {
            var element = getItem(0);
            if (!element.isPresent(TokenType.NEWLINE)) {
                return null;
            }
            return element.asString();
        }

        public boolean hasNewline() {
            var element = getItem(0);
            return element.isPresent(TokenType.NEWLINE);
        }

        public Stmt stmt() {
            var element = getItem(1);
            element.failIfAbsent(Stmt.RULE);
            return Stmt.of(element);
        }

        public Stmt stmtOrNull() {
            var element = getItem(1);
            if (!element.isPresent(Stmt.RULE)) {
                return null;
            }
            return Stmt.of(element);
        }

        public boolean hasStmt() {
            var element = getItem(1);
            return element.isPresent(Stmt.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(TokenType.NEWLINE);
            result = result || Stmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
