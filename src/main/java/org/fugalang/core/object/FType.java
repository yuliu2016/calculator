package org.fugalang.core.object;

@SuppressWarnings("unused")
public interface FType<T> {

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

    // Unary Operators
    Object unary_op(T a, int unary_op);

    // Binary Operators
    Object binary_op(T a, Object o, int binary_op);

    // Number Operators (right hand operand)
    Object rh_binary_op(T a, Object b, int binary_op);

    // Binary Operators (in-place)
    Object inplace_binary_op(T a, Object b, int binary_op);

    // Context managers
    Object __enter__(T a, Object o);
    Object __exit__(T a, Object o);
}
