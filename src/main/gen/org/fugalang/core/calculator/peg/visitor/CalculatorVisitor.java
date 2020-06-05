package org.fugalang.core.calculator.peg.visitor;

import org.fugalang.core.calculator.peg.*;

@SuppressWarnings("unused")
public interface CalculatorVisitor<T> {
    T visitSum(Sum sum);

    T visitTerm(Term term);

    T visitFactor(Factor factor);

    T visitPower(Power power);

    T visitAtom(Atom atom);

    T visitParameters(Parameters parameters);
}
