/* valid(TrialRow, TrialSwDiag, TrialSeDiag, 
         Rowlist, SwDiagList, SeDiagList) */
valid(_, _, _, [ ]).

valid(TrialRow, TrialSwDiag, TrialSeDiag, 
         RowList, SwDiagList, SeDiagList) :-
     not(member(TrialRow, RowList)),
     not(member(TrialSwDiag, SwDiagList)),
     not(member(TrialSeDiag, SeDiagList)).

/* compute SeDiag, SwDiag */    
getDiag(Row, Col, SwDiag, SeDiag) :-
     SwDiag is Row + Col, SeDiag is Row - Col.

/* for current col, find safe row */
place(N, Row, Col, RowList, SwDiagList, SeDiagList, Row) :-
     Row < N,
     getDiag(Row, Col, SeDiag, SwDiag),
     valid(Row, SeDiag, SwDiag, RowList, SwDiagList, SeDiagList).
     
place(N, Row, Col, RowList, SwDiagList, SeDiagList, Answer) :-
     NextRow is Row + 1, 
     NextRow < N,
     place(N, NextRow, Col, RowList, SwDiagList, SeDiagList, Answer).

/* iterate over columns, placing queens */
solve(N, Col, RowList, _, _, RowList) :- Col >= N.

solve(N, Col, RowList, SwDiagList, SeDiagList, Answer) :- 
    Col < N,
    place(N, 0, Col, RowList, SwDiagList, SeDiagList, Row), 
    getDiag(Row, Col, SwDiag, SeDiag),
    NextCol is Col + 1,
    solve(N, NextCol, [Row | RowList], [SwDiag | SwDiagList],
        [SeDiag | SeDiagList], Answer).

queens(N, Answer) :- solve(N, 0, [ ], [ ], [ ], Answer).
