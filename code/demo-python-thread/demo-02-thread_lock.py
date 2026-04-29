# -*-* encoding:UTF-8 -*-
import time, threading

# 假定这是你的银行存款:
balance = 0

def run_thread(n):
  global balance
  current_thread = threading.current_thread()
  for i in range(1000):
    time.sleep(0.001)
    print(f"[{current_thread.name}][{i}]add:{n}")
    balance = balance + n
    # time.sleep(0.001)
    b = balance
    print(f"[{current_thread.name}][{i}]del:{n}")
    balance = b - n

ts = [
  threading.Thread(target=run_thread, name="t8", args=(8,)),
  threading.Thread(target=run_thread, name="t7", args=(7,))
]
for t in ts:
  t.start()
s = time.time()
for t in ts:
  t.join()
e = time.time()
print(f"{int(e - s)}s:{balance}")