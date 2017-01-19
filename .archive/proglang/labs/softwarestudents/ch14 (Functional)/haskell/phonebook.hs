type Entry = (Person, Number)
type Person = String
type Number = Integer

type Phonebook = [(Person, Number)]

find :: Phonebook -> Person -> [Number]
find pb p = [n | (person, n) <- pb, person == p]

addEntry :: Phonebook -> Person -> Number -> Phonebook
addEntry pb p n = [(p,n)] ++ pb

deleteEntry :: Phonebook -> Person -> Number -> Phonebook
deleteEntry pb p n = [entry | entry <- pb, entry /= (p, n)]
