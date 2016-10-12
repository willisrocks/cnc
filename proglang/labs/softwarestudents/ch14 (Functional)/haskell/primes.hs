sieve (pr : lst) = pr : sieve [n | n <- lst, mod n pr /= 0]
primes = sieve [2..]
