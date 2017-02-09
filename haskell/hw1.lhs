> import Prelude hiding (Maybe(..), Either(..))

1.  Use the following Error data type for this problem.

> data Maybe a = Nothing | Just a
>   deriving (Show)

  (a) Give the type and Haskell code for an appropriate map function over
      the (Maybe a) type.

> mapMaybe :: (a -> b) -> Maybe a -> Maybe b

> mapMaybe _ Nothing = Nothing
> mapMaybe g (Just a) = Just (g a)

  (b) Declare the Maybe type constructor as an instance of the Functor class.

> instance Functor Maybe where
>   fmap f Nothing = Nothing
>   fmap f (Just a) = Just (f a)

2.  Use the following Error data type for this problem.

> data Error a = Ok a | Error String
>   deriving (Show)

  (a) Give the type and Haskell code for an appropriate map function over
      the (Error a) type.

> mapError :: (a -> b) -> Error a -> Error b

> mapError _ (Error s) = Error s
> mapError g (Ok a) = Ok (g a)

  (b) Declare the Error type constructor as an instance of the Functor class.

> instance Functor Error where
>   fmap _ (Error s) = Error s
>   fmap f (Ok a) = Ok (f a)


3. Consider the following binary tree data type.

> data Tree a = Nil | Node a (Tree a) (Tree a)
> 	    deriving (Eq, Read,Show)

  (a) Give the type and function definition for mapT, the map function over the
      binary tree data type above

> --mapT :: 

> mapT f Nil = Nil
> mapT f (Node a left right) = Node (f a) (mapT f left) (mapT f right)

  (b) Declare the Tree type constructor as an instance of the Functor class.

> instance Functor Tree where
>   fmap _ Nil = Nil
>   fmap f (Node a left right) = Node (f a) (fmap f left) (fmap f right)

4. Consider the following General Tree data type.

> data GTree a = GNil | GNode a [GTree a]

  (a) Give a type and a code definition for a mapGT function over GTree. Give
      the most general type.


> --mapGT :: 

> --mapGT 
> --mapGT 

  (b) Declare the Gtree type constructor as an instance of the Functor class.




5. Consider the (Either a b) data type.

> data Either a b = Left a | Right b


  (a) Give a type and a code definition for a mapEither function over the
      Either type. This is a bit tricky because the Either type constructor
      takes two parameters and the mapEither will only work on one of them.
      Recall that (Either a b) means ((Either a) b).

> --mapEither :: 

> --mapEither 
> --mapEither 


  (b) Declare the (Either a) type constructor as an instance of the Functor
      class.


