## Homework 4
#### Programming Language Design
#####Chris Fenton
---

#####4.1 Pick one of the following languages: Python, Modula, Ada, C#, or Perl. After consulting an authoritative reference, discuss each of the following requirements for that language:
---


In Python, you don't declare variables before use. Variable names have no types, just the values have types.

In Python, you can overload operators for programmer-defined types. Types are just objects, and you can overload methods for objects.

Python lists are dynamically sized and mutable, containing references to objects. Since they're dynamic, array size is bound when the list gets an assignment. If something is added to the list it creates a new, larger, list.

Scope in Python is a matter of where a name is accessible. Names defined outside of a function are global, names defined inside of a function are only accessible inside of that function, etc.


#####4.2 After consulting an authoritative reference on the requirements for using global variables in C, answer the following questions.
---



**(c) Why would you want to hide global variables?**

If you wanted to reduce name collisions. For example, you had a common variable name in 2 different files that you wanted to make sure their namespaces didn't overlap.

#####4.3 C and C++ distinguish between declarations and definitions. What is the distinction? Give an example of each each.
---

A declaration creates an address in memory for that specific type. Whereas, a definition assigns it a value.

#####4.5 Most programming languages prohibit redeclaring the same variable name within the same scope.
---


#####4.6 For the language C, give three examples of r-values that cannot be l-values. Give three more examples of l-values? Are there l-values that cannot be r-values? Explain.
---

Cannot be l-values:
Constants,
Expressions,
An array type

Cannot be r-values:
Assignment operators,
Address operator (&)

L-values are a locator value and has an address to location in memory. A r-value is everything that doesn't represent an object in memory.