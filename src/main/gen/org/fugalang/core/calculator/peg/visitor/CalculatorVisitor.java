package org.fugalang.core.calculator.peg.visitor;

import org.fugalang.core.calculator.peg.wrapper.*;

@SuppressWarnings("unused")
public interface CalculatorVisitor<T> {

    /**
     * sum: sum '+' term | sum '-' term | term
     */
    default T visitSum(Sum sum) {
        return null;
    }

    /**
     * term: term '*' factor | term '/' factor | term '%' factor | factor
     */
    default T visitTerm(Term term) {
        return null;
    }

    /**
     * factor: '+' factor | '-' factor | '~' factor | power
     */
    default T visitFactor(Factor factor) {
        return null;
    }

    /**
     * power: atom '**' factor | atom
     */
    default T visitPower(Power power) {
        return null;
    }

    /**
     * atom: '(' sum ')' | NAME '(' [parameters] ')' | NAME | NUMBER
     */
    default T visitAtom(Atom atom) {
        return null;
    }

    /**
     * parameters: ','.sum+ [',']
     */
    default T visitParameters(Parameters parameters) {
        return null;
    }
}
