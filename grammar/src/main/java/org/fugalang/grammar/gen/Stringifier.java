package org.fugalang.grammar.gen;

import org.fugalang.grammar.peg.visitor.MetaVisitor;
import org.fugalang.grammar.peg.wrapper.*;

import java.util.StringJoiner;

public class Stringifier implements MetaVisitor<String> {

    public static final Stringifier INSTANCE = new Stringifier();

    private Stringifier() {
    }

    public String stringifyOrRule(OrRule orRule, boolean named) {
        var orRuleList = orRule.orRule2s();
        StringJoiner joiner;

        if (named) {
            joiner = new StringJoiner("\n*   | ", "\n*   | ", "");
        } else {
            joiner = new StringJoiner(" | ");
        }

        joiner.add(visitAndRule(orRule.andRule()));

        for (var orRule2 : orRuleList) {
            joiner.add(visitAndRule(orRule2.andRule()));
        }

        return joiner.toString();
    }

    @Override
    public String visitRules(Rules rules) {
        return null;
    }

    @Override
    public String visitSingleRule(SingleRule singleRule) {
        return singleRule.name() + ":" +
                stringifyOrRule(singleRule.orRule(), true);
    }

    @Override
    public String visitOrRule(OrRule orRule) {
        return stringifyOrRule(orRule, false);
    }

    @Override
    public String visitAndRule(AndRule andRule) {
        StringJoiner joiner = new StringJoiner(" ");
        for (Repeat repeat : andRule.repeats()) {
            joiner.add(visitRepeat(repeat));
        }
        return joiner.toString();
    }

    @Override
    public String visitRepeat(Repeat repeat) {
        var item = visitItem(PEGUtil.getRepeatItem(repeat));
        return repeat.hasDelimited() ? "'" + repeat.delimited().string() + "'." + item + "+" :
                repeat.hasItemPlus() ? item + "+" :
                        repeat.hasItemTimes() ? item + "*" :
                                item;
    }

    @Override
    public String visitItem(Item item) {
        return item.hasGroup() ? visitGroup(item.group()) :
                item.hasOptional() ? visitOptional(item.optional()) :
                        item.hasName() ? item.name() : "'" + item.string() + "'";
    }

    @Override
    public String visitGroup(Group group) {
        return "(" + visitOrRule(group.orRule()) + ")";
    }

    @Override
    public String visitOptional(Optional optional) {
        return "[" + visitOrRule(optional.orRule()) + "]";
    }

    @Override
    public String visitDelimited(Delimited delimited) {
        return null;
    }
}
