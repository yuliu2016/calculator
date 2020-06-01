package org.fugalang.core.object;

import java.math.BigInteger;


public class FLong extends FObject {

    private static final FObject NI = FConst.NotImplemented;

    public static FLong of(long val) {
        return new FLong(BigInteger.valueOf(val));
    }

    private final BigInteger f_long;

    FLong(BigInteger bigInteger) {
        this.f_long = bigInteger;
    }

    public int getInteger() {
        return f_long.intValueExact();
    }

    @Override
    public FObject __repr__() {
        return FString.of(f_long.toString());
    }

    @Override
    public FObject __str__() {
        return __repr__();
    }

    @Override
    public FObject __format__() {
        return __repr__();
    }

    @Override
    public FObject __hash__() {
        return this;
    }

    @Override
    public FObject __bool__() {
        return f_long.signum() != 0 ? FConst.True : FConst.False;
    }

    @Override
    public FObject __compare__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return FLong.of(f_long.compareTo(((FLong) o).f_long));
    }

    @Override
    public FObject __lt__(FObject o) {
        if (!(o instanceof FLong))
            return FConst.False;
        return f_long.compareTo(((FLong) o).f_long) < 0 ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __le__(FObject o) {
        if (!(o instanceof FLong))
            return FConst.False;
        return f_long.compareTo(((FLong) o).f_long) <= 0 ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __eq__(FObject o) {
        if (!(o instanceof FLong))
            return FConst.False;
        return f_long.compareTo(((FLong) o).f_long) == 0 ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __ne__(FObject o) {
        if (!(o instanceof FLong))
            return FConst.False;
        return f_long.compareTo(((FLong) o).f_long) != 0 ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __gt__(FObject o) {
        if (!(o instanceof FLong))
            return FConst.False;
        return f_long.compareTo(((FLong) o).f_long) > 0 ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __ge__(FObject o) {
        if (!(o instanceof FLong))
            return FConst.False;
        return f_long.compareTo(((FLong) o).f_long) >= 0 ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __add__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.add(((FLong) o).f_long));
    }

    @Override
    public FObject __sub__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.subtract(((FLong) o).f_long));
    }

    @Override
    public FObject __mul__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.multiply(((FLong) o).f_long));
    }

    @Override
    public FObject __matmul__(FObject o) {
        return NI;
    }

    @Override
    public FObject __truediv__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.divide(((FLong) o).f_long));
    }

    @Override
    public FObject __floordiv__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.divide(((FLong) o).f_long));
    }

    @Override
    public FObject __mod__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.mod(((FLong) o).f_long));
    }

    @Override
    public FObject __divmod__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.divide(((FLong) o).f_long));
    }

    @Override
    public FObject __pow__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.subtract(((FLong) o).f_long));
    }

    @Override
    public FObject __lshift__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.shiftLeft(((FLong) o).f_long.intValueExact()));
    }

    @Override
    public FObject __rshift__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.shiftRight(((FLong) o).f_long.intValueExact()));
    }

    @Override
    public FObject __and__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.and(((FLong) o).f_long));
    }

    @Override
    public FObject __xor__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.xor(((FLong) o).f_long));
    }

    @Override
    public FObject __or__(FObject o) {
        if (!(o instanceof FLong))
            return NI;
        return new FLong(f_long.or(((FLong) o).f_long));
    }

    @Override
    public FObject __neg__() {
        return new FLong(f_long.negate());
    }

    @Override
    public FObject __pos__() {
        return this;
    }

    @Override
    public FObject __abs__() {
        return new FLong(f_long.abs());
    }

    @Override
    public FObject __invert__() {
        return new FLong(f_long.not());
    }

    @Override
    public FObject __int__() {
        return this;
    }

    @Override
    public FObject __float__() {
        return super.__float__();
    }

    @Override
    public FObject __round__() {
        return this;
    }

    @Override
    public FObject __trunk__() {
        return this;
    }

    @Override
    public FObject __floor__() {
        return this;
    }

    @Override
    public FObject __ceil__() {
        return this;
    }
}
