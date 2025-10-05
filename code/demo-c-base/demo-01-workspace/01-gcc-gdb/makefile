OUTPUT = output/

main:main.o libhello.a # 可执行程序 （入口）
	g++ ${OUTPUT}main.o -l hello -L ${OUTPUT}/lib -o ${OUTPUT}main

main.o:main.cpp
	mkdir -pv ${OUTPUT}
	g++ -c -g -o ${OUTPUT}main.o -I lib/ main.cpp

libhello.a:hello.o # 静态库
	mkdir -pv ${OUTPUT}lib
	ar -r ${OUTPUT}lib/libhello.a ${OUTPUT}lib/hello.o

hello.o:lib/hello/core.cpp
	mkdir -pv ${OUTPUT}lib
	g++ -c -g -o ${OUTPUT}lib/hello.o lib/hello/core.cpp

clean:
	rm -rfv ${OUTPUT}
