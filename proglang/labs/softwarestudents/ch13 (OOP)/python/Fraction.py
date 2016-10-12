class Fraction:
    def  __init__(self, numer=1, denomin=1):
        """constructor: default value is 1/1"""
        if denomin == 0:
            raise ZeroDivisionError, "Illegal 0 denominator"
        div = gcd(numer, denomin)
        self.num = abs(numer / div)
        self.denom = abs(denomin / div)
        if numer * denomin < 0: self.num = - self.num

    def numerator(self):
        return self.num

    def denominator(self):
        return self.denom

    def __add__(self, other):
        return Fraction(other.denom * self.num 
                        + self.denom * other.num, 
                        self.denom + other.denom)

    def  __neg__(self):
        return Fraction(- self.num, self.denom)

    def  __sub__(self, other):
        return - other + self

    def  __eq__(self, other):
        return (self.num == other.num and 
                self.denom == other.denom)

    def  __str__(self):
        return str(self.num) + "/" + str(self.denom)

def gcd(a, b):
    a = abs(a)
    b = abs(b)
    while b:
        c = a
        a = b
        b = c % b
    return a

def main( ):
    a = Fraction(1, 3)
    b = Fraction(-2, -6)
    c = Fraction(3, 9)
    d = a + b + c
    print d

main( )
