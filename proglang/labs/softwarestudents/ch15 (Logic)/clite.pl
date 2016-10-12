/* get(var, inState, outValue) */
get(Var, [[Var, Val] | _], Val).
get(Var, [_ | Rest], Val) :- get(Var, Rest, Val).

/* onion(var, val, inState, outState) */
onion(Var, Val, [[Var, _] | Rest], [[Var, Val] | Rest]).
onion(Var, Val, [Xvar | Rest], [Xvar | OState]) :-
   onion(Var, Val, Rest, OState).

/* mexpression(expr, state, val) */
mexpression(value(Val), _, Val).
mexpression(variable(Var), State, Val) :- 
   get(Var, State, Val).
mexpression(plus(Expr1, Expr2), State, Val) :-
   mexpression(Expr1, State, Val1),
   mexpression(Expr2, State, Val2),
   Val is Val1 + Val2.

/* minstruction(statement, inState, outState) */
minstruction(skip, State, State).
minstruction(assignment(Var, Expr), InState, OutState) :-
    mexpression(Expr, InState, Val),
    onion(Var, Val, InState, OotState).
