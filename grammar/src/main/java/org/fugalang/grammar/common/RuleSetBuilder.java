package org.fugalang.grammar.common;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.ResultSource.Kind;
import org.fugalang.grammar.peg.wrapper.*;
import org.fugalang.grammar.util.GrammarRepr;
import org.fugalang.grammar.util.PEGUtil;

import java.util.*;

public class RuleSetBuilder {

    private static final boolean REQUIRED = false;
    private static final boolean OPTIONAL = true;

    private final List<Rule> rules;
    private final Map<String, TokenEntry> tokenMap;
    private final Map<String, RuleName> ruleNameMap = new LinkedHashMap<>();
    private final RuleSet ruleSet;

    private NamedRule currentNamedRule;
    private int ruleCounter = 0;

    public RuleSetBuilder(
            Grammar grammar,
            Map<String, TokenEntry> tokenMap) {
        this.rules = grammar.rules();
        this.tokenMap = tokenMap;
        this.ruleSet = new RuleSet(new ArrayList<>(), tokenMap);
    }

    public static RuleSet generateRuleSet(Grammar grammar, Map<String, TokenEntry> tokenMap) {
        var builder = new RuleSetBuilder(grammar, tokenMap);
        builder.generateRuleSet();
        return builder.ruleSet;
    }

    private void generateRuleSet() {
        // Each rule needs to be able to find the names of all rules
        for (Rule rule : rules) {
            ruleNameMap.put(rule.name(), PEGUtil.getRuleName(rule));
        }

        for (Rule rule : rules) {
            var args = PEGUtil.extractRuleArgs(rule);
            var leftRecursive = PEGUtil.checkLeftRecursive(rule, args);

            var ruleName = ruleNameMap.get(rule.name());

            var grammarString = GrammarRepr.INSTANCE.visitRule(rule);
            UnitRule unit = createNamedRule(ruleName, leftRecursive, args, grammarString);
            unit.setRuleType(RuleType.Disjunction);

            addAltList(ruleName, unit, rule.ruleSuite().altList());

            // Protect against not initializing result
            unit.guardMatchEmptyString();
            // Make sure that we don't accidentally add sub-rules
            currentNamedRule = null;
        }
    }


    private void addAltList(RuleName ruleName, UnitRule unit, AltList altList) {
        if (altList.alternatives().isEmpty()) {
            // Only one alternative - the entire rule is a sequence
            unit.setRuleType(RuleType.Conjunction);
            addSequence(ruleName, unit, altList.sequence());
            // todo result clause: goes to the unit rule
        } else {
            // Multiple alternatives, which need numbering to avoid conflicts
            int sequenceCount = 1;

            for (var sequence : PEGUtil.allSequences(altList)) {
                var newRuleName = ruleName.withSuffix(sequenceCount++);

                if (sequence.primaries().size() == 1) {
                    // only one primary - can propagate fields of this unit
                    addPrimary(ruleName, unit, sequence.primaries().get(0), REQUIRED);
                    // todo result clause: goes to the field
                } else {
                    // Need to make a new unit rule to hold the sequence
                    var grammarString = GrammarRepr.INSTANCE.visitSequence(sequence);
                    var subUnit = createUnnamedRule(newRuleName, grammarString);
                    subUnit.setRuleType(RuleType.Conjunction);

                    var smartName = PEGUtil.getSmartName(newRuleName, sequence, tokenMap);
                    var fieldName = FieldName.of(smartName);

                    addField(newRuleName,
                            unit,
                            fieldName,
                            FieldType.Required,
                            REQUIRED,
                            new ResultSource(Kind.UnitRule, newRuleName),
                            null);
                    // todo result clause: goes to the *new* unit rule

                    addSequence(newRuleName, subUnit, sequence);
                }
            }
        }
    }

    private void addSequence(RuleName ruleName, UnitRule unit, Sequence sequence) {
        if (sequence.primaries().size() == 1) {
            // No numbering required - just add the field
            addPrimary(ruleName, unit, sequence.primaries().get(0), REQUIRED);
        } else {
            // Every Primary can be on a single field
            // Need to add numbering to avoid naming conflicts
            int primaryCount = 1;
            for (var primary : sequence.primaries()) {
                addPrimary(ruleName.withSuffix(primaryCount++), unit, primary, REQUIRED);
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
            var altList = item.group().altList();
            addAltListAsComponent(ruleName, unit, altList, fieldType, REQUIRED);
        } else if (item.hasOptional()) {
            var altList = item.optional().altList();
            addAltListAsComponent(ruleName, unit, altList, fieldType, OPTIONAL);
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
        if (altList.alternatives().isEmpty() &&
                altList.sequence().primaries().size() == 1 &&
                fieldType == FieldType.Required) {
            // Only one (non-special) primary in the altlist - add it directly
            // todo result clause: goes to the field
            addPrimary(ruleName, unit, altList.sequence().primaries().get(0), isOptional);
        } else {
            // Create a separate unit rule for the altlist
            var grammarString = GrammarRepr.INSTANCE.visitAltList(altList);
            var subUnit = createUnnamedRule(ruleName, grammarString);
            subUnit.setRuleType(RuleType.Disjunction);

            var smartName = PEGUtil.getSmartName(ruleName, altList, tokenMap);
            var fieldName = FieldName.of(smartName);

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
            // The primary name is referring to another named rule.
            var ruleName = ruleNameMap.get(primaryName);

            addField(ruleName,
                    unit,
                    FieldName.of(ruleName.snakeCase()),
                    fieldType,
                    isOptional,
                    new ResultSource(Kind.UnitRule, ruleName),
                    delimiter);
        } else {
            // The primary name is referring to a token in the token map

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
        var newFName = switch (fieldType) {
            case RequiredList, OptionalList -> fieldName.pluralize();
            default -> fieldName;
        };

        FieldType newFType;
        if (fieldType == FieldType.Required) {
            newFType = isOptional ? FieldType.Optional : FieldType.Required;
        } else {
            newFType = fieldType;
        }

        var field = new UnitField(ruleName, newFName, newFType, resultSource, delimiter, null);
        unit.addField(field);
    }

    public UnitRule createNamedRule(
            RuleName ruleName,
            boolean leftRecursive,
            Map<String, String> args,
            String grammarString
    ) {
        if (ruleSet.namedRules().stream().anyMatch(nr -> nr.root().ruleName().equals(ruleName))) {
            throw new IllegalStateException("Duplicate named rule: " + ruleName);
        }
        var unit = new UnitRule(++ruleCounter, ruleName, leftRecursive, grammarString);
        currentNamedRule = new NamedRule(unit, new ArrayList<>(), args);
        ruleSet.namedRules().add(currentNamedRule);
        return unit;
    }

    public UnitRule createUnnamedRule(RuleName ruleName, String grammarString) {
        if (currentNamedRule == null) {
            throw new IllegalStateException("No named rule to add to");
        }
        var current = currentNamedRule;
        if (current.components().stream().anyMatch(c -> c.ruleName().equals(ruleName))) {
            throw new IllegalStateException("Duplicate inner rule: " + ruleName);
        }
        var unit = new UnitRule(++ruleCounter, ruleName, false, grammarString);
        current.components().add(unit);
        return unit;
    }
}
