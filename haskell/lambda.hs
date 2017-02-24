--
-- File to support lambda terms
-- closely based on Sergio Antoy's verion


type Var = String

data Constant = Plus
              | Minus
              | Times
              | Div
              | BTrue
              | BFalse
              | Num Int
     deriving (Show, Eq)


data Exp = Abs Var Exp
         | App Exp Exp
         | Var Var
         | Cons Constant
  deriving (Show, Eq)

-- fv(exp) returns the free vars

fv :: Exp -> [Var]

fv (Abs v e) = setdiff (fv e) [v]
fv (App e1 e2) = un (fv e1) (fv e2)
fv (Var v) = [v]

-- create a fresh variable. Use the function maximum which
-- returns the maximum value of a list (lexocographic ordering).
-- then append a prime. Could also use minimum. Or some other
-- plan. Just create a new variable not present in the list

fresh vars = maximum vars ++ "'"

--
-- do beta substitution
-- sub m x e:  sub m for x in e
--

sub m (Var x) (Cons c)           = (Cons c)         -- constant rule
sub m (Var x) (Var v)                           
   | x == v                = m                -- variable rule
   | x /= v                = (Var v)
sub m (Var x) (App e1 e2)        = (App (sub m (Var x) e1) (sub m (Var x) e2))
                                   -- app rule
sub m (Var x) (Abs v e)
   | x == v                = (Abs v e)        -- abs rule, x is the bound var
   | notElem x (fv e)
     || notElem v (fv m)   = (Abs v (sub m (Var x) e))
                                              -- abs , x not free in e OR
                                              -- y not free in m
   | otherwise             = (Abs z (sub m (Var x) (sub (Var z) (Var v) e)) )
       where
         z = fresh (un (fv m) (fv e))


-- Beta reduction

reduce (Cons c) = (Cons c)
reduce (Var x) = (Var x)
-- if the leftmost term of an application is a lambda abstraction,
-- reduce it using the sub rule
reduce (App (Abs v e) e2) = sub e2 (Var v) e
-- if the leftmost term of an application is not a lambda abstraction,
-- try reducing it
reduce (App e1 e2) = App (reduce e1) (reduce e2)
-- if the leftmost term is an abstraction, reduce the body
reduce (Abs v e) = Abs v (reduce e)

-- Church numerals: the number of apps of f corresponds to the
-- numeral

one = Abs "f" (Abs "x" (App (Var "f") (Var "x")))
two = Abs "f" (Abs "x" (App (Var "f") (App (Var "f") (Var "x"))))
three = Abs "f" (Abs "x" (App (Var "f") (App (Var "f") (App (Var "f") (Var "x")))))
four = Abs "f" (Abs "x" (App (Var "f") (App (Var "f") (App (Var "f") (App (Var "f") (Var "x"))))))

add = Abs "n" (Abs "m" (Abs "f" (Abs "x" (App (App (Var "n") (Var "f"))
                (App (App (Var "m") (Var "f"))
                     (Var "x"))))))

-- Main> reduce (App (App add one) one)
-- App (Abs "m" (Abs "f" (Abs "x" (App (App (Abs "f" (Abs "x" (App (Var "f") (Var "x")))) (Var "f")) (App (App (Var "m") (Var "f")) (Var "x")))))) (Abs "f" (Abs "x" (App (Var "f") (Var "x"))))
-- Main> reduce (reduce (App (App add one) one))
-- Abs "f" (Abs "x" (App (App (Abs "f" (Abs "x" (App (Var "f") (Var "x")))) (Var "f")) (App (App (Abs "f" (Abs "x" (App (Var "f") (Var "x")))) (Var "f")) (Var "x"))))
-- Main> reduce (reduce (reduce (App (App add one) one)))
-- Abs "f" (Abs "x" (App (Abs "f'" (App (Var "f") (Var "f'"))) (App (Abs "f'" (App(Var "f") (Var "f'"))) (Var "x"))))
-- Main> reduce (reduce (reduce (reduce (App (App add one) one))))
-- Abs "f" (Abs "x" (App (Var "f") (App (Abs "f'" (App (Var "f") (Var "f'"))) (Var"x"))))
-- Main> reduce (reduce (reduce (reduce (reduce (App (App add one) one)))))
-- Abs "f" (Abs "x" (App (Var "f") (App (Var "f") (Var "x"))))
-- Main>

-- reduce (add one one). This takes 5 reductions
-- Main> reduce (reduce (reduce (reduce (reduce (App (App add one) one)))))
-- Abs "f" (Abs "x" (App (Var "f") (App (Var "f") (Var "x"))))
-- Main>



suc = App add one


--
-- delta rules

delta (App (App (Cons Plus) (Cons (Num a))) (Cons (Num b))) = a + b

t1 = delta (App (App (Cons Plus) (Cons (Num 4))) (Cons (Num 5)))
   



-- Support functions: set difference and union

setdiff [] _ = []
setdiff x [] = x
setdiff (x:xs) ys
   | elem x ys             = setdiff xs ys
   | otherwise             = x:(setdiff xs ys)

un x [] = x
un [] x = x
un (x:xs) ys
   | elem x ys             = un xs ys
   | otherwise             = x:(un xs ys)


-- my code
-- 1. Use the file lambda.hs. Load it and run it. done
-- 2. Construct terms:
-- The addition of the terms three and four (you may need to make the church numerals for these). Make sure they are in normal form.
-- Write a definition for if (not a constant but a lambda term).
-- Add 3 and 4:
-- App (App add three) four
-- if:
-- (Abs "b1" (Abs "e1" (Abs "e2" (App (App (Var "b1") (Var "e1")) (Var "e2")))))
-- Write a function that attempts to evaluate an expression to normal form using call-by-value. (cbv)

-- Call by Value (cbv)
-- takes labmda term and attempts to reduce the term to normal form
cbv (Var x) = (Var x) -- Variable term

-- Application term
cbv (App m n) = reduce (cbv m) v1 where
  v1 = cbv n

-- Abstraction term
