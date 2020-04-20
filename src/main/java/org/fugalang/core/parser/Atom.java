package org.fugalang.core.parser;

public class Atom {
    Object val;

    public Atom(Object val) {
        this.val = val;
    }

    public Object getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "(atom " + val + ")";
    }
}
