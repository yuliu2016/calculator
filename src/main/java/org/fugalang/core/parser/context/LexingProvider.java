package org.fugalang.core.parser.context;

import org.fugalang.core.parser.ParserElement;

import java.util.Iterator;

@FunctionalInterface
public interface LexingProvider {

    /**
     * Creates an iterator containing the parsed elements
     *
     * @param context the (text) context to tokenize with
     * @return an iterator of tokens. It does not need to be defined
     * when the context has an error. There should be one token in
     * reserve, so that hasNext() does not need to compute the value.
     * The next value instead should be computed and stored
     * in next(), but not returned until next() is called again.
     * A null value in next() should not be permitted
     */
    Iterator<ParserElement> tokenize(LexingContext context);


    default LazyArrayList<ParserElement> toList(LexingContext context) {
        return new LazyArrayList<>(tokenize(context));
    }
}
