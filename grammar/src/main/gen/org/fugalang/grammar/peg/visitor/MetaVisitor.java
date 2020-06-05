package org.fugalang.grammar.peg.visitor;

import org.fugalang.grammar.peg.*;

@SuppressWarnings("unused")
public interface MetaVisitor<T> {
    T visitRules(Rules rules);

    T visitSingleRule(SingleRule singleRule);

    T visitOrRule(OrRule orRule);

    T visitAndRule(AndRule andRule);

    T visitRepeat(Repeat repeat);

    T visitItem(Item item);

    T visitGroup(Group group);

    T visitOptional(Optional optional);

    T visitDelimited(Delimited delimited);
}
