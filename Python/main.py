import math as math
from Pyro4 import expose


class Solver:
	def __init__(self, workers=None, input_file=None, output_file=None):
		self.input_file_name = input_file
		self.output_file_name = output_file
		self.workers = workers
		print("Inited")

	def solve(self):
		print("Start")

		v, n = self.read_input()
		print("v = " + str(v))
		print("n = " + str(n))

		root = int(math.sqrt(n))
		print("root = " + str(root))

		workersLen = 1
		if self.workers is not None:
			workersLen = len(self.workers)

		mapped = []
		for i in xrange(0, workersLen):
			l = root * i / workersLen + 2
			r = root * (i + 1) / workersLen + 1
			print("i = " + str(i) + "  l = " + str(l) + "  r = " + str(r))

			#mapped.append(self.findDivider(n, l, r))
			mapped.append(self.workers[i].findDivider(n, l, r))

		p = self.myreduce(mapped)
		q = n / p

		print(str(p) + " * " + str(q) + " = " + str(n))

		x1 = self.solveMod(v, p)
		x2 = self.solveMod(v, q)

		print("x1 = " + str(x1) + "\nx2 = " + str(x2))

		if x1 is None or x2 is None:
			self.write_output("NO")
		else:
			g, l1, l2 = self.gcdExtended(p, q)

			y1 = (l1 * p * x2 + l2 * q * x1) % n
			y2 = (l1 * p * x2 - l2 * q * x1) % n
			y3 = n - y1
			y4 = n - y2
			self.write_output("YES", y1, y2, y3, y4)

	def solveMod(self, v, p):
		a = v % p
		if pow(a, (p - 1) / 2, p) == 1:
			return pow(a, (p + 1) / 4, p)
		else:
			return None

	def gcdExtended(self, a, b):
		if a == 0:
			return b, 0, 1

		gcd, x1, y1 = self.gcdExtended(b % a, a)

		x = y1 - (b / a) * x1
		y = x1

		return gcd, x, y

	@staticmethod
	@expose
	def findDivider(n, l, r):
		res = -1
		l = int(l / 5040) * 5040
		r = int(r / 5040 + 1) * 5040

		primes = Solver.getPrimes(5040)

		i = l
		while i < r:
			for pr in primes:
				num = i + pr
				if n % num == 0:
					res = num
			i += 5040

		return res

	@staticmethod
	@expose
	def getPrimes(n):
		primes = [1]

		for i in xrange(2, n):
			j = 2
			isPrime = True
			while j * j <= i:
				if i % j == 0:
					isPrime = False
					break
				j += 1
			if isPrime:
				primes.append(i)

		return primes

	@staticmethod
	@expose
	def myreduce(mapped):
		p = -1
		for x in mapped:
			if x.value > 1:
				p = x.value

		return p

	def read_input(self):
		f = open(self.input_file_name, 'r')

		v = int(f.readline())
		n = int(f.readline())

		f.close()

		return v, n

	def write_output(self, result, y1=None, y2=None, y3=None, y4=None):
		f = open(self.output_file_name, 'w')

		if result == "NO":
			f.write("NO\n")
		else:
			f.write("YES\n")
			f.write("y1 = " + str(y1) + "\n")
			f.write("y2 = " + str(y2) + "\n")
			f.write("y3 = " + str(y3) + "\n")
			f.write("y4 = " + str(y4) + "\n")

		f.close()