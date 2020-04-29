package org.fugalang.core.pgen;

// argument: 'NAME' ['comp_for'] | 'NAME' ':=' 'expr' | 'NAME' '=' 'expr' | '**' 'expr' | '*' 'expr'
public class Argument {
    public final Argument1 argument1;
    public final Argument2 argument2;
    public final Argument3 argument3;
    public final Argument4 argument4;
    public final Argument5 argument5;

    public Argument(
            Argument1 argument1,
            Argument2 argument2,
            Argument3 argument3,
            Argument4 argument4,
            Argument5 argument5
    ) {
        this.argument1 = argument1;
        this.argument2 = argument2;
        this.argument3 = argument3;
        this.argument4 = argument4;
        this.argument5 = argument5;
    }
}
