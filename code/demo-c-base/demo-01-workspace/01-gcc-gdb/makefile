hello:hello.o
	g++ hello.o -o hello
	chmod +x hello
hello.o:hello.cc
	g++ -c -g -o hello.o hello.cc
clean:
	rm -f hello.o
	rm -f hello
