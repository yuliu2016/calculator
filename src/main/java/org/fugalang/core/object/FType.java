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
    Object compare_op(T a, Object o, int compare_op);

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
    Object binary_op(T a, Object o, int binary_op);

    // Number Operators (right hand operand)
    Object rh_binary_op(T a, Object b, int binary_op);

    // Number Operators (in-place)
    Object inplace_binary_op(T a, Object b, int binary_op);

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
