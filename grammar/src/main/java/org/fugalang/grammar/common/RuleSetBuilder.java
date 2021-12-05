package org.fugalang.grammar.common;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.ResultSource.Kind;
import org.fugalang.grammar.peg.wrapper.*;
import org.fugalang.grammar.util.GrammarRepr;
import org.fugalang.grammar.util.PEGUtil;

import java.util.*;

public class RuleSetBuilder {

    // make arguments clear
    private static final boolean REQUIRED = false;
    private static final boolean OPTIONAL = true;

    private final List<Rule> rules;
    private final Map<String, TokenEntry> tokenMap;
    private final Map<String, RuleName> ruleNameMap = new LinkedHashMap<>();
    private final RuleSet ruleSet;

    private NamedRule currentRule;
    private int ruleIndexCounter = 0;

    public RuleSetBuilder(
            Grammar grammar,
            Map<String, TokenEntry> tokenMap) {
        this.rules = grammar.rules();
        this.tokenMap = tokenMap;
        this.ruleSet = new RuleSet(new ArrayList<>(), tokenMap);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static RuleSet generateRuleSet(Grammar grammar, Map<String, TokenEntry> tokenMap) {
        var builder = new RuleSetBuilder(grammar, tokenMap);
        builder.generateRuleSet();
        return builder.ruleSet;
    }

    private void generateRuleSet() {
        // do this first because each rule needs to lookup the types of previous rules
        for (var rule : rules) {
            var ruleName = rule.name();
            var returnType = rule.hasReturnType() ? rule.returnType().name() : null;
            ruleNameMap.put(ruleName, RuleName.of(ruleName, returnType));
        }

        for (var rule : rules) {
            var args = PEGUtil.extractRuleArgs(rule);
            var leftRecursive = PEGUtil.checkLeftRecursive(rule, args);

            var ruleName = ruleNameMap.get(rule.name());

            var grammarString = GrammarRepr.INSTANCE.visitRule(rule);
            UnitRule unit = createNamedRule(ruleName, leftRecursive, args, grammarString);

            unit.setRuleType(RuleType.Disjunction);

            addAltList(ruleName, unit, rule.ruleSuite().altList());

            // protect against not initializing result
            unit.guardMatchEmptyString();

            // for making sure that we don't accidentally add sub-rules
            namedRuleDone();
        }
    }


    private void addAltList(
            RuleName ruleName,
            UnitRule unit,
            AltList altList
    ) {
        if (altList.alternatives().isEmpty()) {
            // only one rule - can propagate fields of this rule
            // but need to change the type here
            unit.setRuleType(RuleType.Conjunction);
            addSequence(ruleName, unit, altList.sequence(), REQUIRED);
        } else {

            // For counting component sequences
            int sequenceCount = 1;

            // must create new sub rules for AND rules that have more than one
            // REPEAT rule
            for (var sequence : PEGUtil.allSequences(altList)) {

                // add sequence count to the name, even if not used, because
                // otherwise could result in the same rule names later,
                // when there are two branches
                var newRuleName = ruleName.withSuffix(sequenceCount);
                sequenceCount++;

                if (sequence.primaries().size() == 1) {
                    // only one repeat rule - can propagate fields of this unit
                    addSequence(newRuleName, unit, sequence, REQUIRED);
                } else {
                    // need to make a new unit for this, because
                    // a list can't hold multiple-ly typed objects
                    var grammarString = GrammarRepr.INSTANCE.visitSequence(sequence);
                    var subUnit = createUnnamedSubRule(newRuleName, grammarString);

                    subUnit.setRuleType(RuleType.Conjunction);

                    var smartName = PEGUtil.getSmartName(newRuleName, sequence, tokenMap);
                    var fieldName = FieldName.of(smartName);

                    // Add a field to the rule set
                    // The reason to do this first is that if adding the rule fails,
                    // this field can still show that this point was reached
                    addField(newRuleName,
                            unit,
                            fieldName,
                            FieldType.Required,
                            REQUIRED,
                            new ResultSource(Kind.UnitRule, newRuleName),
                            null);

                    addSequence(newRuleName, subUnit, sequence, REQUIRED);
                }
            }
        }
    }

    private void addSequence(
            RuleName ruleName,
            UnitRule unit,
            Sequence sequence,
            boolean isOptional
    ) {
        if (sequence.primaries().size() == 1) {
            addPrimary(ruleName, unit, sequence.primaries().get(0), isOptional);
        } else {
            // don't need to check for subrules - every Primary
            // can be on a single field

            // still need to change the names though, since there may be two
            // parallel groups

            // it's also why there's an if condition: if only one rule, there
            // shouldn't be numbering

            int primaryCount = 1;

            for (var primary : sequence.primaries()) {
                var ruleNameWithCount = ruleName.withSuffix(primaryCount);
                primaryCount++;

                addPrimary(ruleNameWithCount, unit, primary, isOptional);
            }
        }
    }

    private void addPrimary(
            RuleName ruleName,
            UnitRule unit,
            Primary primary,
            boolean isOptional
    ) {
        var item = PEGUtil.getModifierItem(primary);
        var fieldType = PEGUtil.getFieldType(primary);

        if (item.hasGroup()) {
            addAltListAsComponent(ruleName,
                    unit,
                    item.group().altList(),
                    fieldType,
                    REQUIRED);
        } else if (item.hasOptional()) {
            addAltListAsComponent(ruleName,
                    unit,
                    item.optional().altList(),
                    fieldType,
                    OPTIONAL);
        } else {
            addSimplePrimary(unit,
                    fieldType,
                    PEGUtil.getItemString(item),
                    isOptional,
                    PEGUtil.getDelimiter(primary, tokenMap));
        }
    }

    private void addAltListAsComponent(
            RuleName ruleName,
            UnitRule unit,
            AltList altList,
            FieldType fieldType,
            boolean isOptional
    ) {
        // maybe this can just be added to this unit rule
        // but maybe there needs to be a separate sub-rule

        if (altList.alternatives().isEmpty() &&
                altList.sequence().primaries().size() == 1 &&
                fieldType == FieldType.Required) {

            // just add all the repeat rules and be done with it
            addSequence(ruleName, unit, altList.sequence(), isOptional);
        } else {
            var grammarString = GrammarRepr.INSTANCE.visitAltList(altList);
            var subUnit = createUnnamedSubRule(ruleName, grammarString);
            subUnit.setRuleType(RuleType.Disjunction);

            var smartName = PEGUtil.getSmartName(ruleName, altList, tokenMap);
            var fieldName = FieldName.of(smartName);

            // Add a field to the rule set
            // The reason to do this first is that if adding the rule fails,
            // this field can still show that this point was reached
            addField(ruleName,
                    unit,
                    fieldName,
                    fieldType,
                    isOptional,
                    new ResultSource(Kind.UnitRule, ruleName),
                    null);

            addAltList(ruleName, subUnit, altList);
        }
    }

    private void addSimplePrimary(
            UnitRule unit,
            FieldType fieldType,
            String primaryName,
            boolean isOptional,
            TokenEntry delimiter
    ) {

        if (ruleNameMap.containsKey(primaryName)) {
            // it is referring to another named rule.
            var ruleName = ruleNameMap.get(primaryName);

            // fix - need to add repeat rules here
            addField(ruleName,
                    unit,
                    FieldName.of(ruleName.snakeCase()),
                    fieldType,
                    isOptional,
                    new ResultSource(Kind.UnitRule, ruleName),
                    delimiter);
        } else {

            // not another named rule generated by the parser
            // add a token value instead

            var tokenEntry = tokenMap.get(primaryName);
            if (tokenEntry == null) {
                throw new RuntimeException("'" + primaryName + "' is neither a rule or a token");
            }
            var ruleName = unit.ruleName();

            String fieldName;
            ResultSource resultSource;

            if (tokenEntry.isLiteral()) {
                fieldName = "is_" + tokenEntry.snakeCase();
                resultSource = new ResultSource(Kind.TokenLiteral, tokenEntry);
            } else {
                fieldName = tokenEntry.snakeCase();
                resultSource = new ResultSource(Kind.TokenType, tokenEntry);
            }

            addField(ruleName,
                    unit,
                    FieldName.of(fieldName),
                    fieldType,
                    isOptional,
                    resultSource,
                    delimiter);
        }
    }

    private void addField(
            RuleName ruleName,
            UnitRule unit,
            FieldName fieldName,
            FieldType fieldType,
            boolean isOptional,
            ResultSource resultSource,
            TokenEntry delimiter
    ) {

        var newFieldName = switch (fieldType) {
            case RequiredList, OptionalList -> fieldName.pluralize();
            default -> fieldName;
        };

        FieldType newFieldType;
        if (fieldType == FieldType.Required) {
            newFieldType = isOptional ? FieldType.Optional : FieldType.Required;
        } else {
            newFieldType = fieldType;
        }

        var field = new UnitField(
                ruleName,
                newFieldName,
                newFieldType,
                resultSource,
                delimiter);

        unit.addField(field);
    }

    public UnitRule createNamedRule(
            RuleName ruleName,
            boolean leftRecursive,
            Map<String, String> args,
            String grammarString
    ) {
        var dupError = false;
        for (var namedRule : ruleSet.namedRules()) {
            if (namedRule.getRoot().ruleName().compareExact(ruleName)) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            throw new IllegalStateException("Duplicate named rule: " + ruleName);
        }

        var unit = new UnitRule(++ruleIndexCounter,
                ruleName, leftRecursive, grammarString);

        currentRule = new NamedRule(unit, args);
        ruleSet.namedRules().add(currentRule);

        return unit;
    }


    public void namedRuleDone() {
        if (currentRule == null) {
            throw new IllegalStateException("No named rule set");
        }
        currentRule = null;
    }

    public UnitRule createUnnamedSubRule(RuleName ruleName, String grammarString) {
        if (currentRule == null) {
            throw new IllegalStateException("No named rule to add to");
        }

        var current = currentRule;

        var dupError = false;
        for (var builder : current.getComponents()) {
            if (builder.ruleName().compareExact(ruleName)) {
                dupError = true;
                break;
            }
        }

        if (dupError) {
            throw new IllegalStateException("Duplicate inner rule: " + ruleName);
        }

        var unit = new UnitRule(++ruleIndexCounter, ruleName, false, grammarString);

        current.getComponents().add(unit);

        return unit;
    }
}
