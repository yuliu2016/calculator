package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// simple_atom: 'NAME' | 'NUMBER' | 'STRING' | 'None' | 'True' | 'False'
public final class SimpleAtom extends DisjunctionRule {
    private final String name;
    private final Object number;
    private final String string;
    private final boolean isTokenNone;
    private final boolean isTokenTrue;
    private final boolean isTokenFalse;

    public SimpleAtom(
            String name,
            Object number,
            String string,
            boolean isTokenNone,
            boolean isTokenTrue,
            boolean isTokenFalse
    ) {
        this.name = name;
        this.number = number;
        this.string = string;
        this.isTokenNone = isTokenNone;
        this.isTokenTrue = isTokenTrue;
        this.isTokenFalse = isTokenFalse;

        addChoice("name", name);
        addChoice("number", number);
        addChoice("string", string);
        addChoice("isTokenNone", isTokenNone);
        addChoice("isTokenTrue", isTokenTrue);
        addChoice("isTokenFalse", isTokenFalse);
    }

    public String name() {
        return name;
    }

    public Object number() {
        return number;
    }

    public String string() {
        return string;
    }

    public boolean isTokenNone() {
        return isTokenNone;
    }

    public boolean isTokenTrue() {
        return isTokenTrue;
    }

    public boolean isTokenFalse() {
        return isTokenFalse;
    }
}
