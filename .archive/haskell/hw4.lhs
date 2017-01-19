> import Test.QuickCheck
> import Data.Char (ord)
> import Data.List (unfoldr)

Chris Fenton
CNC - Haskell
Homework 4

1. Define a function composeList that composes a list of functions into a single function.

> composeList [f] = f
> composeList (f:fs) = f . composeList fs

> propComposeList :: Integer -> Bool
> propComposeList x = composeList fs x == testComposeFunction x where
>   fs = [t1, t2]
>   testComposeFunction = t1 . t2
>   t1 = \x -> x + 2
>   t2 = \x -> 2 * x
> testComposeList = quickCheck propComposeList


2. Rewrite composeList as a fold and use quickCheck to demonstrate the equivalence of your two implementations.

> composeList' fs = foldr (.) id fs

> propComposeList' :: Int -> Bool
> propComposeList' x = composeList fs x == composeList' fs x where
>   fs = [\x -> x + 2, \x -> 2 * x]
> testComposeList' = quickCheck propComposeList'

3. Recode your stringToNum program from hw2 as a composition of functions and then write a function numToString (also using the composition form) that does the inverse. Test your implementations using quickCheck.

> stringToNum :: [Char] -> Int
> stringToNum xs = sum . digitsToNums . getDigits . charsToInts $ xs where
>   digitsToNums xs = map (\(x,y) -> x * y) xs
>   getDigits xs = zip xs (reverse . powersOf10 . length $ xs)
>   charsToInts xs = map (subtract 48) $ map ord xs
>   powersOf10 n = [10^x | x <- [0..(n - 1)]]

> numToString :: Int -> [Char]
> numToString = show

> propStringToNum :: Int -> Bool
> propStringToNum x = (stringToNum . numToString $ abs x) == abs x
> testStringToNum = quickCheck propStringToNum

4. Write a function iter f x n that applies the function f n times to the initial value x. Assume that if n is zero or less then the value x is returned (CRFP).

> iter :: (Int -> Int) -> Int -> Int -> Int
> iter f x 0 = x
> iter f x n = f (iter f x (n - 1))

> iter' f x n = head . reverse $ take (n + 1) $ iterate f x

5. Rewrite the iter function of the previous problem as an unfold. Use quickCheck to verify that both implementations work the same.

> iter'' f x n = head . reverse $ unfold' f x n where
>   unfold' f x n | n == 0    = []
>                 | otherwise = f x : unfold' f (f x) (n-1)

> propIter'' :: Int -> Bool
> propIter'' x = (iter (+2) x 5) == (iter'' (+2) x 5)
> testIter'' = quickCheck propIter''

6. Rewrite the functions any, all, takeWhile, dropWhile as folds. Test them with quickCheck to see that they work the same as the prelude functions.

> any' p xs = foldr (\x acc -> acc || p x) False xs

> propAny :: Int -> Bool
> propAny x = (any even $ replicate 20 x) == (any' even $ replicate 20 x)
> testAny = quickCheck propAny

> all' p xs = foldr (\x acc -> acc && p x) True xs

> propAll :: Int -> Bool
> propAll x = (all even $ replicate 20 x) == (all' even $ replicate 20 x)
> testAll = quickCheck propAll

> takeWhile' p xs = foldr (\x xs -> if p x then x:xs else []) [] xs
> propTakeWhile :: Int -> Bool
> propTakeWhile x = (takeWhile (<x) [1..x+20]) == (takeWhile' (<x) [1..x+20])
> testTakeWhile = quickCheck propTakeWhile

> dropWhile' p xs = drop (length $ takeWhile' p xs) xs
> propDropWhile :: Int -> Bool
> propDropWhile x = (dropWhile (>x) [1..x+20]) == (dropWhile' (>x) [1..x+20]) 
> testDropWhile = quickCheck propDropWhile

7. Code the function bin2int that takes a binary string of 0s and 1s and returns the value as an integer. Code the inverse function int2bin. Test your functions using quickCheck to demonstrate that starting with a positive integer you can get back the original integer.

Since I have your lecture notes in front of me where you already solved this problem, I don't see much sense in copying it over :)

8. Recode bin2int as a fold and int2bin as an unfold. Again, use quickcheck to verify your functions work as inverses of each other. It works easier to assume the least significant digits are on the left.

9. Using the functions curry and uncurry as a model, code functions curry3 and uncurry3 that work on triples rather than pairs.

> curry3 f a b c = f (a, b, c)
> uncurry3 f t@(_,_,_) = f t

> fst3 (x,_,_) = x
> add3 (a,b,c) = a + b + c

10. Define functions curryList and uncurryList that work on a list of arguments rather than a tuple of arguments.

Maybe I'm misunderstanding the curry function, but I can't see how to take a variable amount of arugments for curryList.
