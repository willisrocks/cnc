Haskell Homework Assignment 5
Chris Fenton
---

Comments

Finished 1-3. Skipped the bit transmittor.

Binary Trees

1. Define a Binary Tree data type and write the following functions on your binary tree data type

> data Tree a = Nil | Node a (Tree a) (Tree a)
>   deriving (Eq, Ord, Show, Read)

a. map

> treeMap f Nil = Nil
> treeMap f (Node a l r) = (Node a (treeMap f l) (treeMap f r))

b. foldr

> treeFold f acc Nil = acc
> treeFold f acc (Node a l r) = (f a acc) `f` (treeFold f acc l) `f` (treeFold f acc r)

Clite Expression Evaluation

2. Build an algebraic data type for the Abstract Syntax category Expressions in the Clite Abstract Syntax. Skip the ArrayRef (so that an Expression just be "Expression = Variable | Value | Binary | Unary").

 data Expr =  VarRef Variable
            | ValExpr Value
            | Binary BinOp Expr Expr
            | Unary UnaryOp Expr
            deriving (Eq, Show)
 data BinOp = And | Or | Plus | Minus | Mult | Div
            deriving (Eq, Show)
 data UnaryOp = Not | Neg
            deriving (Eq, Show)
 data Value = IntegerVal Integer | IntVal Int
            deriving (Eq, Show)
 data Variable = String
            deriving (Eq, Show)

> data Expr = Val Int | BinOp Op Expr Expr
>             deriving (Show, Eq)
> data Op   = Add | Sub | Mul | Div
>             deriving (Show, Eq)


3. Write an eval function to evaluate Expressions in the Clite Abstract Syntax that you coded in your algebraic data type for the Abstract Syntax of Clite.

> exprEval (Val x) = x
> exprEval (BinOp Add e1 e2) = (exprEval e1) + (exprEval e2)
> exprEval (BinOp Sub e1 e2) = (exprEval e1) - (exprEval e2)
> exprEval (BinOp Mul e1 e2) = (exprEval e1) * (exprEval e2)
> exprEval (BinOp Div e1 e2) = (exprEval e1) `div` (exprEval e2)

4. Write some meaningful tests of your expression evaluator. Coding Expressions in the algebraic data type for the abstract syntax is tedious, but if you use multi-line and indentation that can help.

5. (Extra) Code your Clite Expression evaluator as a fold. See the Haskell Wikibook section on Other Data Structures for a discussion of how to code folds over arbitrary algebraic data types (like your Clite Expressions).

A Transmission Simulator

The next series of exercises are from Hutton's textbook. The code is simpler if you assume bit strings with higher order positions on the right instead of the left. The goal is to simulate binary transmission of strings as in the following functions. The algebraic data type Bit in this example is:

data Bit = B0 | B1

If you prefer, you can just use Ints 0 and 1 for bits as I did in the lecture code, which is simpler, but a little less safe. See Lecture 2b for samples of both.

transmit :: String -> String
transmit = deocde . channel . encode

channel :: [Bit] -> [Bit]   -- simulates a transmission channel.
channel = id

so "transmit "hi there Robo 432!" prints "hi there Robo 432!""

6. Code make8 :: [Bit] -> [Bit] that pads or truncates to make exactly 8 bits.

7. Code encode :: String -> [Bit] that encodes a string of characters as a list of bits that encode the 8-bit ascii representations.

8. Code chop8 :: [Bit] -> [[Bit]] that chops up a long list of bits into a list of 8-bit binary numbers.

9. Code decode :: [Bit] -> String that decodes a list of bits into the corresponding string of characters.

Folds and Unfolds

10. Redefine the function chop8 as an unfold.

11. Redefine the function map f as an unfold.

