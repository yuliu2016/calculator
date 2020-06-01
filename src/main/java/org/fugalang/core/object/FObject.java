package org.fugalang.core.object;

import static org.fugalang.core.object.FConst.NotImplemented;

@SuppressWarnings("unused")
public class FObject {

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
        return NotImplemented;
    }

    public FObject __str__() {
        return NotImplemented;
    }

    public FObject __format__() {
        return NotImplemented;
    }

    public FObject __hash__() {
        return NotImplemented;
    }

    public FObject __bool__() {
        return NotImplemented;
    }

    // Comparision Operators

    public FObject __compare__(FObject o) {
        return NotImplemented;
    }

    public FObject __lt__(FObject o) {
        return NotImplemented;
    }

    public FObject __le__(FObject o) {
        return NotImplemented;
    }

    public FObject __eq__(FObject o) {
        return NotImplemented;
    }

    public FObject __ne__(FObject o) {
        return NotImplemented;
    }

    public FObject __gt__(FObject o) {
        return NotImplemented;
    }

    public FObject __ge__(FObject o) {
        return NotImplemented;
    }


    // Customizing attribute acces

    public FObject __getattr__(FObject o) {
        return NotImplemented;
    }

    public FObject __setattr__(FObject o) {
        return NotImplemented;
    }

    public FObject __delattr__(FObject o) {
        return NotImplemented;
    }

    public FObject __dir__(FObject o) {
        return NotImplemented;
    }

    // calling

    public FObject __call__(FObject o) {
        return NotImplemented;
    }

    // Sequences

    public FObject __len__() {
        return NotImplemented;
    }

    public FObject __getitem__(FObject o) {
        return NotImplemented;
    }

    public FObject __setitem__(FObject o) {
        return NotImplemented;
    }

    public FObject __delitem__(FObject o) {
        return NotImplemented;
    }

    public FObject __iter__(FObject o) {
        return NotImplemented;
    }

    public FObject __reversed__(FObject o) {
        return NotImplemented;
    }

    public FObject __contains__(FObject o) {
        return NotImplemented;
    }

    // Number Operators

    public FObject __add__(FObject o) {
        return NotImplemented;
    }

    public FObject __sub__(FObject o) {
        return NotImplemented;
    }

    public FObject __mul__(FObject o) {
        return NotImplemented;
    }

    public FObject __matmul__(FObject o) {
        return NotImplemented;
    }

    public FObject __truediv__(FObject o) {
        return NotImplemented;
    }

    public FObject __floordiv__(FObject o) {
        return NotImplemented;
    }

    public FObject __mod__(FObject o) {
        return NotImplemented;
    }

    public FObject __divmod__(FObject o) {
        return NotImplemented;
    }

    public FObject __pow__(FObject o) {
        return NotImplemented;
    }

    public FObject __lshift__(FObject o) {
        return NotImplemented;
    }

    public FObject __rshift__(FObject o) {
        return NotImplemented;
    }

    public FObject __and__(FObject o) {
        return NotImplemented;
    }

    public FObject __xor__(FObject o) {
        return NotImplemented;
    }

    public FObject __or__(FObject o) {
        return NotImplemented;
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
        return NotImplemented;
    }

    public FObject __pos__() {
        return NotImplemented;
    }

    public FObject __abs__() {
        return NotImplemented;
    }

    public FObject __invert__() {
        return NotImplemented;
    }

    // Number functions

    public FObject __int__(FObject o) {
        return NotImplemented;
    }

    public FObject __float__(FObject o) {
        return NotImplemented;
    }

    public FObject __round__(FObject o) {
        return NotImplemented;
    }

    public FObject __trunk__(FObject o) {
        return NotImplemented;
    }

    public FObject __floor__(FObject o) {
        return NotImplemented;
    }

    public FObject __ceil__(FObject o) {
        return NotImplemented;
    }

    // Context managers

    public FObject __enter__(FObject o) {
        return NotImplemented;
    }

    public FObject __exit__(FObject o) {
        return NotImplemented;
    }
}
