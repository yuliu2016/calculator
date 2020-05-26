package org.fugalang.core.parser;

public interface ParseTree {

    /**
     * Enter a new call frame in the parse tree stack
     *
     * @param level the recursion level
     * @param rule  the rule that is being parsed
     * @return a boolean value containing the result on entering
     * this frame. The value is nonnull if the result of this
     * frame can be predetermined, which can occur in one of two
     * conditions. 1) the {@code level} parameter surpassed a
     * certain recursion limit, or 2) there has been a precomputed
     * value for this frame, due to this function being called
     * previously at the exact same parsing position. If the return
     * value is nonnull, then {@link #exit(boolean)} is not expected
     * to be called. Otherwise, every {@code enter} call must be linked
     * with a call to {@link #exit(boolean)}
     */
    Boolean enter(int level, ParserRule rule);

    /**
     * Pop the current call frame store the result
     *
     * @param success whether this frame has successfully parsed its components
     */
    void exit(boolean success);


    void cache();

    void reset();

    void restore();

    /**
     * Enter a collection subframe
     */
    void enterLoop();

    /**
     * Enter the collection subframe
     */
    void exitLoop();

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

    /**
     * Consume a token of a certain predefined element type.
     * This will only succeed for identical type objects,
     * i.e. (a == b), not (a.equals(b))
     *
     * @param type the type of the token to be tested
     * @return true if the token is consumed at the current position
     */
    boolean consume(ElementType type);

    /**
     * Consume a token of a certain string value. This will only
     * succeed if {@link ElementType#isLiteral()} returns true
     * for the current token, and {@link ParserElement#getValue()}
     * is equal to the literal parameter
     *
     * @param literal the string representing the value of the token
     * @return true if the token is consumed at the current position
     */
    boolean consume(String literal);
}
