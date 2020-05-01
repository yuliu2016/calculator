package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
public final class FileInput extends ConjunctionRule {
    public static final String RULE_NAME = "file_input";

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
        setExplicitName(RULE_NAME);
        addRequired("fileInput1List", fileInput1List);
        addRequired("endmarker", endmarker);
    }

    public List<FileInput1> fileInput1List() {
        return fileInput1List;
    }

    public Object endmarker() {
        return endmarker;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
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

    // 'NEWLINE' | 'stmt'
    public static final class FileInput1 extends DisjunctionRule {
        public static final String RULE_NAME = "file_input:1";

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
            setImpliedName(RULE_NAME);
            addChoice("newline", newline);
            addChoice("stmt", stmt);
        }

        public Object newline() {
            return newline;
        }

        public Stmt stmt() {
            return stmt;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenType("NEWLINE");
            result = result || Stmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
