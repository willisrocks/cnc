class Polynomial:
	def  __init__(self, coef):
		"""constructor"""
		self.coefficient = [ ] + coef
	
	def  degree(self):
		"""Highest power with a non-zero coefficient"""
		return len(coefficient)
		
	def  coefficient(self, power):
		"""Coefficient of given power"""
		if power > len(coefficient):
			return 0
		return coefficient[power]
		
	def  asList(self):
		"""return copy of coefficient"""
		return [ ] + coefficient
		
	def  __eq__(self, aPoly):
		"""return self == aPoly"""
		return coefficient == aPoly.asList( )
		
	def  __ne__(self, aPoly):
		"""return self <> aPoly.asList( )"""
		return coefficient <> aPoly.asList( )
		
	def  __str__(self):
		"""return string representation"""
		r = ""
		p = len(coefficient) + 1
		while p > 0:
			p = p - 1
			if coefficient[p] == 0: continue
			if p < len(coefficient): r = r + "+"
			r = r + str(coefficient[p])
			if p == 0: continue
			r = r + "x"
			if p <= 1: continue
			r = r + "^" + str(p)
		return r
