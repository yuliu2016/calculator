package org.fugalang.core.grammar.gen;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PackageOutput {
    private final Path filePath;
    private final String packageName;

    public PackageOutput(String basePath, String relPath, String packageName) {
        this.packageName = packageName;
        this.filePath = Paths.get(basePath, relPath,
                packageName.replace(".", "/"));
    }

    public String getPackageName() {
        return packageName;
    }

    public Path getFilePath() {
        return filePath;
    }

    public Path getParserPath() {
        return Paths.get(filePath.toString(), "parser");
    }

    public String getParserPackage() {
        return packageName + ".parser";
    }
}
