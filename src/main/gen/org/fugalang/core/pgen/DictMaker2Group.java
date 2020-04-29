package org.fugalang.core.pgen;

// 'comp_for' | (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [',']
public class DictMaker2Group {
    public final CompFor compFor;
    public final DictMaker2Group2 dictMaker2Group2;

    public DictMaker2Group(
            CompFor compFor,
            DictMaker2Group2 dictMaker2Group2
    ) {
        this.compFor = compFor;
        this.dictMaker2Group2 = dictMaker2Group2;
    }
}
