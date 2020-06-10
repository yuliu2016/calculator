package org.fugalang.core.parser;

/**
 * Interface used by a generated PEG parser
 */
public interface ParseTree {

    /**
     * Enter a new call frame in the parse tree stack
     *
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
    Boolean enter(ParserRule rule);

    /**
     * Pop the current call frame store the result. This will also store the
     * resulting tree in the memoization cache, unless it's parent frame
     * is parsing a left-recursive rule, i.e. it has called
     * {@link #cache(boolean)} at least once. This is needed because on
     * {@link #enter(ParserRule)} of the same frame later, it needs
     * to retry the rule, not just pulling it from the cache
     *
     * @param success whether this frame has successfully parsed its components
     */
    void exit(boolean success);

    /**
     * Cache the current parsing result, used to parse left-recursive grammars.
     * The children nodes of the current frame is assigned to a cache field,
     * then emptied, so that the same frame can be re-parsed with new information
     * for the parser.
     *
     * @param success if the cache should indicate success or not. When parsing
     *                left-recursive grammars, this should be false for the
     *                first iteration to "prime" a failure (to avoid infinite
     *                recursion). Subsequent calls should be true (when parsing
     *                is actually successful)
     */
    void cache(boolean success);

    /**
     * Restore the last cached value from {@link #cache(boolean)} back onto
     * the frame. This is needed because there has to be one extra iteration
     * before the parser realises that it has parsed the longest chain of
     * tokens that it could for the left-recursive rule.
     *
     * @param position the position to go back to, as tracked in the call frame
     */
    void restore(int position);

    /**
     * Enter a loop subframe. This must be paired with {@link #exitLoop()} ()}
     */
    void enterLoop();

    /**
     * Exit a loop subframe. This must be paired with {@link #enterLoop()} ()} ()}
     */
    void exitLoop();

    /**
     * @return the current position of the parser. i.e. the index to the stream
     * of tokens that it's currently parsing
     */
    int position();

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

    /**
     * Skip a token of a certain string value. This will only
     * succeed if {@link ElementType#isLiteral()} returns true
     * for the current token, and {@link ParserElement#getValue()}
     * is equal to the literal parameter. This method will test
     * the literal but will not add it to the parse tree if it
     * succeeds
     *
     * @param literal the string representing the value of the token
     * @return true if the token is matched at the current position
     */
    boolean skip(String literal);
}
