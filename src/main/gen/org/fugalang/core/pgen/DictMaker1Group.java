package org.fugalang.core.pgen;

// 'expr' ':' 'expr' | '**' 'bitwise_or'
public class DictMaker1Group {
    public final DictMaker1Group1 dictMaker1Group1;
    public final DictMaker1Group2 dictMaker1Group2;

    public DictMaker1Group(
            DictMaker1Group1 dictMaker1Group1,
            DictMaker1Group2 dictMaker1Group2
    ) {
        this.dictMaker1Group1 = dictMaker1Group1;
        this.dictMaker1Group2 = dictMaker1Group2;
    }
}
