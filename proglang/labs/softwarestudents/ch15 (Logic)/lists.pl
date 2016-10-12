append([], X, X). 
append([Head | Tail], Y, [Head | Z]) :- append(Tail, Y, Z). 

prefix(X, Z) :- append(X, Y, Z).
suffix(Y, Z) :- append(X, Y, Z).

member(X, [X | _]).
member(X, [_ | Y]) :- member(X, Y).
