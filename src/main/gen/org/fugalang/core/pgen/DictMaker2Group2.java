package org.fugalang.core.pgen;

// (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [',']
public class DictMaker2Group2 {
    public final DictMaker2Group21Group dictMaker2Group21Group;
    public final boolean isTokenComma;

    public DictMaker2Group2(
            DictMaker2Group21Group dictMaker2Group21Group,
            boolean isTokenComma
    ) {
        this.dictMaker2Group21Group = dictMaker2Group21Group;
        this.isTokenComma = isTokenComma;
    }
}
