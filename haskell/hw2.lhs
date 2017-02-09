1. Make the type (IO a) an instance of the Functor class. You won't be able to actually code GHC your instance because the GHC-Base exports the IO instance of Functor, so just put the code in as a comment (suggestion: use lhs style).

instance Functor IO where
  fmap f action = do
    result <- action
    return (f result)

2. Now find the definition of the IO instance of Functor in GHC-Base and copy the code into your Hw2.lhs file as a comment. Chances are your code is not the same as the GHC-Base code, so explain how your code is correct and equivalent to the GHC-Base code even if not the same. If your code is not correct, then adjust it so that it is correct and then explain how your code is correct and equivalent but not the same.

instance Functor IO where
  fmap f x = x >>= (pure . f)

3. Code an ex2 test case using fmap to compute the equivalent of the ex1 test code below. Your code should be fmap something1 something2

4. Code an ex3 test case using bind and in point free form that is equivalent to the ex1 test case below. Hint: look at the IO instance of Functor that you clipped from GHC-Base. Another big hint: first code ex1 using the bind operator (>>=) instead of the do notation. (See LYAH Ch12 Fistful of Monads Chapter, the do-notation section where they show the translation).

> ex1 = do {putStrLn "Enter a string"; x <- getLine; return (reverse x)}
> ex2 = do {putStrLn "Enter a string"; x <- fmap reverse getLine; return x}
> ex3 = putStrLn "Enter a string" >>= (\x -> fmap reverse getLine)

5. Compile your IO-fmap test program from the previous exercise into a Unix executable that does ex1 followed by ex2 followed by ex3.

6. (CRFP) Define a function putNtimes :: Integer -> String -> IO () that putNtimes n str will output a given str n times.

7. (CRFP) Write an IO program that will read a line of input and test whether the inut string is palindrome. The program should prompt for its input and output the answer.

8. (CRFP) Define an interactive palindrome checker that will repeatedly input strings checking them for palindromes until an empty string is input.
Proofs

No proofs this week. (What?!) Naw. Try this one

9. Prove that the instance of the "Evil Functor instance" (defined in the TCOP listing in Section 3.3 Laws) does not obey the functor laws. Remember, to prove a law does not hold you only need to provide one specific counterexample.
