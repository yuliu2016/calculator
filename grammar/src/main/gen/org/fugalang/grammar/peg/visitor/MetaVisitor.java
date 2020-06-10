package org.fugalang.grammar.peg.visitor;

import org.fugalang.grammar.peg.wrapper.*;

@SuppressWarnings("unused")
public interface MetaVisitor<T> {

    /**
     * grammar:
     * *   | [NEWLINE] rule+
     */
    default T visitGrammar(Grammar grammar) {
        return null;
    }

    /**
     * rule:
     * *   | NAME ':' NEWLINE '|' alt_list NEWLINE
     */
    default T visitRule(Rule rule) {
        return null;
    }

    /**
     * alt_list:
     * *   | sequence ([NEWLINE] '|' sequence)*
     */
    default T visitAltList(AltList altList) {
        return null;
    }

    /**
     * sequence:
     * *   | primary+
     */
    default T visitSequence(Sequence sequence) {
        return null;
    }

    /**
     * primary:
     * *   | delimited
     * *   | item '*'
     * *   | item '+'
     * *   | item
     */
    default T visitPrimary(Primary primary) {
        return null;
    }

    /**
     * item:
     * *   | group
     * *   | optional
     * *   | NAME
     * *   | STRING
     */
    default T visitItem(Item item) {
        return null;
    }

    /**
     * group:
     * *   | '(' alt_list ')'
     */
    default T visitGroup(Group group) {
        return null;
    }

    /**
     * optional:
     * *   | '[' alt_list ']'
     */
    default T visitOptional(Optional optional) {
        return null;
    }

    /**
     * delimited:
     * *   | STRING '.' item '+'
     */
    default T visitDelimited(Delimited delimited) {
        return null;
    }
}
