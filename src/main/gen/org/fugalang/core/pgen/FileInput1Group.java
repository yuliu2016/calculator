package org.fugalang.core.pgen;

// 'NEWLINE' | 'stmt'
public class FileInput1Group {
    public final Object newline;
    public final Stmt stmt;

    public FileInput1Group(
            Object newline,
            Stmt stmt
    ) {
        this.newline = newline;
        this.stmt = stmt;
    }
}
