package org.fugalang.grammar.cgen;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.gen.TokenConverter;
import org.fugalang.grammar.peg.wrapper.*;
import org.fugalang.grammar.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The C version of {@link org.fugalang.grammar.gen.PEGBuilder}
 */
public class CpegBuilder {

    // make arguments clear
    private static final boolean REQUIRED = false;
    private static final boolean OPTIONAL = true;

    private final List<Rule> rules;
    private final TokenConverter converter;
    private final Map<String, RuleName> ruleNameMap = new LinkedHashMap<>();
    private final RuleSet ruleSet;

    public CpegBuilder(
            Grammar grammar,
            TokenConverter converter) {
        this.rules = grammar.rules();
        this.converter = converter;
        this.ruleSet = new RuleSet();
    }

    public RuleSet generate(boolean toFiles) {
        if (!ruleSet.getNamedRules().isEmpty()) {
            throw new IllegalStateException("Cannot repeat generation");
        }

        generateRuleSet();

        return ruleSet;
    }

    private void generateRuleSet() {
        // do this first because each rule needs to lookup the types of previous rules
        for (var rule : rules) {
            var ruleName = rule.name();
            ruleNameMap.put(ruleName, RuleName.of(ruleName));
        }

        for (var rule : rules) {
            var left_recursive = PEGUtil.isLeftRecursive(rule.name(), rule.altList());

            var ruleName = ruleNameMap.get(rule.name());

            // use a root class to reduce files
            UnitRule unit = ruleSet.createRootClass(ruleName, left_recursive);

            var rule_repr = ReprConstructor.INSTANCE.visitRule(rule);
            unit.setHeaderComments(rule_repr);
            unit.setRuleType(RuleType.Disjunction);

            addAltList(ruleName, unit, rule.altList());

            // protect against not initializing result
            unit.guardMatchEmptyString();

            // for checking invariant state
            ruleSet.markRootClassDone();
        }
    }


    private void addAltList(
            RuleName className,
            UnitRule cb,
            AltList altList
    ) {
        if (altList.altList2s().isEmpty()) {
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

                if (sequence.primarys().size() == 1) {
                    // only one repeat rule - can propagate fields of this class
                    addSequence(newClassName, cb, sequence, REQUIRED);
                } else {
                    // need to make a new class for this, because
                    // a list can't hold multiple-ly typed objects
                    var component_cb = ruleSet.createComponentClass(newClassName);

                    var rule_repr = ReprConstructor.INSTANCE.visitSequence(sequence);
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
            RuleName className,
            UnitRule cb,
            Sequence rule,
            boolean isOptional
    ) {
        if (rule.primarys().size() == 1) {
            addPrimary(className, cb, rule.primarys().get(0), isOptional);
        } else {
            // don't need to check for component classes - every Primary
            // can be on a single field

            // still need to change the names though, since there may be two
            // parallel groups

            // it's also why there's an if condition: if only one rule, there
            // shouldn't be numbering

            int class_count = 1;

            for (var primary : rule.primarys()) {
                var classWithCount = className.suffix(class_count);
                class_count++;

                addPrimary(classWithCount, cb, primary, isOptional);
            }
        }
    }

    private void addPrimary(
            RuleName className,
            UnitRule cb,
            Primary primary,
            boolean isOptional
    ) {
        var item = PEGUtil.getModifierItem(primary);
        var modifier = PEGUtil.getModifier(primary);

        var delimiter = primary.hasDelimited() ? primary.delimited().string() : null;

        switch (PEGUtil.getRuleType(item)) {
            case Group:
                addAltListAsComponent(className, cb, item.group().altList(),
                        modifier, REQUIRED, delimiter);
                break;
            case Optional:
                addAltListAsComponent(className, cb,
                        item.optional().altList(), modifier, OPTIONAL, delimiter);
                break;
            case Token:
                addToken(cb, modifier, PEGUtil.getItemString(item), isOptional, delimiter);
                break;
        }
    }

    private void addAltListAsComponent(
            RuleName className,
            UnitRule cb,
            AltList rule,
            Modifier modifier,
            boolean isOptional,
            String delimiter
    ) {
        // maybe this can just be added to this class
        // but maybe there needs to be a separate class

        if (rule.altList2s().isEmpty() && rule.sequence().primarys().size() == 1 &&
                modifier == Modifier.Once) {
            // ^fix - single-char repeats

            // just add all the repeat rules and be done with it
            addSequence(className, cb, rule.sequence(), isOptional);
        } else {

            var component_cb = ruleSet.createComponentClass(className);
            var rule_repr = ReprConstructor.INSTANCE.visitAltList(rule);
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
            UnitRule cb,
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
            RuleName className,
            UnitRule cb,
            String fieldName,
            Modifier modifier,
            boolean isOptional,
            ResultSource resultSource,
            String delimiter
    ) {
        RuleName newClassName;
        String newFieldName;
        FieldType fieldType;
        switch (modifier) {
            case TestTrue:
                newFieldName = fieldName;
                newClassName = className;
                fieldType = FieldType.RequireTrue;
                break;
            case TestFalse:
                newFieldName = fieldName;
                newClassName = className;
                fieldType = FieldType.RequireFalse;
                break;
            case OnceOrMore:
                cb.addImport("java.util.List");
                newClassName = className.asSequence();
                newFieldName = fieldName + "s";
                fieldType = FieldType.RequiredList;
                break;
            case NoneOrMore:
                cb.addImport("java.util.List");
                newClassName = className.asSequence();
                newFieldName = fieldName + "s";
                fieldType = FieldType.OptionalList;
                break;
            case Once:
                newClassName = className;
                newFieldName = fieldName;
                fieldType = isOptional ? FieldType.Optional : FieldType.Required;
                break;
            default:
                throw new IllegalArgumentException();
        }

        var field = new ClassField(
                newClassName,
                newFieldName,
                fieldType,
                resultSource,
                delimiter);

        cb.addField(field);
    }

    public String getSmartName(RuleName className, Sequence sequence) {
        var primaries = sequence.primarys();
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
        return className.getCamelCase();
    }


    public String getSmartName(RuleName className, AltList altList) {
        var andList = altList.altList2s();
        if (andList.isEmpty()) {
            return getSmartName(className, altList.sequence());
        }
        if (andList.size() == 1) {
            return getSmartName(className, altList.sequence()) +
                    "Or" + StringUtil.capitalizeFirstChar(
                    getSmartName(className, andList.get(0).sequence())
            );
        }
        return className.getCamelCase();
    }
}
