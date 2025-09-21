hello:hello.o
	g++ hello.o -o hello.out
	chmod +x hello.out
hello.o:hello.cc
	g++ -c -g -o hello.o hello.cc
clean:
	rm -f *.o
