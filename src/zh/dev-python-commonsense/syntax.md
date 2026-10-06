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

## 弱引用

```py
import random
import weakref

# id_user = {}
id_user = weakref.WeakValueDictionary() # value 是弱引用 —— 当 value 没有引用时，对应的 entry 被删除
# user_id = weakref.WeakKeyDictionary() # key 是弱引用 —— 当 key 没有引用时，对应的 entry 被删除
# s = weakref.WeakSet() # 弱引用集合
# u_ref = weakref.ref(u) # 获得 u 的弱引用

class User:
  def __init__(self):
    self._id = random.randint(0, 1000)
    while self._id in id_user:
      self._id = random.randint(0, 1000)
    id_user[self._id] = self

def chat_room():
  u1 = User()
  u2 = User()

  # Chat ...

  # del id_user[ul._id]
  # del id_user[u2._id]

chat_room()

for i, u in id_user.items():
  print(f"{i} {u}")
```

## 上下文管理器（Context Manager）

```py
import os

# 写法1
class CtxManager:
  def __int__(self, old_path):
    self.old_path = old_path
  def __enter__(self):
    print("enter __enter__")
    return self.old_path # 返回的是 as 后面引用的值
  def __exit__(self,
    exc_type, # 异常类型
    exc_value, # 异常实例
    traceback # 异常堆栈
  ):
    os.chdir(self.old_path)
    if exc_type is ZeroDivisionError: # 如果有异常，返回True表示异常已经被处理
      return True
    return False
def change_dir(path):
  old_path = os.getcwd()
  os.chdir(path)
  return CtxManager(old_path)

# 写法2
from contextlib import contextmanager
@contextmanager
def change_dir(path):
  old_path = os.getcwd()
  os.chdir(path)
  try:
    yield old_path
  except ZeroDivisionError:
    pass
  os.chdir(old_path)

# 使用
tmp = change_dir("/tmp")
with change_dir as old_dir:
  print(f"old dir {old_dir}")
  print(f"current dir {os.getcwd()}")
print(f"After with dir is {os.getcwd()}")
```

## 模块管理

包导入

```py title="mymath.py"
略
```

```py title="main.py"
import mymath as mm
import mymath as mm2

print(mm.add(1, 2))
print(type(mm))
print(id(mm1) == id(mm2)) # true
```

包查找

```py title="main.py"
from pprint import pprint

pprint(sys.path) # 可以看到module查找路径

PYTHONPATH=/a/b/c python main.py # 指定环境变量将路径加到module查找路径中

import xx # 基于上述路径，会找 xx.py 或者 xx/ （找到目录的话，如果有 __init__.py 会被执行）
print(dir()) # 可以看到 xx 目录
print(dir(xx)) # 可以看到 xx 目录内部有什么目录
```

包编写

```py title="xx/a.py"
# 导入当前模块的子模块方法
import xx.subdir       # 相对项目根目录
import . import subdir # 相对当前文件目录
import .. import main
```

## 日志模块

略，AI
<https://www.youtube.com/watch?v=f7yS61tTxuc>

```py
import logging

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(name)s - %(levelname)s')

logging.debug("debug")
logging.info("info")
logging.warning("warning")
logging.error("error")
logging.critical("critical")

# 记录异常
try:
  1 / 0
except:
  logging.exception("Get Exception")
```

loguru库

```py
# 装包
pip install loguru

# 引包
from loguru import logger
import sys

# 配置
logger.remove() # 关闭控制台打印
logger.add(sys.stdout, format="{time} - <level>{level}</level> - <YELLOW>{message}</YELLOW>") # 自定义控制台输出
handler_id = logger.add("msg.log", level="ERROR", format="{time} - {level} - {message}") # 开启文件输出
# logger.remove(handler_id) # 关闭指定输出

# 日志打印
logger.info("info msg")
logger.warning("warning msg")
logger.error("error msg")
logger.critical("critical msg")

# 添加自定义信息 —— 方式 1
child = logger.bind(foo="bar") # 在 format 中使用 `{extra}` 声明使用
child.info("msg xxxxxxx")
# 添加自定义信息 —— 方式 2
with logger.contextualize(foo="bar"):
  logger.info("msg xxxxxxxxx")
# 添加自定义信息 —— 方式 3
@logger.contextualize(foo="bar")
def xxx():
  logger.info("msg xxxxxxxxx")
xxx()


# 异常记录
# 1
try:
  1 / 0
except:
  logger.exception("xxxx")
# 2
with logger.catch(ZeroDivisionError, level="WARNING"):
  1 / 0
# 3
@logger.catch()
def test():
  1 / 0
test()
```

## 调试工具

pdb <https://www.youtube.com/watch?v=rUygQQ8Dxv8>

```py
python -m pdb main.py

help # 列出pdb命令

回车 # 执行上一个命令

list # 看代码
list . # 查看当前代码

next # 执行当前语句

step # 步入函数

continue # 执行到断点
break # 列出所有断点
break main.py:4 # 设置断点
clear # 清理断点
clear 1 # 清理第一个断点
disable 1 # 临时关闭断点
enable 1 # 重新开启断点

until 7 # 执行到第7行

p i # 查看变量i的值
p [i, n, nums] # 支持python语法，这里用数组形式查看 i n nums 的变量取值
p globals() # 查看全局变量
pp global() # （格式化）查看全局变量

where # 查看当前命令所在堆栈
up # 切换到上一个堆栈环境 （不改变语句执行）
down # 切换到下一个堆栈环境 （不改变语句执行）
```

## 调用C语言

Cython 调用C语言
<https://www.youtube.com/watch?v=28nOTHMUcco>
