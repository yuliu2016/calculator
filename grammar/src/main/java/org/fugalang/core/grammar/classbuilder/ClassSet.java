package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.gen.PackageOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassSet {

    private final PackageOutput packageOutput;

    private final List<NamedClass> classes;

    // represents the working top-level class
    private NamedClass currentClass;

    public ClassSet(PackageOutput packageOutput) {
        this.packageOutput = packageOutput;
        classes = new ArrayList<>();
    }

    public List<NamedClass> getClasses() {
        return classes;
    }

    public ClassBuilder createRootClass(ClassName className) {
        var dupError = false;
        for (var cls : classes) {
            if (cls.getClassName().equals(className.getType())) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            for (var cls : classes) {
                System.out.println(cls.generateClassCode());
            }
            throw new IllegalArgumentException("Duplicate class: " + className);
        }

        var rootClassBuilder = new ClassBuilder(packageOutput.getPackageName(),
                className.getType(), className.getRuleName());

        currentClass = new NamedClass(rootClassBuilder);
        classes.add(currentClass);

        return rootClassBuilder;
    }

    public void markRootClassDone() {
        if (currentClass == null) {
            throw new IllegalStateException("No root class being worked on");
        }
        currentClass = null;
    }

    public ClassBuilder createComponentClass(ClassName className) {

        if (currentClass == null) {
            throw new IllegalStateException("No root class to add component to");
        }

        var current = currentClass;

        var dupError = false;
        for (var builder : current.components) {
            if (builder.getClassName().equals(className.getType())) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            for (var builder : current.components) {
                System.out.println(builder.generateClassCode());
            }
            throw new IllegalArgumentException("Duplicate inner class: " + className);
        }

        var builder = new ClassBuilder(packageOutput.getPackageName(),
                className.getType(), className.getRuleName());

        current.components.add(builder);

        return builder;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void writeToFiles() {
        try {
            var rootFile = packageOutput.getFilePath().toFile();

            if (!rootFile.isDirectory()) {
                rootFile.mkdirs();
            }

            var fileList = rootFile.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    file.delete();
                }
            }

            for (var aClass : classes) {
                var code = aClass.generateClassCode()
                        .replace("\n", System.lineSeparator());

                Files.writeString(Paths.get(packageOutput.getFilePath().toString(),
                        aClass.getClassName() + ".java"), code);
            }

            var parserBase = packageOutput.getParserPath().toString();
            var parserPath = Paths.get(parserBase, "Parser.java");
            var pcls = generateParserClass();
            Files.writeString(parserPath, pcls);

            var parserRulePath = Paths.get(parserBase, "ParserRules.java");
            var ruleClass = generateRuleClass();
            Files.writeString(parserRulePath, ruleClass);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateParserClass() {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageOutput.getParserPackage()).append(";\n\n");
        sb.append("import org.fugalang.core.parser.ParseTree;\n");
        sb.append("import org.fugalang.core.token.TokenType;\n\n" + "import static ")
                .append(packageOutput.getParserPackage());
        sb.append(".ParserRules.*;\n\n" +
                "@SuppressWarnings(\"UnusedReturnValue\")\n" +
                "public class Parser {\n");
        for (NamedClass namedClass : classes) {
            namedClass.generateParser(sb);
        }
        sb.append("}\n");
        return sb.toString();
    }

    private String generateRuleClass() {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageOutput.getParserPackage()).append(";\n\n");
        sb.append("import org.fugalang.core.parser.ParserRule;\n\n");
        sb.append("import static org.fugalang.core.parser.ParserRule.and_rule;\n" +
                "import static org.fugalang.core.parser.ParserRule.or_rule;\n\n" +
                "public class ParserRules {\n");
        for (NamedClass namedClass : classes) {
            namedClass.generateRules(sb);
        }
        sb.append("}\n");
        return sb.toString();
    }
}
