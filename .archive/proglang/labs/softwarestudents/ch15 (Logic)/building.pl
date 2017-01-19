building(Floors) :- floors(Floors),
        member(floor(baker, B), Floors), B \= 5, 
        member(floor(cooper, C), Floors), C \= 1,
        member(floor(fletcher, F), Floors), F \= 1, F \= 5,
        member(floor(miller, M), Floors), M > C,
        member(floor(smith, S), Floors), not(adjacent(S, F)),
        not(adjacent(F, C)),
        print_floors(Floors).

print_floors([A | B]) :- write(A), nl, print_floors(B).
print_floors([]).

member(X, [X | _]).
member(X, [_ | Y]) :- member(X, Y).

adjacent(X, Y) :- X =:= Y+1.
adjacent(X, Y) :- X =:= Y-1.
