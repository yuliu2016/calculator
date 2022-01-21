package org.fugalang.grammar.common;

import java.util.List;
import java.util.Map;

public record GrammarSpec(
        List<NamedDirective> directives,
        List<NamedRule> namedRules,
        Map<String, TokenEntry> tokenMap
) {
}
