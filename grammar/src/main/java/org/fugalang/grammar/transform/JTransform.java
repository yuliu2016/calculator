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
        generateParsingFunc(sb, rule.root(), true);
        for (var subRule : rule.components()) {
            generateParsingFunc(sb, subRule, false);
        }
    }

    public static void generateParsingFunc(StringBuilder sb, UnitRule rule, boolean isNamedRule) {
        var small_name = rule.ruleName().symbolicName();
        var cap_name = small_name.toUpperCase();
        sb.append("\n");

        // rule name constant

        var headerComments = rule.grammarString();
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

        if (rule.leftRecursive()) {
            if (!isNamedRule) {
                throw new IllegalStateException(
                        "Cannot be left-recursive and not a named rule");
            }
            generateLeftRecursiveBody(sb, rule);
        } else {
            generateNormalParserBody(sb, rule);
        }

        sb.append("    }\n");

        for (UnitField field : rule.fields()) {
            var loopParser = getLoopParser(field);
            if (loopParser != null) {
                sb.append(loopParser);
            }
        }
    }

    private static void generateNormalParserBody(StringBuilder sb, UnitRule rule) {
        sb.append("        boolean r;\n");

        var first = true;
        for (UnitField field : rule.fields()) {
            var result = getParserFieldStatement(field, rule, first);
            if (field.isRequired()) {
                first = false;
            }
            sb.append("        ");
            sb.append(result);
        }

        if (first) {
            throw new IllegalStateException("The rule " + rule.ruleName() +
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
        for (UnitField field : rule.fields()) {
            var result = getParserFieldStatement(field, rule, first);
            if (field.isRequired()) {
                first = false;
            }
            sb.append("            ");
            sb.append(result);
        }

        if (first) {
            throw new IllegalStateException("The rule " + rule.ruleName() +
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
        var ruleType = rule.ruleType();
        return switch (field.fieldType()) {
            case RequireTrue -> getRequiredStmt(getResultExpr(field, true), ruleType, isFirst);
            case RequireFalse -> getRequiredStmt("!" + getResultExpr(field, true), ruleType, isFirst);
            case Required -> getRequiredStmt(getResultExpr(field, false), ruleType, isFirst);
            case Optional -> getOptionalStmt(getResultExpr(field, false), ruleType, isFirst);
            case RequiredList -> getRequiredStmt(getLoopExpr(field), ruleType, isFirst);
            case OptionalList -> getOptionalStmt(getLoopExpr(field), ruleType, isFirst);
        };
    }

    private static String getLoopExpr(UnitField field) {
        var rule_name = field.ruleName().symbolicName();
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
        var resultSource = field.resultSource();
        return switch (resultSource.kind()) {
            case UnitRule -> resultSource.asRuleName().symbolicName() +
                    "(" + parseTreeInst + ")";
            case TokenType -> parseTreeInst + ".consume(TokenType." +
                    resultSource.asTokenEntry().literalValue() + ")";
            case TokenLiteral -> parseTreeInst + ".consume(\"" +
                    resultSource.asTokenEntry().literalValue() + "\")";
        };
    }

    private static String getLoopParser(UnitField field) {
        if (field.isSingular() || field.isPredicate()) {
            return null;
        }
        return field.fieldType() == FieldType.RequiredList ?
                getRequiredLoopParser(field) : getOptionalLoopParser(field);
    }


    private static String getRequiredLoopParser(UnitField field) {
        var resultExpr = getResultExpr(field, false);
        var rule_name = field.ruleName().symbolicName();

        String whileBody;
        TokenEntry delimiter = field.delimiter();
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
        var rule_name = field.ruleName().symbolicName();
        return "\n    private static void " + rule_name + "_loop(ParseTree t) {\n" +
                "        t.enterLoop();\n" +
                "        while (true) {\n" +
                "            if (!" + resultExpr + ") break;\n" +
                "        }\n" +
                "        t.exitLoop();\n" +
                "    }\n";
    }


    public static void generateRule(StringBuilder sb, NamedRule rule) {
        generateRuleDeclaration(sb, rule.root());
        for (var subRule : rule.components()) {
            generateRuleDeclaration(sb, subRule);
        }
    }

    private static void generateRuleDeclaration(StringBuilder sb, UnitRule rule) {
        var small_name = rule.ruleName().symbolicName();
        var cap_name = small_name.toUpperCase();
        sb.append("    public static final ParserRule ")
                .append(cap_name)
                .append(" = ")
                .append(rule.ruleType() == RuleType.Conjunction ? "and_rule" :
                        rule.leftRecursive() ? "leftrec_rule" : "or_rule")
                .append("(\"")
                .append(rule.ruleName().fullName())
                .append("\");\n");
    }

    public static void generateVisitor(StringBuilder sb, NamedRule rule) {
        generateVisitor(sb, rule.root());
    }

    private static void generateVisitor(StringBuilder sb, UnitRule rule) {
        sb.append("\n");
        var headerComments = rule.grammarString();
        if (headerComments != null && !headerComments.isBlank()) {
            sb.append(StringUtil.javadoc(headerComments, 4));
        }
        var name = rule.ruleName();
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

        var importIterator = rule.components().stream().map(JTransform::classImports).iterator();

        for (Set<String> importSet : FirstAndMore.of(classImports(rule.root()), importIterator)) {
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

        sb.append(generateClassBody(rule.root(), false));

        for (UnitRule subeRule : rule.components()) {
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
        if (rule.fields().stream().anyMatch(f -> f.resultSource().isTokenType())) {
            set.add("org.fugalang.core.token.TokenType");
        }
        if (rule.fields().stream().anyMatch(f ->
                f.fieldType() == RequiredList || f.fieldType() == OptionalList)) {
            set.add("java.util.List");
        }
        return set;
    }

    public static String generateClassBody(UnitRule rule, boolean isStaticInnerClass) {
        StringBuilder sb = new StringBuilder();

        if (isStaticInnerClass) {
            sb.append("\n");
        }

        var headerComments = rule.grammarString();
        if (headerComments != null && !headerComments.isBlank()) {
            sb.append(StringUtil.javadoc(headerComments, 0));
        }

        if (isStaticInnerClass) {
            sb.append("public static final class ");
        } else {
            sb.append("public final class ");
        }

        var className = rule.ruleName().pascalCase();
        sb.append(className);

        // extends
        var ruleType = rule.ruleType();
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

        for (int i = 0; i < rule.fields().size(); i++) {
            var field = rule.fields().get(i);

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
        var f = StringUtil.decap(field.fieldName().fullCamelCase());
        return "\n    public " + getFieldTypeName(field) + " " + f + "() {\n" +
                body +
                "    }\n";
    }

    private static String getFieldTypeName(UnitField field) {
        String source;
        if (field.fieldType() == RequiredList || field.fieldType() == OptionalList) {
            source = "List<" + switch (field.resultSource().kind()) {
                case UnitRule -> field.ruleName().pascalCase();
                case TokenType -> "String";
                case TokenLiteral -> "Boolean";
            } + ">";
        } else {
            source = switch (field.resultSource().kind()) {
                case UnitRule -> field.ruleName().pascalCase();
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
        var resultSource = field.resultSource();
        switch (resultSource.kind()) {
            case UnitRule -> {
                var className = field.ruleName();
                if (field.isSingular()) {
                    return "        return new " + className.pascalCase() + "(get(" + index + "));\n";
                }
                return "        return getList(" + index + ", " + className.pascalCase() + "::new);\n";
            }
            case TokenType -> {
                if (field.isSingular()) {
                    var type = resultSource.asTokenEntry().literalValue();
                    return "        return get(" + index + ", TokenType." + type + ");\n";
                }
                return "        return getList(" + index + ", ParseTreeNode::asString);\n";
            }
            case TokenLiteral -> {
                if (ruleType == RuleType.Conjunction && field.fieldType() == Required) {
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
        var f = field.fieldName().fullCamelCase();
        return "\n    public boolean has" + f +
                "() {\n" + body + "    }\n";
    }

    private static String absentCheckBody(UnitField field, RuleType ruleType, int index) {
        var resultSource = field.resultSource();
        switch (resultSource.kind()) {
            case UnitRule:
            case TokenType:
                if (ruleType == RuleType.Conjunction && field.fieldType() == Required) {
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
