package org.fugalang.core.pgen;

// except_clause: 'except' ['expr' ['as' 'NAME']]
public class ExceptClause {
    public final boolean isTokenExcept;
    public final ExceptClause2Group exceptClause2Group;

    public ExceptClause(
            boolean isTokenExcept,
            ExceptClause2Group exceptClause2Group
    ) {
        this.isTokenExcept = isTokenExcept;
        this.exceptClause2Group = exceptClause2Group;
    }
}
