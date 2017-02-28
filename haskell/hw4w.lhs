> import Prelude hiding (Maybe(..))

Programming Problems with Applicative Functors

1. Code your own Applicative class using what you know from the Typeclassopedia (TCOP) paper. Structure your definition so that it depends on the Pointed class you defined in the last assignment. Use your definitions from the previous assignment for the Pointed class.

> class Functor f => Pointed f where
>   mypure :: a -> f a

2. Code the Maybe type constructor as an instance of your Pointed and Applicative classes. Write some good test cases.

> data Maybe a = Nothing | Just a deriving (Show)

> instance Functor Maybe where
>   fmap _ Nothing = Nothing
>   fmap f (Just a) = Just (f a)

> instance Pointed Maybe where
>   mypure = Just

> class Functor f => MyApplicative f where
>   apure :: a -> f a
>   (<*>) :: f (a -> b) -> f a -> f b

> instance MyApplicative Maybe where
>   apure = mypure
>   Nothing <*> _ = Nothing
>   (Just f) <*> x = fmap f x

3. Code the (Either a) type constructor as an instance of Pointed and then an instance of your Applicative class. You'll need to give code for pure and app (<*>). You give the code for pure when you make (Either a) an instance of Pointed, and you give the code for (<*>) when you make (Either a) an instance of Applicative. Write some good test cases.

> --instance Pointed (Either a) where
>   --mypure = Left

> --instance MyApplicative (Either a) where
>   --apure = Left
>   --(Right b) <*> _ = Right b
>   --(Left b) <*> something = fmap b something


4. Make the [] type constructor (List) an instance of Pointed and then an instance of your Applicative class. Again you'll need to code pure and app (<*>). There are the two possible ways. I suggest you do both. Write some good test cases.

> instance Pointed [] where
>   mypure x = [x]

> instance MyApplicative [] where
>   apure = mypure
>   fs <*> xs = [f x | f <- fs, x <- xs]

5. Make the ((->) r) type constructor an instance of your Pointed and Applicative classes. Write some good test cases.

> instance Pointed ((->) r) where
>   mypure x = \r -> x

> instance MyApplicative ((->) r) where
>   apure = mypure
>   f <*> g = \x -> f x (g x)

Proofs

You'll need to include in this assignment your code for the Applicative and Pointed class definitions and class instance declarations for the appropriate type constructors. Carefully label your function definitions and clearly refer to them in your proofs as proof-step justifications.

The Applicative law to prove in the following exercises is the law that characterizes the relationship between <*> and fmap. This particular law is not given in a name in the paper and I think that's because the law is a consequence of the named laws. In any case, for reference, here's the law and the name I'll give it.

fmap f x = pure f <*> x                (Applicative)

6. Prove the Applicative law given in the TCOP paper for the Maybe type constructor using the function definitions in your Applicative instance of the Maybe type constructor. No induction needed.

Maybe Law 1: fmap id (Maybe a) = id (Maybe a)

Maybe has two cases: Nothing and (Just a)

Nothing Case:

LHS: fmap id (Nothing)
     Nothing  --from definition of fmap _ Nothing = Nothing

RHS: id (Nothing)
     Nothing  --from definition of pure

LHS = RHS

Just a Case:

LHS: fmap id (Just a)
     Just (id a)  --definition of fmap
     Just a  --definition of id

RHS: id (Just a)
     Just a  --definition of id

LHS = RHS

Maybe Law 2: fmap (f . g) (Maybe a) = fmap f (fmap g (Maybe a))

Nothing Case:

LHS: fmap (f . g) Nothing
     Nothing  --from definition of fmap on Nothing

RHS: fmap f (fmap g Nothing)
     fmap f Nothing  --from definition of fmap on Nothing
     Nothing  -from definition of fmap on Nothing
     
LHS = RHS     

Just a Case:

LHS: fmap (f . g) (Just a)
     Just ((f . g) a)  --definition of fmap on Just a
     Just (f(g a))  --definition of .

RHS: fmap f (fmap g (Just a))
     fmap f (Just (g a))  --definition of fmap on Just a
     Just (f(g a)) -- definition of fmap on Just a
     
LHS = RHS
QED

OOPS, I did the functor laws. I guess the applicative laws have to obey the functor laws anyways, so not a waste of time!

Applicative Law 1: fmap g (Maybe a) = pure g <*> (Maybe a)

Nothing Case:

LHS: fmap g Nothing
     Nothing  --from definition of fmap on Nothing

RHS: pure g <*> Nothing
     (Just g) <*> Nothing  --definition of pure
     Nothing  --definition of <*> on Nothing

LHS = RHS
QED


7. Prove the Applicative law given in the TCOP paper for the (Either a) type constructor using the function definitions in your Applicative instance of the (Either e) type constructor. No induction needed.



8. Prove the Applicative law given in the TCOP paper for the ((->) r) type constructor. No induction needed.
