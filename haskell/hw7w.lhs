-- Type classes and instances
------------------------------------------------------------------------------

> infixl 4 <*>
> class Functor f => Applicative f where
>   pure  :: a -> f a
>   (<*>) :: f (a -> b) -> f a -> f b

> liftA2 :: Applicative f => (a -> b -> c) -> f a -> f b -> f c
> liftA2 g x y = pure g <*> x <*> y
> --           = g <$> x <*> y
> --           = fmap g x <*> y


Maybe type constructor and instances

> -- Maybe type constructor as instance of Applicative
> instance Applicative Maybe where
>   pure = Just
>   Nothing <*> _  = Nothing
>   (Just g) <*> x = fmap g x


Reader ((->) r) type constructor and instances

> -- Reader ((->) r) type constructor as instance of Functor
> instance Functor ((->) r) where
>   fmap = (.)

> -- Reader ((->) r) type constructor as instance of Applicative
> instance Applicative ((->) env) where
>   pure g = \env -> g
>   eg <*> ex = \env -> (eg env) (ex env)

> -- Reader ((->) r) type constructor as instance of Monad
> instance Monad ((->) r) where
>   return x = \_ -> x
>   mx >>= mg = \env -> mg (mx env) env



------------------------------------------------------------------------------
-- Abstract Syntax for Expressions with Assignment
------------------------------------------------------------------------------

> -- Assignment is not implemented in the Expression evaluators
> data Expr = Val Int
>           | Var String
>           | BinOp (Int -> Int -> Int) Expr Expr
>           | Asn String Expr

> -- Environment mapping variables to values
> type Env = [(String, Int)]

> -- An environment lookup with Prelude.head exception on lookup failure
> fetch x env = head [b | (a,b) <- env, x == a ]

> -- A simple environment store for use in variable assignment
> store :: String -> Int -> Env -> Env
> store var val env = (var, val) : env


------------------------------------------------------------------------------
-- Evaluators for Expressions using Reader Monad ((->) r)
------------------------------------------------------------------------------

1. The Monadic Reader Evaluator 

> evalMR :: Expr -> Env -> Int
> evalMR (Val x) = return x
> evalMR (Var v) = (return v) >>= fetch
> evalMR (BinOp op e1 e2) = do
>   a <- evalMR e1
>   b <- evalMR e2
>   return (op a b)
>                         
> evalMR (Asn _ _)        = error "Assignment not Implemented in evalMR"

2. The Monadic Reader+Maybe Evaluator

> evalMRM :: Expr -> Env -> Maybe Int
> evalMRM (Val x)          = return (Just x)
> evalMRM (Var v)          = do
>   a <- fetch v
>   return (Just a)
> evalMRM (BinOp op e1 e2) = do
>   a <- evalMRM e1
>   b <- evalMRM e2
>   return (liftA2 op a b)
>                            
>                            
> evalMRM (Asn _ _)        = error "Assignment not Implemented in evalMRM"


------------------------------------------------------------------------------
-- Support functions for the Monadic State evaluator
------------------------------------------------------------------------------

> -- Read from State
> fetchS :: String -> State Int
> fetchS var = State (\env -> (env, fetch var env) )

