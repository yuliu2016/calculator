# Fuga By Examples

### Basic Statements

##### Hello World

```python
print("Hello World")
```

##### Comments

Only single-line comments exist for Fuga, starting with the `#` symbol and ending with newline

```python
# This is a single-line comment
print("Hello World") # This is a comment after some other code
```

##### Variables

Fuga has no command for declaring a variable. Variables are created when a value is assigned to a name. Variables are used to store data, and can be used to replace literal constants like numbers.

```python
x = 5               # type: int
y = "Hello, World!" # type: str
print(x)
print(y)
```

Variables can be assigned to a value of a different type. Their names may include letters, numbers, and underscore (`_`), but may not start with a number and are case-sensitive.

Variable assignment can be combined:

```python
a = x, y, z = "Hello", "World", "Fuga"
```

##### Print Function

The print function writes values to the standard output of the program, and by default adds a newline. It can print values consecutively:

```python
word = "Hello"
print(word, "World")
```

To not add the newline by default:

```python
print(stuff, end="")
```

##### Numbers

Numbers are of type `int`, `float`, or `complex`.

An `int` represents an integer value. It can be arbitrarily long as computer memory allows:

```python
x = 1
y = 35656222554887711
z = -3255522
```

A `float` represents a 64-bit floating point value:

```python
x = 1.10
y = 1.0
z = -35.59
a = 35e3      # e is used for scientific notation
b = 12e4
c = -87.7e100
```

A `complex` has two `float` components - real and imaginary:

```
x = 3+5j
y = 5j
z = -5j
```

##### Strings

A string is a sequence of characters. Strings are made by putting quotes(single/double) around the desired characters. Strings can be concatenated using the `+` operator:

```python
phrase = "Hello " + "World" + "!"
```

Strings can include special escape characters, including:

```python
"\n" # newline
"\t" # tab
"\\" # backslash
```

##### Type Conversions

`int`, `float`, `complex`, and `string` can be used to convert number values between each other:

```python
x = int(1)   # x will be 1
y = int(2.8) # y will be 2
z = int("3") # z will be 3

x = float(1)     # x will be 1.0
y = float(2.8)   # y will be 2.8
z = float("3")   # z will be 3.0
w = float("4.2") # w will be 4.2

x = str("s1") # x will be 's1'
y = str(2)    # y will be '2'
z = str(3.0)  # z will be '3.0'
```



E.g. String concatenation does not work with values that are not strings. To fix, wrap value with `str()`:

```python
s = "Number" + str(3)
```

### Functions

##### Declaring a function

A variable can be assigned to a function. A function is a value that may be called. To define a function, use the `def` keyword followed by curly braces

```python
say_hi = def {
    print("Hello World")
}
say_hi()
```

A function that is only one line could instead be defined using only a colon:

```python
say_hi = def: print("Hello World")
say_hi()
```

