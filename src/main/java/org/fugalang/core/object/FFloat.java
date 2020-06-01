package org.fugalang.core.object;

public class FFloat extends FObject {

    private static final FObject NI = FConst.NotImplemented;

    double f_float;

    public FFloat(double f_float) {
        this.f_float = f_float;
    }

    public double getDouble() {
        return f_float;
    }


    @Override
    public FObject __repr__() {
        return FString.of(String.valueOf(f_float));
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
        return f_float != 0 ? FConst.True : FConst.False;
    }

    @Override
    public FObject __compare__(FObject o) {
        if (!(o instanceof FFloat))
            return NI;
        return FLong.of(Double.compare(f_float, ((FFloat) o).f_float));
    }

    @Override
    public FObject __lt__(FObject o) {
        if (!(o instanceof FFloat))
            return FConst.False;
        return f_float < ((FFloat) o).f_float ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __le__(FObject o) {
        if (!(o instanceof FFloat))
            return FConst.False;
        return f_float < ((FFloat) o).f_float ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __eq__(FObject o) {
        if (!(o instanceof FFloat))
            return FConst.False;
        return f_float < ((FFloat) o).f_float ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __ne__(FObject o) {
        if (!(o instanceof FFloat))
            return FConst.False;
        return f_float < ((FFloat) o).f_float ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __gt__(FObject o) {
        if (!(o instanceof FFloat))
            return FConst.False;
        return f_float < ((FFloat) o).f_float ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __ge__(FObject o) {
        if (!(o instanceof FFloat))
            return FConst.False;
        return f_float < ((FFloat) o).f_float ?
                FConst.True : FConst.False;
    }

    @Override
    public FObject __add__(FObject o) {
        if (!(o instanceof FFloat))
            return NI;
        return new FFloat(f_float + ((FFloat) o).f_float);
    }

    @Override
    public FObject __sub__(FObject o) {
        if (!(o instanceof FFloat))
            return NI;
        return new FFloat(f_float - ((FFloat) o).f_float);
    }

    @Override
    public FObject __mul__(FObject o) {
        if (!(o instanceof FFloat))
            return NI;
        return new FFloat(f_float * ((FFloat) o).f_float);
    }

    @Override
    public FObject __matmul__(FObject o) {
        return NI;
    }

    @Override
    public FObject __truediv__(FObject o) {
        if (!(o instanceof FFloat))
            return NI;
        return new FFloat(f_float / ((FFloat) o).f_float);
    }

    @Override
    public FObject __floordiv__(FObject o) {
        if (!(o instanceof FFloat))
            return NI;
        return new FFloat(Math.floor(f_float / ((FFloat) o).f_float));
    }

    @Override
    public FObject __mod__(FObject o) {
        if (!(o instanceof FFloat))
            return NI;
        return new FFloat(f_float % ((FFloat) o).f_float);
    }

    @Override
    public FObject __divmod__(FObject o) {
        if (!(o instanceof FFloat))
            return NI;
        return new FFloat(f_float + ((FFloat) o).f_float);
    }

    @Override
    public FObject __pow__(FObject o) {
        if (!(o instanceof FFloat))
            return NI;
        return new FFloat(f_float + ((FFloat) o).f_float);
    }

    @Override
    public FObject __lshift__(FObject o) {
        return NI;
    }

    @Override
    public FObject __rshift__(FObject o) {
        return NI;
    }

    @Override
    public FObject __and__(FObject o) {
        return NI;
    }

    @Override
    public FObject __xor__(FObject o) {
        return NI;
    }

    @Override
    public FObject __or__(FObject o) {
        return NI;
    }

    @Override
    public FObject __neg__() {
        return new FFloat(-f_float);
    }

    @Override
    public FObject __pos__() {
        return this;
    }

    @Override
    public FObject __abs__() {
        return new FFloat(Math.abs(f_float));
    }

    @Override
    public FObject __invert__() {
        return NI;
    }

    @Override
    public FObject __int__() {
        return FLong.of((long) f_float);
    }

    @Override
    public FObject __float__() {
        return this;
    }

    @Override
    public FObject __round__() {
        return new FFloat(Math.round(f_float));
    }

    @Override
    public FObject __trunk__() {
        return __int__();
    }

    @Override
    public FObject __floor__() {
        return new FFloat(Math.floor(f_float));
    }

    @Override
    public FObject __ceil__() {
        return new FFloat(Math.ceil(f_float));
    }
}
