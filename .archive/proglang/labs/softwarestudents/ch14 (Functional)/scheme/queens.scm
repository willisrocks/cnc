(define (tryone move soln)
  (let ((xsoln (cons move soln)))
    (if (valid move soln)
	(if (done xsoln)  (cons #t xsoln)
	    (try xsoln))
	(cons #f soln)
)))

(define (trywh move soln)
  (if (and (hasmore move) (not (car soln)))
      (let ((atry (tryone move (cdr soln))))
	(if (car atry) atry (trywh (nextmove move) soln))
	)
      soln
))

(define (try soln) (trywh 1 (cons #f soln)))

;;; end general backtrack algorithm

;;; begin specialization for N queens

(define (hasmore move) (<= move N))

(define (nextmove move) (+ move 1))

(define (done soln) (>= (length soln) N))

(define N 8)

(define (swDiag row col) (+ row col))

(define (seDiag row col) (- row col))

(define (selist alist)
  (if (null? alist)
      '()
      (cons (seDiag (car alist) (length alist)) (cdr alist))))

(define (swlist alist)
  (if (null? alist)
      '()
      (cons (swDiag (car alist) (length alist)) (cdr alist))))

(define (valid move soln)
  (let ((col (length (cons move soln))))
    (and (not (member move soln))
	 (not (member (seDiag move col) (selist soln)))
	 (not (member (swDiag move col) (swlist soln)))
)))
