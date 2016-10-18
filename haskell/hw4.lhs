Chris Fenton
CNC - Haskell
Homework 4

1. Define a function composeList that composes a list of functions into a single function.

> composeList [f] = f
> composeList (f:fs) = f . composeList fs

2. Rewrite composeList as a fold and use quickCheck to demonstrate the equivalence of your two implementations.

> composeList' fs = foldr (.) id fs

3. Recode your stringToNum program from hw2 as a composition of functions and then write a function numToString (also using the composition form) that does the inverse. Test your implementations using quickCheck.

4. Write a function iter f x n that applies the function f n times to the initial value x. Assume that if n is zero or less then the value x is returned (CRFP).

5. Rewrite the iter function of the previous problem as an unfold. Use quickCheck to verify that both implementations work the same.

6. Rewrite the functions any, all, takeWhile, dropWhile as folds. Test them with quickCheck to see that they work the same as the prelude functions.

7. Code the function bin2int that takes a binary string of 0s and 1s and returns the value as an integer. Code the inverse function int2bin. Test your functions using quickCheck to demonstrate that starting with a positive integer you can get back the original integer.

8. Recode bin2int as a fold and int2bin as an unfold. Again, use quickcheck to verify your functions work as inverses of each other. It works easier to assume the least significant digits are on the left.

9. Using the functions curry and uncurry as a model, code functions curry3 and uncurry3 that work on triples rather than pairs.

10. Define functions curryList and uncurryList that work on a list of arguments rather than a tuple of arguments.

