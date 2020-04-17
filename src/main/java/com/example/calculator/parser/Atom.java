package com.example.calculator.parser;

public class Atom {
    Object val;

    public Atom(Object val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "(atom " + val + ")";
    }
}
