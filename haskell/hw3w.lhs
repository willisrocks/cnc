> import Prelude hiding (Maybe(..), Either(..))

1. Use the newtype facility in Haskell to define your own MyIO type as follows This will allow you to code your own IO type as a functor and run the code.

> newtype MyIO a = MyIO (IO a)

Now Give an appropriate Haskell code for a map function over your MyIO a type. Don't forget to unwrap the MyIO on the input action and wrap the MyIO around the output action sequence.

> unwrap (MyIO a) = a
> wrap a = MyIO a

> instance Functor MyIO where
>   fmap f x = wrap mappedInner where
>     inner = unwrap x
>     mappedInner = inner >>= (pure . f)

2. Recode your MyIO map function using bind (>>=).

3. Make your type constructor MyIO an instance of functor. Test your code, of course.

4. Give an appropriate Haskell code for a map function over the (a,b) type. Note: that (a,b) is the pair type constructor (,) applied to type variables a and b, that is, (a,b) is equivalent to the prefix form ((,) a b).

> pMap f (a, b) = (f a, f b) 

5. Make the type constructor ((,) a) an instance of functor class. Note as above, you may get a "multiple instance declaration" error because the system has already defined ((,) a) as an instance of the functor class. Use the newtype workaround discussed above for the IO type constructor. Note I did not have to do this.

> newtype Pair a  = Pair (a,a)
>   deriving (Show)

> instance Functor Pair where
>   fmap f (Pair (a,b)) = Pair (f a, f b)

** Had to change the kind of Pair to * -> * from * -> * -> *!

6. Give an appropriate Haskell code for a map function over the ((->) r) type.

7. Make the type constructor ((->) r) an instance of functor class. Note again, you might need to use the newtype workaround. Note I did not have to do this.

> --instance Functor ((->) r) where
>   --fmap f g = (\x -> f (g x))


Pointed Programming Problems

8. Define a class called Pointed following the Typeclassopedia paper (the original version of the paper linked on the main page). You'll need to define Pointed with Functor as a context.

> class Functor f => Pointed f where
>   mypure :: a -> f a

9. Define an instance of Pointed for the type constructor Maybe given in Hw1. Remember you'll need to import Prelude hiding Maybe(..) and define the Maybe data type as you did in Hw1. You'll also have to include your instance Functor Maybe from Hw1.

> data Maybe a = Nothing | Just a deriving (Show)

> instance Functor Maybe where
>   fmap _ Nothing = Nothing
>   fmap f (Just a) = Just (f a)

> instance Pointed Maybe where
>   mypure = Just

10. Define an instance of Pointed for the type constructor (Either a) given in Hw1. Again you'll need to import Prelude hiding Either(..) and define the Either data type as you did in Hw1. You'll also have to include your instance Functor (Either a) from Hw1.

> data Either a b = Left a | Right b deriving (Show)

> instance Functor (Either a) where
>   fmap f (Right b) = Right (f b)

> instance Pointed (Either a) where
>   mypure = Right

11. Define an instance of Pointed for the type constructor ((->) r). Use your Arrow newtype above if needed.

> --instance Pointed ((->) r) where
>   --mypure = id

12. Explain the purpose of the Pointed class.

The purpose of the Pointed class is to give a type class for things that can put a value into a default context.

Functor and Pointed Proofs

13. Finish the proof of the (comp) functor law for the Tree data type constructor.

For each of these proofs you'll need to use the function definitions you gave for the class instances. Include in this assignment your code for the appropriate class definitions and class instance declarations. Carefully label your function definitions and clearly refer to them in your proofs as proof-step justifications.

14. Prove the Pointed law for the Maybe type constructor holds for the definitions of fmap and pure you gave for your Maybe type constructor. No induction needed.

15. Prove the Functor laws for the ((,) a) type constructor. No induction needed.

16. Prove the Functor laws for the ((-> r) type constructor. No induction needed.

17. Prove the Pointed law for the (Either a) type constructor. No induction needed.

18. Prove the Pointed law for the ((-> r) type constructor. No induction needed.

19. Explain why you can't make ((,) a) an instance of the Pointed class.
