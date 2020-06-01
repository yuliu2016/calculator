package org.fugalang.core.object;

import java.util.Arrays;

public class FTuple extends FObject {

    private static final FObject NI = FConst.NotImplemented;

    FObject[] f_tuple;

    public static FTuple of(FObject... items) {
        return new FTuple(items);
    }

    public FTuple(FObject... f_tuple) {
        this.f_tuple = f_tuple;
    }

    public int length() {
        return f_tuple.length;
    }

    public FObject get(int index) {
        return f_tuple[index];
    }

    @Override
    public FObject __repr__() {
        var sb = new StringBuilder();
        sb.append("(");
        for (FObject o : f_tuple) {
            sb.append(o.toString());
        }
        sb.append("(");
        return FString.of(sb.toString());
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
        return FLong.of(Arrays.hashCode(f_tuple));
    }

    @Override
    public FObject __bool__() {
        return f_tuple.length > 0 ? FConst.True : FConst.False;
    }

    @Override
    public FObject __compare__(FObject o) {
        return NI;
    }

    @Override
    public FObject __lt__(FObject o) {
        if (!(o instanceof FTuple)) {
            return FConst.False;
        }
        return super.__lt__(o);
    }

    @Override
    public FObject __le__(FObject o) {
        return super.__le__(o);
    }

    @Override
    public FObject __eq__(FObject o) {
        return super.__eq__(o);
    }

    @Override
    public FObject __ne__(FObject o) {
        return super.__ne__(o);
    }

    @Override
    public FObject __gt__(FObject o) {
        return super.__gt__(o);
    }

    @Override
    public FObject __ge__(FObject o) {
        return super.__ge__(o);
    }

    @Override
    public FObject __getattr__(FObject o) {
        return super.__getattr__(o);
    }

    @Override
    public FObject __setattr__(FObject o) {
        return super.__setattr__(o);
    }

    @Override
    public FObject __delattr__(FObject o) {
        return super.__delattr__(o);
    }

    @Override
    public FObject __dir__(FObject o) {
        return super.__dir__(o);
    }

    @Override
    public FObject __call__(FObject o) {
        return NI;
    }

    @Override
    public FObject __len__() {
        return super.__len__();
    }

    @Override
    public FObject __getitem__(FObject o) {
        return super.__getitem__(o);
    }

    @Override
    public FObject __setitem__(FObject o) {
        return super.__setitem__(o);
    }

    @Override
    public FObject __delitem__(FObject o) {
        return super.__delitem__(o);
    }

    @Override
    public FObject __iter__(FObject o) {
        return super.__iter__(o);
    }

    @Override
    public FObject __reversed__(FObject o) {
        return super.__reversed__(o);
    }

    @Override
    public FObject __contains__(FObject o) {
        return super.__contains__(o);
    }
}
