package org.fugalang.core.pgen;

import java.util.List;

// (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [',']
public class DictMaker2Group2 {
    public final List<DictMaker2Group21Group> dictMaker2Group21GroupList;
    public final boolean isTokenComma;

    public DictMaker2Group2(
            List<DictMaker2Group21Group> dictMaker2Group21GroupList,
            boolean isTokenComma
    ) {
        this.dictMaker2Group21GroupList = dictMaker2Group21GroupList;
        this.isTokenComma = isTokenComma;
    }
}
