package com.example.calculator.parser;

import com.example.calculator.token.Operator;

import java.util.List;

public class Term {
    Atom first_atom;

    List<Operator> ops;
    List<Atom> atoms;

    public Term(Atom first_atom, List<Operator> ops, List<Atom> atoms) {
        this.first_atom = first_atom;
        this.ops = ops;
        this.atoms = atoms;
    }

    private void validate() {
        assert first_atom != null;
        assert ops.size() == atoms.size();
    }

    public List<Operator> getOps() {
        return ops;
    }

    public List<Atom> getAtoms() {
        return atoms;
    }

    public Atom getFirstAtom() {
        return first_atom;
    }

    @Override
    public String toString() {
        validate();

        var sb = new StringBuilder();
        sb.append("(term ");
        sb.append(first_atom);

        for (int i = 0; i < ops.size(); i++) {
            sb.append(" ");
            sb.append(ops.get(i).getCode());
            sb.append(" ");
            sb.append(atoms.get(i));
        }

        sb.append(")");

        return sb.toString();
    }
}
