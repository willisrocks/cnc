data Expr = Num Int | Var String | Add Expr Expr |
            Sub Expr Expr | Mul Expr Expr |
            Div Expr Expr
             deriving (Eq, Ord, Show)
diff :: String -> Expr -> Expr

diff x (Num c) = Num 0
diff x (Var y) = if x == y then Num 1 else Num 0
diff x (Add u v) = Add (diff x u) (diff x v)
diff x (Sub u v) = Sub (diff x u) (diff x v)
diff x (Mul u v) = Add (Mul u (diff x v))
                         (Mul v (diff x u))