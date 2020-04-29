package org.fugalang.core.pgen;

// dict_maker: ('expr' ':' 'expr' | '**' 'bitwise_or') ('comp_for' | (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [','])
public class DictMaker {
    public final DictMaker1Group dictMaker1Group;
    public final DictMaker2Group dictMaker2Group;

    public DictMaker(
            DictMaker1Group dictMaker1Group,
            DictMaker2Group dictMaker2Group
    ) {
        this.dictMaker1Group = dictMaker1Group;
        this.dictMaker2Group = dictMaker2Group;
    }
}
