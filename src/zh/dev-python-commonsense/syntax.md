---
title: Python 语法
---

## 临时变量

[Walrus Operator，海象运算符](https://www.youtube.com/watch?v=aGitW09mXEA) —— 用来简化赋值

```py
# 基本语法
(a := 1)
print(a)

# 例子
l = [1, 2, 3]
# length = len(l)
if (length := len(l)) > 0:
  print(f"list is not empty. Its size is {length}")

# 例子
while (cmd := input()) != "exit":
  print(f"Got Input {cmd}")

# 例子
# is_even = True
# for i in l:
#   if i % 2 != 0:
#     is_even = False
#     break
is_even = all((n := i) % 2 == 0 for i in l)
print(f"偶数： {is_even}")
print(f"非偶数值： {n}")
```

## 类型标注

基本类型

```py
# 类型标注不影响程序运行，类型错误可以用 mypy 检查
# pip3 install mypy
# mypy test.py

# from typing import Union
# from typing import Optional

# Num = Union[int, float]
Num = int | float # 语法糖

# def add(a: Union[int, float], b: Num) -> int:
def add(a: int | float, b: Num) -> int | float | None: # Optional[Num]
  return a + b

add(1, 2)
add(1.1, 2)

# from typing import List # 允许append新增
# from typing import Sequence # 不允许append新增
# num_list: List[Num] = [1, 2, 3]
num_list: list[Num] = [1, 2, 3]
# num_list: Sequence[Num] = [1, 2, 3]
```

泛型

```py
from typing import List, TypeVar, Generic

class Animal:
  pass
class Dog(Animal):
  pass
class Cat(Animal):
  pass

AnimalType = TypeVar("AnimalType", # 要求一样
 bound=Animal,
 covariant=True, # 允许协变
 contravariant=True # 允许逆变
)

class Store(Generic[AnimalType]):
  def __init__(self, stock: List[AnimalType]) -> None:
    self.stock = stock
  def buy(self) -> AnimalType:
    return self.stock.pop()

wang = Store[Animal]([Dog(), Cat()]) # 允许动物
print(wang.buy())

li = Store[Dog]([Dog()]) # 只允许狗
print(li.buy())
```

## 遍历器`iterator`

实现 `__getitem__` 方法

```py
class MyList:
  def __init__(self, lst):
    self.lst = lst
  def __getitem__(self, index):
    if index >= len(self.lst):
      raise IndexError()
    return self.lst[index]

my_list = MyList([2, 3, 3])
for i in my_list:
  print(i)
```

实现 `__iter__` 方法

```py
class MyList:
  def __init__(self, lst):
    self.lst = lst
  def __iter__(self):
    return MyListIterator(self)

class MyListIterator:
  def __init__(self, my_list: MyList):
    self.my_list = my_list
    self.index = 0
  def __next__(self):
    if self.index >= len(self.my_list.lst):
      raise StopIteration()
    result = self.my_list.lst[self.index]
    self.index += 1
    return result
  def __iter__(self): # 适配方式2遍历
    return self

my_list = MyList([2, 3, 3])

# 方式0： 底层方法调用
# it = my_list.__iter__()
# try:
#   while True:
#     i = it.__next__()
#     print(i)
# except StopIteration:
#   pass

# 方式1： 直接调用 iter 调用
for i in my_list:
  print(i)

# 方式2： 通过遍历器遍历
# it = my_list.__iter__()
it = iter(my_list)
for i in it:
  print(i)
```

## 生成器`generator`

```py
for i in range(2):
  print(i)


def myrange(n):
  i = 0
  result = [] # 问题：占用内存，应该返回 range 类型sss
  while i<n:
    result.append(i)
    i += 1
  return result
for i in myrange(2):
  print(i)


# 实现方式 1 用iterator类
class myrange:
  def __init__(self, n):
    self.n = n
  def __iter__(self):
    return MyRangeIter(self.n)
class MyRangeIter:
  def __init__(self, n):
    self.n = n
    self.current = 0
  def __next__(self):
    if self.current >= self.n:
      raise StopIteration()
    result = self.current
    self.current += 1
    return result
for i in myrange(2):
  print(i)


# 实现方式 2 用generator类
def myrange(n):
  # print("当 __next__() 时才执行")
  i = 0 # 在__next__时才首次进入
  while i<n:
    send_msg = yield i # 返回generator类
    # print(f"send_msg: {send_msg}")
    i += 1
for i in myrange(2):
  print(i)
# 测试
xx = myrange(2)
it = xx.__iter__()
print(it.__next__()) # 1
# xx.send("hello world")
print(it.__next__()) # 2
print(it.__next__()) # raise StopIteration
```

## 装饰器`Decorator`

```py
import time

def timing(f):
  def wrapper(*args, **kwargs): # 可变参数
    s = time.time()
    r = f(*args, **kwargs)
    e = time.time()
    print(f"Total time: {e - s}")
    return r
  return wrapper

@timing
def hello():
  print("hello world")

# hello = timing(hello) # 等于 @timing 语法糖
hello()
```
