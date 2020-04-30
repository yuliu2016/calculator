package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
public final class FileInput extends ConjunctionRule {
    private final List<FileInput1Group> fileInput1GroupList;
    private final Object endmarker;

    public FileInput(
            List<FileInput1Group> fileInput1GroupList,
            Object endmarker
    ) {
        this.fileInput1GroupList = fileInput1GroupList;
        this.endmarker = endmarker;

        addRequired("fileInput1GroupList", fileInput1GroupList);
        addRequired("endmarker", endmarker);
    }

    public List<FileInput1Group> getFileInput1GroupList() {
        return fileInput1GroupList;
    }

    public Object getEndmarker() {
        return endmarker;
    }

    // 'NEWLINE' | 'stmt'
    public static final class FileInput1Group extends DisjunctionRule {
        private final Object newline;
        private final Stmt stmt;

        public FileInput1Group(
                Object newline,
                Stmt stmt
        ) {
            this.newline = newline;
            this.stmt = stmt;

            addChoice("newline", newline);
            addChoice("stmt", stmt);
        }

        public Object getNewline() {
            return newline;
        }

        public Stmt getStmt() {
            return stmt;
        }
    }
}
