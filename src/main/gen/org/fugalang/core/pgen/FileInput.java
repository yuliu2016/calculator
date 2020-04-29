package org.fugalang.core.pgen;

// file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
public class FileInput {
    public final FileInput1Group fileInput1Group;
    public final Object endmarker;

    public FileInput(
            FileInput1Group fileInput1Group,
            Object endmarker
    ) {
        this.fileInput1Group = fileInput1Group;
        this.endmarker = endmarker;
    }
}
