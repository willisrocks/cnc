1. Code a function to quadruple a number using a function that doubles a number then write out the complete step-by-step evaluation of quadruple 5 (from HWIB).

> double x = 2 * x
> quadruple x = double x

2. Code a function averageThree :: Integer -> Integer -> Integer - Float that returns the average of the three integer arguments (from CRFP). This will expose you to the reality that Haskell does NO automatic type conversion and you have to learn how to properly do explicit type conversion.

> averageThree a b c = (a + b + c) / 3.0

3. Code a function howManyAboveAverage :: Integer -> Integer -> Integer - Integer that returns how many of its inputs are strictly larger than the average value (from CRFP).

> howManyAboveAverage a b c = length [ x | x <- [a,b,c], x > averageThree a b c]

4. Code the last function for lists using the built-in reverse function for lists. The last function returns the last element of a list. What will you do for the empty list?

> last' xs = head (reverse xs)

* Since last' takes a list and returns an element from that list, it doesn't make sense for it to return an element from the empty list. I'll just let it throw an exception in this case.

5. Code the init function for lists using the built-in reverse function for lists. The init function returns the list of all but the last element of a list. What will you do for the empty list?

> init' [] = []
> init' xs = reverse (drop 1 (reverse xs))

* Since we mean to return a list, it makes sense to return the empty list when the input is an empty list.

6. Code a function that returns the first and last elements of a list as a 2-tuple (a pair). Give a type for the function. Be sure to think about what you want to do for a list of length 0 or 1.

> firstLast (x:xs) = (x, g)
>   where g = last' xs

* Similar to last', it doesn't make sense to return a tuple for an empty or 1 item list, so we'll just let it throw an exception.  

7. Write the power :: Integer -> Integer -> Integer function for integers without using the exponentiation operator. Code this function first without recursion and then with recursion. Examples: power 3 2 gives 9, power 2 6 gives 32, power 4 3 gives 64.

> power' x n = product (replicate n x)

> power'' x 0 = 1
> power'' x n = x * (power'' x (n - 1))

8. Write the ncopies function (from Time Sheard) that works according to the following examples. Code this function first without recursion and then with recursion.

ncopies 3 5  gives [5,5,5]
ncopies 0 True gives []
ncopies 2 False gives [False, False]
ncopies 4 "a" gives ["a","a","a","a"]

> ncopies' n x = take n g
>   where g = x : g

> ncopies'' 0 x = []
> ncopies'' n x = x : (ncopies'' (n - 1) x)

9. (Extra) Write quickCheck tests on as many of the programs above as you can. See QuickCheck Tutorial or QuickCheck Introduction

