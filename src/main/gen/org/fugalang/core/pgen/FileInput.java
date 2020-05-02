package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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
        addRequired("fileInput1List", fileInput1List());
        addRequired("endmarker", endmarker());
    }

    public List<FileInput1> fileInput1List() {
        return fileInput1List;
    }

    public Object endmarker() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return element.asObject();
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
            addChoice("newline", newline());
            addChoice("stmt", stmt());
        }

        public Object newline() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return element.asObject();
        }

        public boolean hasNewline() {
            return newline() != null;
        }

        public Stmt stmt() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Stmt.of(element);
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
