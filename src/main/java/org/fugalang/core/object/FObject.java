package org.fugalang.core.object;


@SuppressWarnings("unused")
public class FObject {

    private static final FObject NI = FConst.NotImplemented;

    @Override
    public String toString() {
        var repr = __repr__();
        if (repr instanceof FString) {
            return ((FString) repr).getString();
        }
        return super.toString();
    }

    @Override
    public int hashCode() {
        var hash = __hash__();
        if (hash instanceof FLong) {
            return ((FLong) hash).getInteger();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FObject)) return false;
        return FConst.True == __eq__((FObject) obj).__bool__();
    }

    public FObject __repr__() {
        return NI;
    }

    public FObject __str__() {
        return NI;
    }

    public FObject __format__() {
        return NI;
    }

    public FObject __hash__() {
        return NI;
    }

    public FObject __bool__() {
        return NI;
    }

    // Comparision Operators

    public FObject __compare__(FObject o) {
        return NI;
    }

    public FObject __lt__(FObject o) {
        return NI;
    }

    public FObject __le__(FObject o) {
        return NI;
    }

    public FObject __eq__(FObject o) {
        return NI;
    }

    public FObject __ne__(FObject o) {
        return NI;
    }

    public FObject __gt__(FObject o) {
        return NI;
    }

    public FObject __ge__(FObject o) {
        return NI;
    }


    // Customizing attribute acces

    public FObject __getattr__(FObject o) {
        return NI;
    }

    public FObject __setattr__(FObject o) {
        return NI;
    }

    public FObject __delattr__(FObject o) {
        return NI;
    }

    public FObject __dir__(FObject o) {
        return NI;
    }

    // calling

    public FObject __call__(FObject o) {
        return NI;
    }

    // Sequences

    public FObject __len__() {
        return NI;
    }

    public FObject __getitem__(FObject o) {
        return NI;
    }

    public FObject __setitem__(FObject o) {
        return NI;
    }

    public FObject __delitem__(FObject o) {
        return NI;
    }

    public FObject __iter__(FObject o) {
        return NI;
    }

    public FObject __reversed__(FObject o) {
        return NI;
    }

    public FObject __contains__(FObject o) {
        return NI;
    }

    // Number Operators

    public FObject __add__(FObject o) {
        return NI;
    }

    public FObject __sub__(FObject o) {
        return NI;
    }

    public FObject __mul__(FObject o) {
        return NI;
    }

    public FObject __matmul__(FObject o) {
        return NI;
    }

    public FObject __truediv__(FObject o) {
        return NI;
    }

    public FObject __floordiv__(FObject o) {
        return NI;
    }

    public FObject __mod__(FObject o) {
        return NI;
    }

    public FObject __divmod__(FObject o) {
        return NI;
    }

    public FObject __pow__(FObject o) {
        return NI;
    }

    public FObject __lshift__(FObject o) {
        return NI;
    }

    public FObject __rshift__(FObject o) {
        return NI;
    }

    public FObject __and__(FObject o) {
        return NI;
    }

    public FObject __xor__(FObject o) {
        return NI;
    }

    public FObject __or__(FObject o) {
        return NI;
    }

    // Number Operators (right hand operand)

    public FObject __radd__(FObject o) {
        return __add__(o);
    }

    public FObject __rsub__(FObject o) {
        return __sub__(o);
    }

    public FObject __rmul__(FObject o) {
        return __mul__(o);
    }

    public FObject __rmatmul__(FObject o) {
        return __matmul__(o);
    }

    public FObject __rtruediv__(FObject o) {
        return __truediv__(o);
    }

    public FObject __rfloordiv__(FObject o) {
        return __floordiv__(o);
    }

    public FObject __rmod__(FObject o) {
        return __mod__(o);
    }

    public FObject __rdivmod__(FObject o) {
        return __divmod__(o);
    }

    public FObject __rpow__(FObject o) {
        return __pow__(o);
    }

    public FObject __rlshift__(FObject o) {
        return __lshift__(o);
    }

    public FObject __rrshift__(FObject o) {
        return __rshift__(o);
    }

    public FObject __rand__(FObject o) {
        return __and__(o);
    }

    public FObject __rxor__(FObject o) {
        return __xor__(o);
    }

    public FObject __ror__(FObject o) {
        return __or__(o);
    }

    // Number Operators (in-place)

    public FObject __iadd__(FObject o) {
        return __add__(o);
    }

    public FObject __isub__(FObject o) {
        return __sub__(o);
    }

    public FObject __imul__(FObject o) {
        return __mul__(o);
    }

    public FObject __imatmul__(FObject o) {
        return __matmul__(o);
    }

    public FObject __itruediv__(FObject o) {
        return __truediv__(o);
    }

    public FObject __ifloordiv__(FObject o) {
        return __floordiv__(o);
    }

    public FObject __imod__(FObject o) {
        return __mod__(o);
    }

    public FObject __idivmod__(FObject o) {
        return __divmod__(o);
    }

    public FObject __ipow__(FObject o) {
        return __pow__(o);
    }

    public FObject __ilshift__(FObject o) {
        return __lshift__(o);
    }

    public FObject __irshift__(FObject o) {
        return __rshift__(o);
    }

    public FObject __iand__(FObject o) {
        return __and__(o);
    }

    public FObject __ixor__(FObject o) {
        return __xor__(o);
    }

    public FObject __ior__(FObject o) {
        return __or__(o);
    }

    // Unary operators

    public FObject __neg__() {
        return NI;
    }

    public FObject __pos__() {
        return NI;
    }

    public FObject __abs__() {
        return NI;
    }

    public FObject __invert__() {
        return NI;
    }

    // Number functions

    public FObject __int__() {
        return NI;
    }

    public FObject __float__() {
        return NI;
    }

    public FObject __round__() {
        return NI;
    }

    public FObject __trunk__() {
        return NI;
    }

    public FObject __floor__() {
        return NI;
    }

    public FObject __ceil__() {
        return NI;
    }

    // Context managers

    public FObject __enter__(FObject o) {
        return NI;
    }

    public FObject __exit__(FObject o) {
        return NI;
    }
}
