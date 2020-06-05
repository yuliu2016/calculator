package org.fugalang.grammar.peg.visitor;

import org.fugalang.grammar.peg.*;

@SuppressWarnings("unused")
public interface MetaVisitor<T> {

    /**
     * rules: [NEWLINE] single_rule+
     */
    default T visitRules(Rules rules) {
        return null;
    }

    /**
     * single_rule: NAME ':' [NEWLINE '|'] or_rule NEWLINE
     */
    default T visitSingleRule(SingleRule singleRule) {
        return null;
    }

    /**
     * or_rule: and_rule ([NEWLINE] '|' and_rule)*
     */
    default T visitOrRule(OrRule orRule) {
        return null;
    }

    /**
     * and_rule: repeat+
     */
    default T visitAndRule(AndRule andRule) {
        return null;
    }

    /**
     * repeat: delimited | item '*' | item '+' | item
     */
    default T visitRepeat(Repeat repeat) {
        return null;
    }

    /**
     * item: group | optional | NAME | STRING
     */
    default T visitItem(Item item) {
        return null;
    }

    /**
     * group: '(' or_rule ')'
     */
    default T visitGroup(Group group) {
        return null;
    }

    /**
     * optional: '[' or_rule ']'
     */
    default T visitOptional(Optional optional) {
        return null;
    }

    /**
     * delimited: STRING '.' item '+'
     */
    default T visitDelimited(Delimited delimited) {
        return null;
    }
}
