;;; A State is implemented as a list of ordered pairs,
;;; of the form (variable value).  E.g. the initial state
;;; for int x; int y would be:
;;;   '((x 0) (y 0))
;;; we assume that EVERY variable occurs EXACTLY once in the state

(define (get id sigma)
  ; typical state ((x 1) (y 3) (z 5))
  ; need not check for (null? sigma) -- why?
  (if (equal? id (caar sigma))  (cadar sigma)  ; why not cdar?
      (get id (cdr sigma))
  ))

(define (onion id val sigma)
  ;  typical state ((x 1) (y 3) (z 5))
  ;  why not (null? sigma)
  (if (equal? id (caar sigma))
      (cons (list id val) (cdr sigma))
      (cons (car sigma) (onion id val (cdr sigma)))
  ))

;;; Defines an Expression to be:
;;; 1) (value val), where val is either a number, #t, #f
;;; 2) (variable  id), where id is a variable
;;; 3) (operator left  right), where operator is one of:
;;;     plus minus times div -- arithemtic
;;;     lt, le, eq, ne, gt, ge -- relational

(define (m-expression expr  sigma)
  (case (car expr)
        ((value)  (cadr  expr))
        ((variable)  (get (cadr expr) sigma))
        (else  (applyBinary (car expr) (cadr expr) (caddr expr) sigma))
  ))

(define (applyBinary op left right sigma)
  (let  ((leftval (m-expression left sigma))
         (rightval (m-expression right sigma)))
    (case op
      ((plus)   (+ leftval rightval))
      ((minus)  (- leftval rightval))
      ((times)  (* leftval rightval))
      ((div)    (/ leftval rightval))
      (else  #f)
    )))

;;; Instructions are:
;;; (skip)
;;; (assign target source)
;;; (compound  s1 ... sn)
;;; (loop test body)
;;; (conditional test thenbr elsebr)

(define (m-statement  statement  sigma)
  (case (car statement)
        ((skip)  (m-skip statement sigma))
        ((assign) (m-assign statement sigma))
        ((compound) (m-compound (cdr statement) sigma))
        ((loop) (m-loop statement sigma))
        ((conditional) (m-conditional statement sigma))
        (else  ())
  ))

;;; (assign target source)
(define (m-assign  statement  sigma)
  ; wrong -- you fix
  (let ((target (cadr statement)) (source (caddr statement)))
    (onion ???)
  )
)
