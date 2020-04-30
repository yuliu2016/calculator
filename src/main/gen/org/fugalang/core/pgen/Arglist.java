package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// arglist: 'argument' (',' 'argument')* [',']
public final class Arglist extends ConjunctionRule {
    public static final String RULE_NAME = "arglist";

    private final Argument argument;
    private final List<Arglist2> arglist2List;
    private final boolean isTokenComma;

    public Arglist(
            Argument argument,
            List<Arglist2> arglist2List,
            boolean isTokenComma
    ) {
        this.argument = argument;
        this.arglist2List = arglist2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("argument", argument);
        addRequired("arglist2List", arglist2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public Argument argument() {
        return argument;
    }

    public List<Arglist2> arglist2List() {
        return arglist2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    // ',' 'argument'
    public static final class Arglist2 extends ConjunctionRule {
        public static final String RULE_NAME = "arglist:2";

        private final boolean isTokenComma;
        private final Argument argument;

        public Arglist2(
                boolean isTokenComma,
                Argument argument
        ) {
            this.isTokenComma = isTokenComma;
            this.argument = argument;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("argument", argument);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Argument argument() {
            return argument;
        }
    }
}
