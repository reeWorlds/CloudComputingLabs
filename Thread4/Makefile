all: run

clean:
	rm -f out/Solver.jar out/Factorizer.jar

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf out/Solver.jar -C src Solver.class -C src
	@rm -f src/Solver.class

out/Factorizer.jar: out/parcs.jar src/Factorizer.java
	@javac -cp out/parcs.jar src/Factorizer.java
	@jar cf out/Factorizer.jar -C src Factorizer.class -C src
	@rm -f src/Factorizer.class

build: out/Solver.jar out/Factorizer.jar

run: out/Solver.jar out/Factorizer.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver