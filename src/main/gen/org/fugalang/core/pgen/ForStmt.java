package org.fugalang.core.pgen;

// for_stmt: 'for' 'targets' 'in' 'exprlist' 'suite' ['else' 'suite']
public class ForStmt {
    public final boolean isTokenFor;
    public final Targets targets;
    public final boolean isTokenIn;
    public final Exprlist exprlist;
    public final Suite suite;
    public final ForStmt6Group forStmt6Group;

    public ForStmt(
            boolean isTokenFor,
            Targets targets,
            boolean isTokenIn,
            Exprlist exprlist,
            Suite suite,
            ForStmt6Group forStmt6Group
    ) {
        this.isTokenFor = isTokenFor;
        this.targets = targets;
        this.isTokenIn = isTokenIn;
        this.exprlist = exprlist;
        this.suite = suite;
        this.forStmt6Group = forStmt6Group;
    }
}
