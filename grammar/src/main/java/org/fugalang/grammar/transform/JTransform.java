package org.fugalang.grammar.transform;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.FirstAndMore;
import org.fugalang.grammar.util.StringUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.fugalang.grammar.common.FieldType.*;

public class JTransform {
    public static void generateParser(StringBuilder sb, NamedRule rule) {
        generateParsingFunc(sb, rule.getRoot(), true);
        for (var subRule : rule.getComponents()) {
            generateParsingFunc(sb, subRule, false);
        }
    }

    public static void generateParsingFunc(StringBuilder sb, UnitRule rule, boolean isNamedRule) {
        var small_name = rule.getRuleName().symbolicName();
        var cap_name = small_name.toUpperCase();
        sb.append("\n");

        // rule name constant

        var headerComments = rule.getGrammarString();
        if (headerComments != null && !headerComments.isBlank()) {
            sb.append(StringUtil.javadoc(headerComments, 4));
        }

        var visibility = isNamedRule ? "public" : "private";
        sb.append("    ").append(visibility).append(" static boolean ")
                .append(small_name)
                .append("(ParseTree t) {\n");

        sb.append("        var m = t.enter(")
                .append(cap_name).append(");\n");
        sb.append("        if (m != null) return m;\n");

        if (rule.isLeftRecursive()) {
            if (!isNamedRule) {
                throw new IllegalStateException(
                        "Cannot be left-recursive and not a named rule");
            }
            generateLeftRecursiveBody(sb, rule);
        } else {
            generateNormalParserBody(sb, rule);
        }

        sb.append("    }\n");

        for (UnitField field : rule.getFields()) {
            var loopParser = getLoopParser(field);
            if (loopParser != null) {
                sb.append(loopParser);
            }
        }
    }

    private static void generateNormalParserBody(StringBuilder sb, UnitRule rule) {
        sb.append("        boolean r;\n");

        var first = true;
        for (UnitField field : rule.getFields()) {
            var result = getParserFieldStatement(field, rule, first);
            if (field.isRequired()) {
                first = false;
            }
            sb.append("        ");
            sb.append(result);
        }

        if (first) {
            throw new IllegalStateException("The rule " + rule.getRuleName() +
                    " may match an empty string");
        }

        sb.append("""
                        t.exit(r);
                        return r;
                """);
    }



    private static void generateLeftRecursiveBody(StringBuilder sb, UnitRule rule) {
        sb.append("""
                        var p = t.position();
                        boolean s = false;
                        while (true) {
                            t.cache(s);
                            boolean r;
                """);
        var first = true;
        for (UnitField field : rule.getFields()) {
            var result = getParserFieldStatement(field, rule, first);
            if (field.isRequired()) {
                first = false;
            }
            sb.append("            ");
            sb.append(result);
        }

        if (first) {
            throw new IllegalStateException("The rule " + rule.getRuleName() +
                    " may match an empty string");
        }

        sb.append("""
                            s = r || s;
                            var e = t.position();
                            if (e <= p) break;
                            p = e;
                        }
                        t.restore(p);
                        t.exit(s);
                        return s;
                """);
    }

    private static String getParserFieldStatement(UnitField field, UnitRule rule, boolean isFirst) {
        var ruleType = rule.getRuleType();
        return switch (field.getFieldType()) {
            case RequireTrue -> getRequiredStmt(getResultExpr(field, true), ruleType, isFirst);
            case RequireFalse -> getRequiredStmt("!" + getResultExpr(field, true), ruleType, isFirst);
            case Required -> getRequiredStmt(getResultExpr(field, false), ruleType, isFirst);
            case Optional -> getOptionalStmt(getResultExpr(field, false), ruleType, isFirst);
            case RequiredList -> getRequiredStmt(getLoopExpr(field), ruleType, isFirst);
            case OptionalList -> getOptionalStmt(getLoopExpr(field), ruleType, isFirst);
        };
    }

    private static String getLoopExpr(UnitField field) {
        var rule_name = field.getRuleName().symbolicName();
        return rule_name + "_loop(t)";
    }

