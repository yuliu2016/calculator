package org.fugalang.grammar.common;

import org.fugalang.core.parser.RuleType;
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

    public RuleSetBuilder(
            Grammar grammar,
            Map<String, TokenEntry> tokenMap) {
        this.rules = grammar.rules();
        this.tokenMap = tokenMap;
        this.ruleSet = new RuleSet(tokenMap);
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
            ruleNameMap.put(ruleName, RuleName.of(ruleName));
        }

        for (var rule : rules) {
            var left_recursive = PEGUtil.isLeftRecursive(rule.name(), rule.ruleSuite().altList());

            Map<String, String> args;
            if (rule.hasRuleArgs()) {
                args = new LinkedHashMap<>();
                for (RuleArg ruleArg : rule.ruleArgs().ruleArgs()) {
                    args.put(ruleArg.name(), ruleArg.hasAssignName() ? ruleArg.assignName().name() : null);
                }
            } else {
                args = Collections.emptyMap();
            }

            var ruleName = ruleNameMap.get(rule.name());

            var grammarString = GrammarRepr.INSTANCE.visitRule(rule);
            UnitRule unit = ruleSet.createNamedRule(ruleName, left_recursive, args, grammarString);

            unit.setRuleType(RuleType.Disjunction);

            addAltList(ruleName, unit, rule.ruleSuite().altList());

            // protect against not initializing result
            unit.guardMatchEmptyString();

            // for making sure that we don't accidentally add sub-rules
            ruleSet.namedRuleDone();
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
                    var subUnit = ruleSet.createUnnamedSubRule(newRuleName, grammarString);

                    subUnit.setRuleType(RuleType.Conjunction);

                    var smartName = PEGUtil.getSmartName(newRuleName, sequence, tokenMap);
                    var fieldName = FieldName.of(smartName);

                    // Add a field to the rule set
                    // The reason to do this first is that if adding the rule fails,
                    // this field can still show that this point was reached
                    addField(newRuleName,
                            unit,
                            fieldName,
                            Modifier.Once,
                            REQUIRED,
                            new ResultSource(SourceKind.UnitRule, newRuleName),
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
        var modifier = PEGUtil.getModifier(primary);

        TokenEntry delimiter;
        if (primary.hasDelimited()) {
            delimiter = tokenMap.get(primary.delimited().string());
            if (!delimiter.isLiteral()) throw new RuntimeException("Delimiter must be literal");
        } else {
            delimiter = null;
        }

        switch (PEGUtil.getRuleType(item)) {
            case Group -> addAltListAsComponent(ruleName, unit, item.group().altList(),
                    modifier, REQUIRED, delimiter);
            case Optional -> addAltListAsComponent(ruleName, unit,
                    item.optional().altList(), modifier, OPTIONAL, delimiter);
            case Token -> addToken(unit, modifier, PEGUtil.getItemString(item), isOptional, delimiter);
        }
    }

    private void addAltListAsComponent(
            RuleName ruleName,
            UnitRule unit,
            AltList altList,
            Modifier modifier,
            boolean isOptional,
            TokenEntry delimiter
    ) {
        // maybe this can just be added to this unit rule
        // but maybe there needs to be a separate sub-rule

        if (altList.alternatives().isEmpty() && altList.sequence().primaries().size() == 1 &&
                modifier == Modifier.Once) {
            // ^fix - single-char repeats

            // just add all the repeat rules and be done with it
            addSequence(ruleName, unit, altList.sequence(), isOptional);
        } else {
            var grammarString = GrammarRepr.INSTANCE.visitAltList(altList);
            var subUnit = ruleSet.createUnnamedSubRule(ruleName, grammarString);
            subUnit.setRuleType(RuleType.Disjunction);

            var smartName = PEGUtil.getSmartName(ruleName, altList, tokenMap);
            var fieldName = FieldName.of(smartName);

            // Add a field to the rule set
            // The reason to do this first is that if adding the rule fails,
            // this field can still show that this point was reached
            addField(ruleName,
                    unit,
                    fieldName,
                    modifier,
                    isOptional,
                    new ResultSource(SourceKind.UnitRule, ruleName),
                    delimiter);

            addAltList(ruleName, subUnit, altList);
        }
    }

    private void addToken(
            UnitRule unit,
            Modifier modifier,
            String token,
            boolean isOptional,
            TokenEntry delimiter
    ) {

        if (ruleNameMap.containsKey(token)) {
            // it is referring to another named rule.
            var ruleName = ruleNameMap.get(token);

            // fix - need to add repeat rules here
            addField(ruleName,
                    unit,
                    FieldName.of(ruleName.snakeCase()),
                    modifier,
                    isOptional,
                    new ResultSource(SourceKind.UnitRule, ruleName),
                    delimiter);
        } else {

            // not another named rule generated by the parser
            // add a token value instead

            var tokenEntry = tokenMap.get(token);
            var ruleName = unit.ruleName();

            String fieldName;
            ResultSource resultSource;

            if (tokenEntry.isLiteral()) {
                fieldName = "is_" + tokenEntry.snakeCase();
                resultSource = new ResultSource(SourceKind.TokenLiteral, tokenEntry);
            } else {
                fieldName = tokenEntry.snakeCase();
                resultSource = new ResultSource(SourceKind.TokenType, tokenEntry);
            }

            addField(ruleName,
                    unit,
                    FieldName.of(fieldName),
                    modifier,
                    isOptional,
                    resultSource,
                    delimiter);
        }
    }

    private void addField(
            RuleName ruleName,
            UnitRule unit,
            FieldName fieldName,
            Modifier modifier,
            boolean isOptional,
            ResultSource resultSource,
            TokenEntry delimiter
    ) {
        RuleName newRuleName;
        FieldName newFieldName;
        FieldType fieldType;
        switch (modifier) {
            case TestTrue -> {
                newFieldName = fieldName;
                newRuleName = ruleName;
                fieldType = FieldType.RequireTrue;
            }
            case TestFalse -> {
                newFieldName = fieldName;
                newRuleName = ruleName;
                fieldType = FieldType.RequireFalse;
            }
            case OnceOrMore -> {
                newRuleName = ruleName.asSequence();
                newFieldName = fieldName.pluralize();
                fieldType = FieldType.RequiredList;
            }
            case NoneOrMore -> {
                newRuleName = ruleName.asSequence();
                newFieldName = fieldName.pluralize();
                fieldType = FieldType.OptionalList;
            }
            case Once -> {
                newRuleName = ruleName;
                newFieldName = fieldName;
                fieldType = isOptional ? FieldType.Optional : FieldType.Required;
            }
            default -> throw new IllegalArgumentException();
        }

        var field = new UnitField(
                newRuleName,
                newFieldName,
                fieldType,
                resultSource,
                delimiter);

        unit.addField(field);
    }
}
