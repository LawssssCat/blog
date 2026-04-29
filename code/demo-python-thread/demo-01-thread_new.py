# -*-* encoding:UTF-8 -*-
# 参考：
# https://liaoxuefeng.com/books/python/process-thread/thread/index.html
#
# Python的标准库提供了两个模块：
# + _thread —— 低级模块
# + threading —— 高级模块，对_thread进行了封装
# 绝大多数情况下，我们只需要使用threading这个高级模块。



import time, threading

def loop():
    # 返回当前线程的实例
    current_thread = threading.current_thread()
    print('thread %s is running...' % current_thread.name)
    n = 0
    while n < 5:
        n = n + 1
        print('thread %s >>> %s' % (threading.current_thread().name, n))
        time.sleep(1)
    print('thread %s ended.' % threading.current_thread().name)

main_thread = threading.current_thread()
print('thread %s is running...' % main_thread.name)
# 启动一个线程就是把一个函数传入并创建Thread实例，然后调用start()开始执行：
t = threading.Thread(target=loop, name='LoopThread')
t.start()
t.join()
print('thread %s ended.' % threading.current_thread().name)