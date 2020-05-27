package org.fugalang.grammar.gen;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PackageOutput {
    private final Path filePath;
    private final String packageName;
    private final String language;

    public PackageOutput(String basePath, String relPath, String packageName, String language) {
        this.packageName = packageName;
        this.filePath = Paths.get(basePath, relPath,
                packageName.replace(".", "/"));
        this.language = language;
    }

    public String getLanguage() {
        return language;
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
