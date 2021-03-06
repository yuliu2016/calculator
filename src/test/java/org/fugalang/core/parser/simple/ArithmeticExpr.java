package org.fugalang.core.parser.simple;

import java.util.List;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class ArithmeticExpr {
    Term first_term;

    List<String> ops;
    List<Term> terms;

    public ArithmeticExpr(Term first_term, List<String> ops, List<Term> terms) {
        this.first_term = first_term;
        this.ops = ops;
        this.terms = terms;
    }

    private void validate() {
        assert first_term != null;
        assert ops.size() == terms.size();
    }

    public Term getFirstTerm() {
        return first_term;
    }

    public List<String> getOps() {
        return ops;
    }

    public List<Term> getTerms() {
        return terms;
    }

    @Override
    public String toString() {
        validate();

        var sb = new StringBuilder();
        sb.append("(arithmetic_expr ");
        sb.append(first_term);
        for (int i = 0; i < ops.size(); i++) {
            sb.append(" ");
            sb.append(ops.get(i));
            sb.append(" ");
            sb.append(terms.get(i));
        }

        sb.append(")");

        return sb.toString();
    }
}
