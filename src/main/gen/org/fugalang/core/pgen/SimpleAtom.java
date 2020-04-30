package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// simple_atom: 'NAME' | 'NUMBER' | 'STRING' | 'None' | 'True' | 'False'
public final class SimpleAtom extends DisjunctionRule {
    private final Object name;
    private final Object number;
    private final Object string;
    private final boolean isTokenNone;
    private final boolean isTokenTrue;
    private final boolean isTokenFalse;

    public SimpleAtom(
            Object name,
            Object number,
            Object string,
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

    public Object getName() {
        return name;
    }

    public Object getNumber() {
        return number;
    }

    public Object getString() {
        return string;
    }

    public boolean getIsTokenNone() {
        return isTokenNone;
    }

    public boolean getIsTokenTrue() {
        return isTokenTrue;
    }

    public boolean getIsTokenFalse() {
        return isTokenFalse;
    }
}
