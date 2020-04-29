package org.fugalang.core.pgen;

import java.util.List;

// file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
public class FileInput {
    private final List<FileInput1Group> fileInput1GroupList;
    private final Object endmarker;

    public FileInput(
            List<FileInput1Group> fileInput1GroupList,
            Object endmarker
    ) {
        this.fileInput1GroupList = fileInput1GroupList;
        this.endmarker = endmarker;
    }

    public List<FileInput1Group> getFileInput1GroupList() {
        return fileInput1GroupList;
    }

    public Object getEndmarker() {
        return endmarker;
    }

    // 'NEWLINE' | 'stmt'
    public static class FileInput1Group {
        private final Object newline;
        private final Stmt stmt;

        public FileInput1Group(
                Object newline,
                Stmt stmt
        ) {
            this.newline = newline;
            this.stmt = stmt;
        }

        public Object getNewline() {
            return newline;
        }

        public Stmt getStmt() {
            return stmt;
        }
    }
}
