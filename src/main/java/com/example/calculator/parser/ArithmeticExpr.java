package com.example.calculator.parser;

import com.example.calculator.token.Operator;

import java.util.List;

public class ArithmeticExpr {
    Term first_term;

    List<Operator> ops;
    List<Term> terms;

    public ArithmeticExpr(Term first_term, List<Operator> ops, List<Term> terms) {
        this.first_term = first_term;
        this.ops = ops;
        this.terms = terms;
    }

    private void validate() {
        assert first_term != null;
        assert ops.size() == terms.size();
    }

    @Override
    public String toString() {
        validate();

        var sb = new StringBuilder();
        sb.append("(arithmetic_expr ");
        sb.append(first_term);
        for (int i = 0; i < ops.size(); i++) {
            sb.append(" ");
            sb.append(ops.get(i).getCode());
            sb.append(" ");
            sb.append(terms.get(i));
        }

        sb.append(")");

        return sb.toString();
    }
}