> -- Write to State
> storeS :: String -> State Int -> State Int
> storeS var (State s) = State ( \env -> let (env', val) = s env
>                                        in (store var val env', val) )

> -- The general apply function for the State functor
> --applyS :: State Int -> Env -> (Env, Int)
> applyS (State s) env = s env

------------------------------------------------------------------------------
-- The State type constructor and instances
------------------------------------------------------------------------------

> -- Note this differs from the Control.Monad.State (the pair is switched).
> newtype State a         = State (Env -> (Env, a))

> newtype S s a           = S { runS :: s -> (a,s) }

> instance Functor State where
>   fmap f (State s)      = State( \env -> let (env', x) = s env
>                                     in (env', f x) )

> instance Applicative State where
>   pure g                = State $ \s -> (s,g)
>   State eg <*> State ex = State $ \env -> let (env', g)  = (applyS (State eg) env)
>                                               (env'', x) = (applyS (State ex) env')
>                                           in (env'', g x)

> instance Monad State where
>   return x              = pure x        -- from Applicative
>   State s >>= f         = State $ \env -> let (env', a)  = (applyS (State s) env)
>                                               (env'', b) = (applyS (f a) env')
>                                           in (env'', b)

3. Monadic State Evaluator for Expressions

> -- The Monadic State Evalulator for expressions (essentially Applicative)
> evalMS :: Expr -> State Int
> evalMS (Val v)          = pure v
> evalMS (Var x)          = fetchS x 
> evalMS (BinOp op e1 e2) = do
>   a <- evalMS e1
>   b <- evalMS e2
>   return (op a b)
> 
> 
> evalMS (Asn _ _)        = error "Assignment not Implemented in evalMS"

> -- Run the Monadic State evaluator on expressions
> evalMSRun :: Expr -> Env -> (Env, Int)
> evalMSRun expr env = applyS (evalMS expr) env

Why didn't we need to do these apply and run with the evaluators eval_MR
and eval_MRM using the ((->) Env) Reader and Maybe functors?
Because in our case here we have
buried the sequence of actions behind our defined State constructor. That
wasn't the case for the simple ((->) Env) evaluator which consists only
of built-in type constructors.

------------------------------------------------------------------------------
-- Evaluators for Expressions with Assignment using State Monad
------------------------------------------------------------------------------

4. Monadic State Evaluator for Expressions with Assignment

> -- The Monadic State Evalulator for expressions with Assignment
> evalMSAsn :: Expr -> State Int
> evalMSAsn (Val v)          = pure v
> evalMSAsn (Var x)          = fetchS x
> evalMSAsn (BinOp op e1 e2) = do
>   a <- evalMSAsn e1
>   b <- evalMSAsn e2
>   return (op a b)
> evalMSAsn (Asn x e)        = storeS x (evalMSAsn e)

> -- Run the Monadic State evaluator on expressions
> evalMSAsnRun :: Expr -> Env -> (Env, Int)
> evalMSAsnRun expr env = applyS (evalMSAsn expr) env

------------------------------------------------------------------------------
-- De-abstracted code - Reference evaluator that carries state explicitly
------------------------------------------------------------------------------

> evalSRef :: Expr -> Env -> (Env, Int)
> evalSRef (Val x) env     = (env, x)
> evalSRef (Var var) env   = (env, fetch var env)
> evalSRef (BinOp op e1 e2) env = let (env1, val1) = evalSRef e1 env
>                                     (env2, val2) = evalSRef e2 env1
>                                 in (env2, op val1 val2)
> evalSRef (Asn var e) env = let (env1, val) = evalSRef e env
>                            in (store var val env1, val)


------------------------------------------------------------------------------
-- Applicative State evaluator for Expressions with Assignment
------------------------------------------------------------------------------

> evalAS :: Expr -> State Int
> evalAS (Val x)          = pure x
> evalAS (Var var)        = fetchS var
> evalAS (BinOp op e1 e2) = pure op <*> evalAS e1 <*> evalAS e2
> evalAS (Asn var e)      = storeS var (evalAS e)
> -- The fetch and store are pretty much hand-plumbed. I don't know how to
> --   do it otherwise.

> -- Run the code constructed by EvalAS in the state functor
> evalASRun :: Expr -> Env -> (Env, Int)
> evalASRun expr env = applyS (evalAS expr) env

------------------------------------------------------------------------------
-- Testing
------------------------------------------------------------------------------

> -- Some sample expressions without assignment
> e1 = Val 3
> e2 = BinOp (+) (Val 3) (Val 4)
> e3 = BinOp (*) (BinOp (-) (Val 9) (Val 4)) (BinOp (-) (Val 7) (Val 2)) 
> e4 = BinOp (+) (BinOp (*) (Val 9) (Val 4)) (BinOp (div) (Val 7) (Val 2)) 
> e5 = BinOp (-) (Val 3) (BinOp (-) (Val 4) (Val 6)) 
> e6 = Var "a" 
> e7 = BinOp (+) (Var "a") (Var "b")
> e8 = BinOp (*) (BinOp (-) (Var "a") (Val 4)) (BinOp (-) (Val 7) (Var "b")) 
> e9 = BinOp (+) (BinOp (*) (Val 9) (Var "a")) (BinOp (div) (Var "b") (Var "c")) 

> -- Sample expressions with assignment
> e10 = BinOp (+) (BinOp (*) (Asn "a" (Val 6)) (Var "b"))
>                 (BinOp (+) (Var "a") (BinOp (*) (Asn "b" (Val 3)) (Var "b")))

> -- First test the reference State evaluator for expressions with assignment
> test_evalSRef = [ evalSRef e1 [] == ([], 3)
>                  ,evalSRef e2 [] == ([], 7)
>                  ,evalSRef e3 [] == ([], 25)
>                  ,evalSRef e4 [] == ([], 39)
>                  ,evalSRef e5 [] == ([], 5)
>                  ,evalSRef e6 [("a",5)] == ([("a",5)], 5)
>                  ,evalSRef e7 [("a",5), ("b",8)] == ([("a",5), ("b",8)], 13)
>                  ,evalSRef e8 [("a",7), ("b",2)] == ([("a",7), ("b",2)], 15)
>                  ,evalSRef e9 [("a",7), ("b",8), ("c", 2)]
>                              == ([("a",7), ("b",8), ("c", 2)], 67)
>                  ,evalSRef e10 [("a",7), ("b",2)] 
>                              == ([("b", 3), ("a",6), ("a", 7), ("b",2)], 27)
>                  ]

> test_evalSRef_fail1 =
>   evalSRef e9 [("a",7), ("b",8)]


> -- Test the Applicative State evaluator for expressions with assignment
> test_evalAS = [ evalASRun e1 [] == ([], 3)
>                  ,evalASRun e2 [] == ([], 7)
>                  ,evalASRun e3 [] == ([], 25)
>                  ,evalASRun e4 [] == ([], 39)
>                  ,evalASRun e5 [] == ([], 5)
>                  ,evalASRun e6 [("a",5)] == ([("a",5)], 5)
>                  ,evalASRun e7 [("a",5), ("b",8)] == ([("a",5), ("b",8)], 13)
>                  ,evalASRun e8 [("a",7), ("b",2)] == ([("a",7), ("b",2)], 15)
>                  ,evalASRun e9 [("a",7), ("b",8), ("c", 2)]
>                              == ([("a",7), ("b",8), ("c", 2)], 67)
>                  ,evalASRun e10 [("a",7), ("b",2)] 
>                              == ([("b", 3), ("a",6), ("a", 7), ("b",2)], 27)
>                  ]

> test_evalAS_fail1 =
>   evalASRun e9 [("a",7), ("b",8)]

-- Test the Monadic Reader Evaluator for expressions

> -- No Run is needed and no environment is returned
> test_evalMR = [ evalMR e1 [] == 3
>                ,evalMR e2 [] == 7
>                ,evalMR e3 [] == 25
>                ,evalMR e4 [] == 39
>                ,evalMR e5 [] == 5
>                ,evalMR e6 [("a",5)] == 5
>                ,evalMR e7 [("a",5), ("b",8)] == 13
>                ,evalMR e8 [("a",7), ("b",2)] == 15
>                ,evalMR e9 [("a",7), ("b",8), ("c", 2)] == 67
>                ]


> test_evalMR_fail1 =
>   evalMR e9 [("a",7), ("b",8)]
> test_evalMR_fail2 = 
>   evalMR e10 [("a",7), ("b",8)]

-- Test the Monadic Reader+Maybe Evaluator for expressions

> -- No Run is needed and no environment is returned
> test_evalMRM = [ evalMRM e1 [] == Just 3
>                ,evalMRM e2 [] == Just 7
>                ,evalMRM e3 [] == Just 25
>                ,evalMRM e4 [] == Just 39
>                ,evalMRM e5 [] == Just 5
>                ,evalMRM e6 [("a",5)] == Just 5
>                ,evalMRM e7 [("a",5), ("b",8)] == Just 13
>                ,evalMRM e8 [("a",7), ("b",2)] == Just 15
>                ,evalMRM e9 [("a",7), ("b",8), ("c", 2)] == Just 67
>                ]


> test_evalMRM_fail1 =
>   evalMRM e9 [("a",7), ("b",8)]
> test_evalMRM_fail2 = 
>   evalMRM e10 [("a",7), ("b",8)]

-- Test the Monadic State Evaluator for expressions

> test_evalMS = [ evalMSRun e1 [] == evalSRef e1 [] -- should do em all this way
>                ,evalMSRun e2 [] == evalSRef e2 []
>                ,evalMSRun e3 [] == evalSRef e3 []
>                ,evalMSRun e4 [] == evalSRef e4 []
>                ,evalMSRun e5 [] == evalSRef e5 []
>                ,evalMSRun e6 [("a",5)] == ([("a",5)], 5)
>                ,evalMSRun e7 [("a",5), ("b",8)] == ([("a",5), ("b",8)], 13)
>                ,evalMSRun e8 [("a",7), ("b",2)] == ([("a",7), ("b",2)], 15)
>                ,evalMSRun e9 [("a",7), ("b",8), ("c", 2)]
>                          == ([("a",7), ("b",8), ("c", 2)], 67)
>                ]


> test_evalMS_fail1 =
>   evalMSRun e9 [("a",7), ("b",8)]
> test_evalMS_fail2 = 
>   evalMSRun e10 [("a",7), ("b",8)]

> -- Test the Monadic State Evaluator for assignment expressions

> test_evalMSAsn
>             = [ evalMSAsnRun e1 [] == evalSRef e1 []
>                ,evalMSAsnRun e2 [] == evalSRef e2 []
>                ,evalMSAsnRun e3 [] == evalSRef e3 []
>                ,evalMSAsnRun e4 [] == evalSRef e4 []
>                ,evalMSAsnRun e5 [] == evalSRef e5 []
>                ,evalMSAsnRun e6 [("a",5)] == ([("a",5)], 5)
>                ,evalMSAsnRun e7 [("a",5), ("b",8)] == ([("a",5), ("b",8)], 13)
>                ,evalMSAsnRun e8 [("a",7), ("b",2)] == ([("a",7), ("b",2)], 15)
>                ,evalMSAsnRun e9 [("a",7), ("b",8), ("c", 2)]
>                          == ([("a",7), ("b",8), ("c", 2)], 67)
>                ,evalMSAsnRun e10 [("a",7), ("b",2)] 
>                    == ([("b", 3), ("a",6), ("a", 7), ("b",2)], 27)
>                ]

> test_evalMSAsn_fail1 =
>   evalMSAsnRun e9 [("a",7), ("b",8)]

> -- Test the kitchen sink

> test_all = and test_evalSRef &&      -- Reference evaluator
>            and test_evalAS &&        -- Applicative State evaluator
>            and test_evalMR &&        -- Monadic Reader
>            and test_evalMRM &&       -- Monadic Reader+Maybe
>            and test_evalMS &&        -- Monadic State evaluator
>            and test_evalMSAsn        -- Monadic State with Assignment
