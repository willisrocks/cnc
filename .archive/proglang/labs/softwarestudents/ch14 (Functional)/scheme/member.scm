(define (member elt alist)
  (if (null? alist) '()
      (if (equal? elt (car alist)) alist
          (member elt (cdr alist))
)))
