> import Data.Char

Chris Fenton
CNC - Haskell
Homework 2


1. Determine the types of "3", "even", and "even 3". How do you determine the last one? Now, determine the types of "head", "[1,2,3]", and "head [1,2,3]". What happens when applying a polymorphic function to an actual parameter? (hint, use the :t top level command of the Hugs interpreter).

2. For each type, write a function with that type.

a)   (Float -> Float) -> Float

> f' :: (Float -> Float) -> Float
> f' f = f 2.0

b)   Float -> (Float -> Float)

> f'' :: Float -> (Float -> Float)
> f'' x = (*) x

c)   (Float -> Float) -> (Float -> Float)

> f''' :: (Float -> Float) -> Float -> Float
> f''' f = f . ((*) 4.0)

3. Code the function rangeProduct that computes according to the following examples (CRFP). Return 0 if the second argument is smaller than the first. You decide what you want to do (if anything) for negative numbers. Write the program without recursion first and then write it with recursion.

rangeProduct 3 5  --> 3*4*5
rangeProduct 5 3  --> 0
rangeProduct 6 12 -> 6*7*8*9*10*11*12
rangeProduct 4 4  --> 4

> rangeProduct a b
>  | b < a     = 0
>  | otherwise = product [a..b]

> rangeProduct' a b = if b < a then 0 else g [a..b]
>   where g [] = 1
>         g (x:xs) = x * g xs

4. Code a version of the factorial function that uses your rangeProduct function (CRFP).

> factorial n = rangeProduct 1 n

5. Design a way to represent straight lines in a cartesian coordinate system as internal data in Haskell and then write a function that calculates the x-intercept (if any) for a given line (CRFP). Be sure to test your function with some interesting cases! Note that you will NOT be doing anything graphically. This is just an algebra problem.

> slopeIntercept (x1, y1) (x2, y2) = b * (-1) / m where
>   m = (y2 - y1)/(x2 - x1)
>   b = y1 - (m * x1)

6. Write a function which converts a string of digits into an int. You will need the following predefined function:
ord ‘1’       --> 49         first char in arg to its ascii code

Follow the following "pipeline" analysis when defining your function
"167"  --> ['1','6','7'] --> [49,54,55] --> [1,6,7] --> [(1,100),(6,10),(7,1)]--> [100, 60, 7] --> 167
(hint: the first function in the pipeline is very simple. why?)

> toAsci xs = map ord xs
> toDigit xs = map (subtract 48) xs

