package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
public final class FileInput extends ConjunctionRule {
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
        addRequired("fileInput1List", fileInput1List);
        addRequired("endmarker", endmarker);
    }

    public List<FileInput1> fileInput1List() {
        return fileInput1List;
    }

    public Object endmarker() {
        return endmarker;
    }

    // 'NEWLINE' | 'stmt'
    public static final class FileInput1 extends DisjunctionRule {
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
            addChoice("newline", newline);
            addChoice("stmt", stmt);
        }

        public Object newline() {
            return newline;
        }

        public Stmt stmt() {
            return stmt;
        }
    }
}
