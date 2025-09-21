#ifndef ADD_HPP
#define ADD_HPP

int add(int a, int b);

#endif
// ADD_HPP —— 防止重复加载头文件
// 否则：
// multiple definition of `add(int, int)'; objs/add.o:xxxx/src/add.cpp:4: first defined here
