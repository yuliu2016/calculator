package org.fugalang.grammar.gen;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.classbuilder.*;
import org.fugalang.grammar.common.FieldType;
import org.fugalang.grammar.common.Modifier;
import org.fugalang.grammar.util.PEGUtil;
import org.fugalang.grammar.transform.JPackageOutput;
import org.fugalang.grammar.util.StringUtil;
import org.fugalang.grammar.util.GrammarRepr;
import org.fugalang.grammar.peg.wrapper.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class PEGBuilder {

    private final List<Rule> rules;
    private final TokenConverter converter;
    private final Map<String, String> classNameMap = new LinkedHashMap<>();
    private final ClassSet classSet;

    private static final boolean REQUIRED = false;
    private static final boolean OPTIONAL = true;

    public PEGBuilder(
            Grammar grammar,
            TokenConverter converter,
            JPackageOutput packageOutput
    ) {
        this.rules = grammar.rules();
        this.converter = converter;
        classSet = new ClassSet(packageOutput);
    }

    public void generate(boolean toFiles) {
        if (!classSet.getClasses().isEmpty()) {
            throw new IllegalStateException("Cannot repeat generation");
        }

        generateClasses();

        if (toFiles) {
            classSet.writeToFiles();
        }
    }

    public void generateClasses() {
        // do this first because each rule needs to lookup the types of previous rules
        for (var rule : rules) {
            classNameMap.put(rule.name(), StringUtil.convertCase(rule.name()));
        }

        for (var rule : rules) {
            var left_recursive = PEGUtil.checkLeftRecursive(rule, Map.of());

            var realClassName = classNameMap.get(rule.name());
            var className = ClassName.of(realClassName, rule.name());

            // use a root class to reduce files
            ClassBuilder cb = classSet.createRootClass(className, left_recursive);

            var rule_repr = GrammarRepr.INSTANCE.visitRule(rule);
            cb.setHeaderComments(rule_repr);
            cb.setRuleType(RuleType.Disjunction);

            addAltList(className, cb, rule.ruleSuite().altList());

            // protect against not initializing result
            cb.guardMatchEmptyString();

            // for checking invariant state
            classSet.markRootClassDone();
        }
    }

    private void addAltList(
            ClassName className,
            ClassBuilder cb,
            AltList altList
    ) {
        if (altList.alternatives().isEmpty()) {
            // only one rule - can propagate fields of this class
            // but need to change the type here
            cb.setRuleType(RuleType.Conjunction);
            addSequence(className, cb, altList.sequence(), REQUIRED);
        } else {

            // For counting component classes
            int class_count = 1;

            // must create new classes for AND rules that have more than one
            // REPEAT rule
            for (var sequence : PEGUtil.allSequences(altList)) {

                // add class count to the name, even if not used, because
                // otherwise could result in the same class names later,
                // when there are two branches
                var newClassName = className.suffix(class_count);
                class_count++;

                if (sequence.primaries().size() == 1) {
                    // only one repeat rule - can propagate fields of this class
                    addSequence(newClassName, cb, sequence, REQUIRED);
                } else {
                    // need to make a new class for this, because
                    // a list can't hold multiple-ly typed objects
                    var component_cb = classSet.createComponentClass(newClassName);

                    var rule_repr = GrammarRepr.INSTANCE.visitSequence(sequence);
                    component_cb.setHeaderComments(rule_repr);
                    component_cb.setRuleType(RuleType.Conjunction);

                    var smart_name = getSmartName(newClassName, sequence);

                    // Add a field to the class set
                    // The reason to do this first is that if adding the rule fails,
                    // this class can still show that this point was reached
                    addField(newClassName,
                            cb,
                            smart_name,
                            Modifier.Once,
                            REQUIRED,
                            ResultSource.ofClass(newClassName),
                            null);

                    addSequence(newClassName, component_cb, sequence, REQUIRED);
                }
            }
        }
    }

    private void addSequence(
            ClassName className,
            ClassBuilder cb,
            Sequence rule,
            boolean isOptional
    ) {
        if (rule.primaries().size() == 1) {
            addPrimary(className, cb, rule.primaries().get(0), isOptional);
        } else {
            // don't need to check for component classes - every Primary
            // can be on a single field

            // still need to change the names though, since there may be two
            // parallel groups

            // it's also why there's an if condition: if only one rule, there
            // shouldn't be numbering

            int class_count = 1;

            for (var primary : rule.primaries()) {
                var classWithCount = className.suffix(class_count);
                class_count++;

                addPrimary(classWithCount, cb, primary, isOptional);
            }
        }
    }

    private void addPrimary(
            ClassName className,
            ClassBuilder cb,
            Primary primary,
            boolean isOptional
    ) {
        var item = PEGUtil.getModifierItem(primary);
        var modifier = PEGUtil.getModifier(primary);

        var delimiter = primary.hasDelimited() ? primary.delimited().string() : null;

        switch (PEGUtil.getRuleType(item)) {
            case Group -> addAltListAsComponent(className, cb, item.group().altList(),
                    modifier, REQUIRED, delimiter);
            case Optional -> addAltListAsComponent(className, cb,
                    item.optional().altList(), modifier, OPTIONAL, delimiter);
            case Token -> addToken(cb, modifier, PEGUtil.getItemString(item), isOptional, delimiter);
        }
    }

    private void addAltListAsComponent(
            ClassName className,
            ClassBuilder cb,
            AltList rule,
            Modifier modifier,
            boolean isOptional,
            String delimiter
    ) {
        // maybe this can just be added to this class
        // but maybe there needs to be a separate class

        if (rule.alternatives().isEmpty() && rule.sequence().primaries().size() == 1 &&
                modifier == Modifier.Once) {
            // ^fix - single-char repeats

            // just add all the repeat rules and be done with it
            addSequence(className, cb, rule.sequence(), isOptional);
        } else {

            var component_cb = classSet.createComponentClass(className);
            var rule_repr = GrammarRepr.INSTANCE.visitAltList(rule);
            component_cb.setHeaderComments(rule_repr);
            component_cb.setRuleType(RuleType.Disjunction);

            var smart_name = getSmartName(className, rule);

            // Add a field to the class set
            // The reason to do this first is that if adding the rule fails,
            // this class can still show that this point was reached
            addField(className,
                    cb,
                    smart_name,
                    modifier,
                    isOptional,
                    ResultSource.ofClass(className),
                    delimiter);

            addAltList(className, component_cb, rule);
        }
    }

    private void addToken(
            ClassBuilder cb,
            Modifier modifier,
            String token,
            boolean isOptional,
            String delimiter
    ) {

        if (classNameMap.containsKey(token)) {
            // it is referring to another class.

            var classType = classNameMap.get(token);
            var className = ClassName.of(classType, token);

            // fix - need to add repeat rules here
            addField(className,
                    cb,
                    className.decapName(),
                    modifier,
                    isOptional,
                    ResultSource.ofClass(className),
                    delimiter);
        } else {

            // not another class generated by the parser
            // add a token value instead

            var convertedValue = converter.checkToken(token);

            var classType = convertedValue.getClassName();

            // this is used to unambiguously refer to two list references of the same token
            var ruleName = cb.getRuleName() + ":" + convertedValue.getFieldName().toLowerCase();
            var className = ClassName.of(classType, ruleName);

            if (classType.equals("boolean")) {
                var fieldName = StringUtil
                        .prefixCap("is", convertedValue.getFieldName());
                var resultSource = ResultSource.ofTokenLiteral(convertedValue.getSourceLiteral());

                addField(className,
                        cb,
                        fieldName,
                        modifier,
                        isOptional,
                        resultSource,
                        delimiter);
            } else {
                var fieldName = convertedValue.getFieldName();
                cb.addImport(converter.getTokenTypeClass());
                var resultSource = ResultSource.ofTokenType(converter.getTokenTypeShort() +
                        "." + convertedValue.getSourceLiteral());

                addField(className,
                        cb,
                        fieldName,
                        modifier,
                        isOptional,
                        resultSource,
                        delimiter);
            }
        }
    }

    private void addField(
            ClassName className,
            ClassBuilder cb,
            String fieldName,
            Modifier modifier,
            boolean isOptional,
            ResultSource resultSource,
            String delimiter
    ) {
        ClassName newClassName;
        String newFieldName;
        FieldType fieldType;
        switch (modifier) {
            case TestTrue -> {
                newFieldName = fieldName;
                newClassName = className;
                fieldType = FieldType.RequireTrue;
            }
            case TestFalse -> {
                newFieldName = fieldName;
                newClassName = className;
                fieldType = FieldType.RequireFalse;
            }
            case OnceOrMore -> {
                cb.addImport("java.util.List");
                newClassName = className.asList();
                newFieldName = StringUtil.pluralize(fieldName);
                fieldType = FieldType.RequiredList;
            }
            case NoneOrMore -> {
                cb.addImport("java.util.List");
                newClassName = className.asList();
                newFieldName = StringUtil.pluralize(fieldName);
                fieldType = FieldType.OptionalList;
            }
            case Once -> {
                newClassName = className;
                newFieldName = fieldName;
                fieldType = isOptional ? FieldType.Optional : FieldType.Required;
            }
            default -> throw new IllegalArgumentException();
        }

        var field = new ClassField(
                newClassName,
                newFieldName,
                fieldType,
                resultSource,
                delimiter);

        cb.addField(field);
    }

    public String getSmartName(ClassName className, Sequence sequence) {
        var primaries = sequence.primaries();
        if (primaries.size() <= 3 &&
                primaries.stream().allMatch(PEGUtil::isSingle)) {

            StringBuilder sb = null;
            for (var primary : primaries) {
                var itemString = PEGUtil.getItemString(PEGUtil.getModifierItem(primary));
                if (sb == null) sb = new StringBuilder();
                if (StringUtil.isWord(itemString)) {
                    sb.append(StringUtil.convertCase(itemString));
                } else {
                    sb.append(converter.checkToken(itemString).getFieldName());
                }
            }
            if (sb != null) {
                return StringUtil.decap(sb.toString());
            }
            throw new IllegalArgumentException();
        }
        return className.decapName();
    }


    public String getSmartName(ClassName className, AltList altList) {
        var andList = altList.alternatives();
        if (andList.isEmpty()) {
            return getSmartName(className, altList.sequence());
        }
        if (andList.size() == 1) {
            return getSmartName(className, altList.sequence()) +
                    "Or" + StringUtil.capitalizeFirstChar(
                    getSmartName(className, andList.get(0).sequence())
            );
        }
        return className.decapName();
    }

}
