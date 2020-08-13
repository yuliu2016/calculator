package org.fugalang.grammar.cgen;

import java.nio.file.Path;

public class CpegConfig {
    private final String nodeStruct;
    private final String fileName;
    private final String includePath;
    private final Path rootPath;

    public CpegConfig(String nodeStruct, String fileName, String includePath, Path rootPath) {
        this.nodeStruct = nodeStruct;
        this.fileName = fileName;
        this.includePath = includePath;
        this.rootPath = rootPath;
    }

    public String getIncludePath() {
        return includePath;
    }

    public String getNodeStruct() {
        return nodeStruct;
    }

    public String getFileName() {
        return fileName;
    }

    public Path getRootPath() {
        return rootPath;
    }
}
