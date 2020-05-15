package org.fugalang.core.parser;

public interface ParseTree {

    /**
     * Enter a call frame in the parser
     *
     * @param level the recursion level
     * @param rule  the rule that is being parsed
     */
    void enter(int level, ParserRule rule);

    /**
     * Exit the current call frame. This must be called in pairs
     * after enter
     *
     * @param success whether this frame has successfully parsed its components
     */
    void exit(boolean success);

    /**
     * Enter a collection subframe
     */
    void enterCollection();

    /**
     * Enter the collection subframe
     */
    void exitCollection();

    /**
     * Consume a token of a certain type. This will only succeed if
     * the current token's type points to the same object as the
     * parameter
     *
     * @param type the type if the token
     * @return true if the token is consumed at the current position
     */
    boolean consume(ElementType type);

    /**
     * Consume a token of a certain string value. This will only
     * succeed if {@link ElementType#isLiteral()} returns true
     * for the current token.
     *
     * @param literal the string representing the value of the token
     * @return true if the token is consumed at the current position
     */
    boolean consume(String literal);

    /**
     * @return the current position of the parser
     */
    int position();

    /**
     * Guard the loop against an empty string
     *
     * @param position the position before the current iteration
     * @return true if nothing has been parsed and the loop should
     * return an empty string
     */
    boolean loopGuard(int position);
}
