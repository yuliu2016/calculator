package org.fugalang.grammar.util;

import org.fugalang.grammar.peg.visitor.MetaVisitor;
import org.fugalang.grammar.peg.wrapper.*;

import java.util.StringJoiner;

public class GrammarRepr implements MetaVisitor<String> {

    public static final GrammarRepr INSTANCE = new GrammarRepr();

    private GrammarRepr() {
    }

    public String stringifyAltList(AltList altList, boolean named) {
        var sequenceList = altList.altList2s();
        StringJoiner joiner;

        if (named) {
            joiner = new StringJoiner("\n    | ", "\n    | ", "");
        } else {
            joiner = new StringJoiner(" | ");
        }

        joiner.add(visitSequence(altList.sequence()));

        for (var altList2 : sequenceList) {
            joiner.add(visitSequence(altList2.sequence()));
        }

        return joiner.toString();
    }

    @Override
    public String visitGrammar(Grammar grammar) {
        throw new RuntimeException();
    }

    @Override
    public String visitRule(Rule rule) {
        return rule.name() + ":" +
                stringifyAltList(rule.altList(), true);
    }


    @Override
    public String visitAltList(AltList altList) {
        return stringifyAltList(altList, false);
    }


    @Override
    public String visitSequence(Sequence sequence) {
        StringJoiner joiner = new StringJoiner(" ");
        for (var primary : sequence.primarys()) {
            joiner.add(visitPrimary(primary));
        }
        return joiner.toString();
    }

    @Override
    public String visitPrimary(Primary primary) {
        var item = visitItem(PEGUtil.getModifierItem(primary));
        if (primary.hasDelimited()) return "'" + primary.delimited().string() + "'." + item + "+";
        if (primary.hasBitAndItem()) return "&" + item;
        if (primary.hasNotItem()) return "!" + item;
        if (primary.hasItemTimes()) return item + "*";
        if (primary.hasItemPlus()) return item + "+";
        if (primary.hasItem()) return item;
        throw new IllegalArgumentException();
    }

    @Override
    public String visitItem(Item item) {
        return item.hasGroup() ? visitGroup(item.group()) :
                item.hasOptional() ? visitOptional(item.optional()) :
                        item.hasName() ? item.name() : "'" + item.string() + "'";
    }

    @Override
    public String visitGroup(Group group) {
        return "(" + visitAltList(group.altList()) + ")";
    }

    @Override
    public String visitOptional(Optional optional) {
        return "[" + visitAltList(optional.altList()) + "]";
    }

    @Override
    public String visitDelimited(Delimited delimited) {
        return null;
    }
}
