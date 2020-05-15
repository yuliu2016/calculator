package org.fugalang.core.parser.simple;

import java.util.List;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class Term {
    Atom first_atom;

    List<String> ops;
    List<Atom> atoms;

    public Term(Atom first_atom, List<String> ops, List<Atom> atoms) {
        this.first_atom = first_atom;
        this.ops = ops;
        this.atoms = atoms;
    }

    private void validate() {
        assert first_atom != null;
        assert ops.size() == atoms.size();
    }

    public List<String> getOps() {
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
            sb.append(ops.get(i));
            sb.append(" ");
            sb.append(atoms.get(i));
        }

        sb.append(")");

        return sb.toString();
    }
}
