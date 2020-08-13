package org.fugalang.grammar.cgen;

/**
 * The simplest unit of a rule, corresponding to
 * {@link org.fugalang.grammar.classbuilder.ClassBuilder}
 * <p>
 * This means the generated code has at most one pair
 * of ENTER_FRAME/EXIT_FRAME calls.
 * <p>
 * It might have another function to gather loop functions
 */
public class CpegFrame {
    public boolean isLeftRecursive() {
        return false;
    }

    public void generateParsingFunction(StringBuilder sb, boolean isNamedRule) {

    }
}
