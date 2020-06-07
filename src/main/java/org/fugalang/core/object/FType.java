package org.fugalang.core.object;

@SuppressWarnings("unused")
public interface FType<T> {

    // Basic operators
    Object __repr__(T a);
    Object __str__(T a);
    Object __format__(T a);
    Object __hash__(T a);
    Object __bool__(T a);

    // Comparision Operators
    Object __lt__(T a, Object b);
    Object __le__(T a, Object b);
    Object __eq__(T a, Object b);
    Object __ne__(T a, Object b);
    Object __gt__(T a, Object b);
    Object __ge__(T a, Object b);

    // Customizing attribute acces
    Object __getattr__(T a, Object o);
    Object __setattr__(T a, Object o);
    Object __delattr__(T a, Object o);
    Object __dir__(T a, Object o);

    // calling
    Object __call__(T a, Object o);

    // Sequences
    Object __len__(T a);
    Object __getitem__(T a, Object o);
    Object __setitem__(T a, Object o);
    Object __delitem__(T a, Object o);
    Object __iter__(T a, Object o);
    Object __reversed__(T a, Object o);
    Object __contains__(T a, Object o);

    // Number Operators
    Object __add__(T a, Object o);
    Object __sub__(T a, Object o);
    Object __mul__(T a, Object o);
    Object __matmul__(T a, Object o);
    Object __truediv__(T a, Object o);
    Object __floordiv__(T a, Object o);
    Object __mod__(T a, Object o);
    Object __divmod__(T a, Object o);
    Object __pow__(T a, Object o);
    Object __lshift__(T a, Object o);
    Object __rshift__(T a, Object o);
    Object __and__(T a, Object o);
    Object __xor__(T a, Object o);
    Object __or__(T a, Object o);

    // Number Operators (right hand operand)
    Object __radd__(T a, Object o);
    Object __rsub__(T a, Object o);
    Object __rmul__(T a, Object o);
    Object __rmatmul__(T a, Object o);
    Object __rtruediv__(T a, Object o);
    Object __rfloordiv__(T a, Object o);
    Object __rmod__(T a, Object o);
    Object __rdivmod__(T a, Object o);
    Object __rpow__(T a, Object o);
    Object __rlshift__(T a, Object o);
    Object __rrshift__(T a, Object o);
    Object __rand__(T a, Object o);
    Object __rxor__(T a, Object o);
    Object __ror__(T a, Object o);

    // Number Operators (in-place)
    Object __iadd__(T a, Object o);
    Object __isub__(T a, Object o);
    Object __imul__(T a, Object o);
    Object __imatmul__(T a, Object o);
    Object __itruediv__(T a, Object o);
    Object __ifloordiv__(T a, Object o);
    Object __imod__(T a, Object o);
    Object __idivmod__(T a, Object o);
    Object __ipow__(T a, Object o);
    Object __ilshift__(T a, Object o);
    Object __irshift__(T a, Object o);
    Object __iand__(T a, Object o);
    Object __ixor__(T a, Object o);
    Object __ior__(T a, Object o);

    // Unary operators
    Object __neg__(T a);
    Object __pos__(T a);
    Object __abs__(T a);
    Object __invert__(T a);

    // Number functions
    Object __int__(T a);
    Object __float__(T a);
    Object __round__(T a);
    Object __trunk__(T a);
    Object __floor__(T a);
    Object __ceil__(T a);

    // Context managers
    Object __enter__(T a, Object o);
    Object __exit__(T a, Object o);
}