    private static String getOptionalStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        var condition = isFirst ? "" :
                (ruleType == RuleType.Conjunction ?
                        "if (r) " : "if (!r) ");
        return condition + resultExpr + ";\n";
    }

    private static String getRequiredStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        var condition = isFirst ? "r = " :
                (ruleType == RuleType.Conjunction ?
                        "r = r && " : "r = r || ");
        return condition + resultExpr + ";\n";
    }

    private static String getResultExpr(UnitField field, boolean isPredicate) {
        var parseTreeInst = isPredicate ? "t.test()" : "t";
        var resultSource = field.getResultSource();
        return switch (resultSource.kind()) {
            case UnitRule -> ((RuleName) resultSource.value()).symbolicName() +
                    "(" + parseTreeInst + ")";
            case TokenType -> parseTreeInst + ".consume(TokenType." +
                    ((TokenEntry)resultSource.value()).literalValue() + ")";
            case TokenLiteral -> parseTreeInst + ".consume(\"" +
                    ((TokenEntry) resultSource.value()).literalValue() + "\")";
        };
    }

    private static String getLoopParser(UnitField field) {
        if (field.isSingular() || field.isPredicate()) {
            return null;
        }
        return field.getFieldType() == FieldType.RequiredList ?
                getRequiredLoopParser(field) : getOptionalLoopParser(field);
    }


    private static String getRequiredLoopParser(UnitField field) {
        var resultExpr = getResultExpr(field, false);
        var rule_name = field.getRuleName().symbolicName();

        String whileBody;
        TokenEntry delimiter = field.getDelimiter();
        if (delimiter == null) {
            whileBody = "            if (!" + resultExpr + ") break;\n";
        } else {
            whileBody = "            var p = t.position();\n" +
                    "            if (t.skip(\"" + delimiter.literalValue() + "\") && "
                    + resultExpr + ") continue;\n" +
                    "            t.reset(p);\n" +
                    "            break;\n";
        }

        return "\n    private static boolean " + rule_name + "_loop(ParseTree t) {\n" +
                "        t.enterLoop();\n" +
                "        var r = " + resultExpr + ";\n" +
                "        if (r) while (true) {\n" +
                whileBody +
                "        }\n" +
                "        t.exitLoop();\n" +
                "        return r;\n" +
                "    }\n";
    }

    private static String getOptionalLoopParser(UnitField field) {
        var resultExpr = getResultExpr(field, false);
        var rule_name = field.getRuleName().symbolicName();
        return "\n    private static void " + rule_name + "_loop(ParseTree t) {\n" +
                "        t.enterLoop();\n" +
                "        while (true) {\n" +
                "            if (!" + resultExpr + ") break;\n" +
                "        }\n" +
                "        t.exitLoop();\n" +
                "    }\n";
    }


    public static void generateRule(StringBuilder sb, NamedRule rule) {
        generateRuleDeclaration(sb, rule.getRoot());
        for (var subRule : rule.getComponents()) {
            generateRuleDeclaration(sb, subRule);
        }
    }

    private static void generateRuleDeclaration(StringBuilder sb, UnitRule rule) {
        var small_name = rule.getRuleName().symbolicName();
        var cap_name = small_name.toUpperCase();
        sb.append("    public static final ParserRule ")
                .append(cap_name)
                .append(" = ")
                .append(rule.getRuleType() == RuleType.Conjunction ? "and_rule" :
                        rule.isLeftRecursive() ? "leftrec_rule" : "or_rule")
                .append("(\"")
                .append(rule.getRuleName().fullName())
                .append("\");\n");
    }

    public static void generateVisitor(StringBuilder sb, NamedRule rule) {
        generateVisitor(sb, rule.getRoot());
    }

    private static void generateVisitor(StringBuilder sb, UnitRule rule) {
        sb.append("\n");
        var headerComments = rule.getGrammarString();
        if (headerComments != null && !headerComments.isBlank()) {
            sb.append(StringUtil.javadoc(headerComments, 4));
        }
        var name = rule.getRuleName();
        sb.append("    default T visit")
                .append(name.pascalCase())
                .append("(")
                .append(name.pascalCase())
                .append(" ")
                .append(name.camelCase())
                .append(") {\n        return null;\n    }\n");
    }

    public static String generateWrapper(NamedRule rule, String wrapperPackage) {
        return generateClassCode(rule, wrapperPackage);
    }

    private static String generateClassCode(NamedRule rule, String wrapperPackage) {
        StringBuilder sb = new StringBuilder();

        sb
                .append("package ")
                .append(wrapperPackage)
                .append(";\n\n");

        Set<String> userImports = new TreeSet<>();
        Set<String> javaImports = new TreeSet<>();

        var importIterator = rule.getComponents().stream().map(JTransform::classImports).iterator();

        for (Set<String> importSet : FirstAndMore.of(classImports(rule.getRoot()), importIterator)) {
            for (String theImport : importSet) {
                if (theImport.startsWith("java")) {
                    javaImports.add(theImport);
                } else {
                    userImports.add(theImport);
                }
            }
        }

        for (String classImport : userImports) {
            sb.append("import ")
                    .append(classImport)
                    .append(";\n");
        }

        if (!userImports.isEmpty()) {
            sb.append("\n");
        }

        for (String classImport : javaImports) {
            sb.append("import ")
                    .append(classImport)
                    .append(";\n");
        }

        if (!javaImports.isEmpty()) {
            sb.append("\n");
        }

        sb.append(generateClassBody(rule.getRoot(), false));

        for (UnitRule subeRule : rule.getComponents()) {
            var classDef = generateClassBody(subeRule, true);
            sb.append(StringUtil.indent(classDef, 4));
        }

        // add final closing bracket
        sb.append("}\n");

        return sb.toString();
    }

    private static Set<String> classImports(UnitRule rule) {
        // A hack to get the class imports...
        Set<String> set = new HashSet<>();
        set.add("org.fugalang.core.parser.NodeWrapper");
        set.add("org.fugalang.core.parser.ParseTreeNode");
        if (rule.getFields().stream().anyMatch(f -> f.getResultSource().kind() == SourceKind.TokenType)) {
            set.add("org.fugalang.core.token.TokenType");
        }
        if (rule.getFields().stream().anyMatch(f ->
                f.getFieldType() == RequiredList || f.getFieldType() == OptionalList)) {
            set.add("java.util.List");
        }
        return set;
    }

    public static String generateClassBody(UnitRule rule, boolean isStaticInnerClass) {
        StringBuilder sb = new StringBuilder();

        if (isStaticInnerClass) {
            sb.append("\n");
        }

        var headerComments = rule.getGrammarString();
        if (headerComments != null && !headerComments.isBlank()) {
            sb.append(StringUtil.javadoc(headerComments, 0));
        }

        if (isStaticInnerClass) {
            sb.append("public static final class ");
        } else {
            sb.append("public final class ");
        }

        var className = rule.getRuleName().pascalCase();
        sb.append(className);

        // extends
        var ruleType = rule.getRuleType();
        if (ruleType != null) {
            // add the parent classes
            sb.append(" extends NodeWrapper");
        } else {
            throw new IllegalStateException("No Rule Type");
        }

        sb.append(" {\n\n");

        sb.append("    public ")
                .append(className);

        sb.append("""
                (ParseTreeNode node) {
                        super(node);
                    }
                """);

        for (int i = 0; i < rule.getFields().size(); i++) {
            var field = rule.getFields().get(i);

            var getter = asGetter(field, ruleType, i);
            if (getter != null) {
                sb.append(getter);
            }

            var absentCheck = asAbsentCheck(field, ruleType, i);
            if (absentCheck != null) {
                sb.append(absentCheck);
            }
        }

        if (isStaticInnerClass) {
            sb.append("}\n");
        }
        // else don't append, since external classes need
        // to add the final quotes

        return sb.toString();
    }

    private static String asGetter(UnitField field, RuleType ruleType, int index) {
        var body = asGetterBody(field, ruleType, index);
        if (body == null) {
            return null;
        }
        var f = StringUtil.decap(StringUtil.convertCase(field.getProperFieldName()));
        return "\n    public " + getFieldTypeName(field) + " " + f + "() {\n" +
                body +
                "    }\n";
    }

    private static String getFieldTypeName(UnitField field) {
        String source;
        if (field.getFieldType() == RequiredList || field.getFieldType() == OptionalList) {
            source = "List<" + switch (field.getResultSource().kind()) {
                case UnitRule -> field.getRuleName().pascalCase();
                case TokenType -> "String";
                case TokenLiteral -> "Boolean";
            } + ">";
        } else {
            source = switch (field.getResultSource().kind()) {
                case UnitRule -> field.getRuleName().pascalCase();
                case TokenType -> "String";
                case TokenLiteral -> "boolean";
            };
        }
        return source;
    }

    private static String asGetterBody(UnitField field, RuleType ruleType, int index) {
        if (field.isPredicate()) {
            return null;
        }
        var resultSource = field.getResultSource();
        switch (resultSource.kind()) {
            case UnitRule -> {
                var className = field.getRuleName();
                if (field.isSingular()) {
                    return "        return new " + className.pascalCase() + "(get(" + index + "));\n";
                }
                return "        return getList(" + index + ", " + className.pascalCase() + "::new);\n";
            }
            case TokenType -> {
                if (field.isSingular()) {
                    var type = ((TokenEntry) resultSource.value()).literalValue();
                    return "        return get(" + index + ", TokenType." + type + ");\n";
                }
                return "        return getList(" + index + ", ParseTreeNode::asString);\n";
            }
            case TokenLiteral -> {
                if (ruleType == RuleType.Conjunction && field.getFieldType() == Required) {
                    return null;
                }
                if (field.isSingular()) {
                    return "        return is(" + index + ");\n";
                }
                return "        return getList(" + index + ", ParseTreeNode::asBoolean);\n";
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private static String asAbsentCheck(UnitField field, RuleType ruleType, int index) {
        var body = absentCheckBody(field, ruleType, index);
        if (body == null) {
            return null;
        }
        var f = StringUtil.convertCase(field.getProperFieldName());
        return "\n    public boolean has" + f +
                "() {\n" + body + "    }\n";
    }

    private static String absentCheckBody(UnitField field, RuleType ruleType, int index) {
        var resultSource = field.getResultSource();
        switch (resultSource.kind()) {
            case UnitRule:
            case TokenType:
                if (ruleType == RuleType.Conjunction && field.getFieldType() == Required) {
                    return null;
                }
                if (field.isSingular()) {
                    return "        return has(" + index + ");\n";
                }
                return null;
            case TokenLiteral:
                return null;
            default:
                throw new IllegalArgumentException();
        }
    }
}
