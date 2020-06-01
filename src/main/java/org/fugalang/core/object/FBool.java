package org.fugalang.core.object;

import java.math.BigInteger;

public class FBool extends FLong {
    FBool(BigInteger bigInteger) {
        super(bigInteger);
    }

    public static class True extends FBool {

        True() {
            super(BigInteger.ONE);
        }

        @Override
        public FObject __str__() {
            return FString.of("True");
        }
    }

    public static class False extends FBool {

        False() {
            super(BigInteger.ZERO);
        }

        @Override
        public FObject __str__() {
            return null;
        }
    }
}
