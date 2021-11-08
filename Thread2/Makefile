all: run

clean:
	rm -f out/Solver.jar out/BigStepBabyStep.jar

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf out/Solver.jar -C src Solver.class -C src
	@rm -f src/Solver.class

out/BigStepBabyStep.jar: out/parcs.jar src/BigStepBabyStep.java
	@javac -cp out/parcs.jar src/BigStepBabyStep.java
	@jar cf out/BigStepBabyStep.jar -C src BigStepBabyStep.class -C src
	@rm -f src/BigStepBabyStep.class

build: out/Solver.jar out/BigStepBabyStep.jar

run: out/Solver.jar out/BigStepBabyStep.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver