package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.ParseTree;

import java.util.Optional;

/**
 * import_as_name: 'NAME' ['as' 'NAME']
 */
public final class ImportAsName extends ConjunctionRule {
    public static final String RULE_NAME = "import_as_name";

    private final String name;
    private final ImportAsName2 importAsName2;

    public ImportAsName(
            String name,
            ImportAsName2 importAsName2
    ) {
        this.name = name;
        this.importAsName2 = importAsName2;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("name", name);
        addOptional("importAsName2", importAsName2);
    }

    public String name() {
        return name;
    }

    public Optional<ImportAsName2> importAsName2() {
        return Optional.ofNullable(importAsName2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenType("NAME");
        ImportAsName2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'as' 'NAME'
     */
    public static final class ImportAsName2 extends ConjunctionRule {
        public static final String RULE_NAME = "import_as_name:2";

        private final boolean isTokenAs;
        private final String name;

        public ImportAsName2(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenAs", isTokenAs);
            addRequired("name", name);
        }

        public boolean isTokenAs() {
            return isTokenAs;
        }

        public String name() {
            return name;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("as");
            result = result && parseTree.consumeTokenType("NAME");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
