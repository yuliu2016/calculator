package org.fugalang.core.pgen;

import java.util.List;

// file_input: ('NEWLINE' | 'stmt')* 'ENDMARKER'
public class FileInput {
    public final List<FileInput1Group> fileInput1GroupList;
    public final Object endmarker;

    public FileInput(
            List<FileInput1Group> fileInput1GroupList,
            Object endmarker
    ) {
        this.fileInput1GroupList = fileInput1GroupList;
        this.endmarker = endmarker;
    }
}
