> import Data.List (group, groupBy)
> import Data.Char (ord)

1. Write a function scanSum that adds the items in a list and returns a list of the running totals. So, scanSum [2,3,4,5] returns [2,5,9,14]. (HWIB)

> scanSum :: [Int] -> [Int]
> scanSum xs = scan' 0 xs where
>   scan' acc [] = []
>   scan' acc (x:xs) = (acc + x) : scan' (acc + x) xs

2. Write a function diffs that returns a list of the differences between adjacent items. So, diffs [3,5,6,8] returns [2,1,2]. (HWIB)

> diffs xs = map sub' $ zip xs $ tail xs where
>   sub' (h,t) = t - h

3 Write a function groupByN with the type:

groupByN :: Int -> [a] -> [[a]]

This function splits the given list in sub-lists (which result in a list of lists), where the sublists have a given length. Only the last sub-list may be shorter, if necessary. An example application of the function is:

groupByN 3 [1,2,3,4,5,6,7,8,9,10]
[[1,2,3], [4,5,6], [7,8,9], [10]]

> groupByN n [] = []
> groupByN n xs  = take n xs : groupByN n (drop n xs)

4. Write a function listTrim with two lists as parameters that deletes the first occurrence of every element of the second list from the first list.

> listTrim xs ys = [ x | y <- ys, x <- (deleteFirst y xs) ]
> unique xs = [x | (x,y) <- zip xs [0..], x `notElem` (take y xs)]
> deleteFirst n [] = []
> deleteFirst n (x:xs)
>   | n == x = xs
>   | otherwise = x : deleteFirst n xs

5. Now write a function listDiff with two lists as parameters that deletes every occurrence of every element of the second list from the first list. This is analogous to a set difference.

> listDiff xs ys = [ x | x <- xs, not $ elem x ys ]

6. Implement a Run Length Encoding (RLE) encoder and decoder. The idea of RLE is simple; given some input: "aaaabbaaa" compress it by taking the length of each run of characters:(4,'a'), (2, 'b'), (3, 'a') The concat and group functions might be helpful. In order to use group, import the Data.List module by typing :m Data.List at the ghci prompt or by adding import Data.List to your Haskell source code file. What is the type of your encode and decode functions? (from HWIB)

** Kind of messy, but I got covered occurrences of a char that are >= 10. Hopefully, I give it another go and clean it up! **

> encode xs = flatten . map enc' . group $ xs where
>   enc' ys = (show . length $ ys) ++ (take 1 ys)

> decode [] = []
> decode xs = x ++ decode y where
>   x = fst . dec' $ xs
>   y = snd . dec' $ xs
>   dec' xs = (explode' token', rest')
>   token' = (getFirstCharNum xs, getFirstCharChar xs)
>   rest' = drop ((length . fst $token') + 1) xs
>   explode' (n,c) = replicate (string2int n) c
>   charIsNum c = 48 <= (ord c) && (ord c) <= 57
>   idFirstCharNum xs = groupBy (\x y -> not . charIsNum $ x) xs
>   getFirstCharNum xs = flatten . reverse . tail . reverse . idFirstCharNum $ xs
>   getFirstCharChar xs = head .head . reverse . idFirstCharNum $ xs

> string2int xs = sum . f . g . h . j $ xs where
>   f xs = map (\(x,y) -> x * y) xs
>   g xs = zip xs (reverse . powersOf10 . length $ xs)
>   h xs = map (subtract 48) xs
>   j xs = map ord xs
>   powersOf10 n = [10^x | x <- [0..(n - 1)]]

> flatten xss = foldr (++) [] xss
