package org.fugalang.core.pgen;

// simple_atom: 'NAME' | 'NUMBER' | 'STRING' | 'None' | 'True' | 'False'
public class SimpleAtom {
    public final Object name;
    public final Object number;
    public final Object string;
    public final boolean isTokenNone;
    public final boolean isTokenTrue;
    public final boolean isTokenFalse;

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
    }
}
