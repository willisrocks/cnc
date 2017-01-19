bsort(L, S) :- append(U, [A, B | V], L),
               B < A, !, 
               append(U, [B, A | V], M),
               bsort(M, S).
