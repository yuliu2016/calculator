package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
 */
public final class FileInput extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("file_input", RuleType.Conjunction, true);

    private final List<FileInput1> fileInput1List;
    private final Object endmarker;

    public FileInput(
            List<FileInput1> fileInput1List,
            Object endmarker
    ) {
        this.fileInput1List = fileInput1List;
        this.endmarker = endmarker;
    }

    @Override
    protected void buildRule() {
        addRequired("fileInput1List", fileInput1List());
        addRequired("endmarker", endmarker());
    }

    public List<FileInput1> fileInput1List() {
        return fileInput1List;
    }

    public Object endmarker() {
        return endmarker;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!FileInput1.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = parseTree.consumeTokenType("ENDMARKER");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'NEWLINE' | 'stmt'
     */
    public static final class FileInput1 extends DisjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("file_input:1", RuleType.Disjunction, false);

        private final Object newline;
        private final Stmt stmt;

        public FileInput1(
                Object newline,
                Stmt stmt
        ) {
            this.newline = newline;
            this.stmt = stmt;
        }

        @Override
        protected void buildRule() {
            addChoice("newline", newline());
            addChoice("stmt", stmt());
        }

        public Object newline() {
            return newline;
        }

        public boolean hasNewline() {
            return newline() != null;
        }

        public Stmt stmt() {
            return stmt;
        }

        public boolean hasStmt() {
            return stmt() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenType("NEWLINE");
            result = result || Stmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
