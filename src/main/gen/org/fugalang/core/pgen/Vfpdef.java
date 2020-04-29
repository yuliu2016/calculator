package org.fugalang.core.pgen;

// vfpdef: 'NAME'
public class Vfpdef {
    private final Object name;

    public Vfpdef(
            Object name
    ) {
        this.name = name;
    }

    public Object getName() {
        return name;
    }
}
