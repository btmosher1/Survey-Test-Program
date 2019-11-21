.phony:time
run:
		@chmod 755 .
		@javac Main.java
		@java Main
clean:
	@\rm -f *.class