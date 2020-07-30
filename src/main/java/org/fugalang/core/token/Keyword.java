package org.fugalang.core.token;

import java.util.List;

public class Keyword {

    public static final List<String> ALL_KEYWORDS = List.of(
            // Functional keywords
            "return", "nonlocal",
            // Condition keywords
            "if", "elif", "else",
            // Boolean comparisons
            "and", "or", "not", "is", "in",
            // Nothing
            "pass",
            // Context keywords
            "as", "from", "import", "with", "async", "await",
            // Loop keywords
            "while", "for", "continue", "break",
            // Exception handling
            "try", "except", "finally", "raise",
            // Other
            "del", "assert",
            // Const
            "None", "True", "False"
    );
}
