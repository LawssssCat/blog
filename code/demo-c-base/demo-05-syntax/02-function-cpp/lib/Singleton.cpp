#include "Singleton.h"

Singleton* Singleton::m_instance = NULL; // 静态变量初始化需要在类定义外面完成
Singleton* Singleton::getInstance() {
  // todo 线程安全问题
  if (!m_instance)
    m_instance = new Singleton();
  return m_instance;
}
void Singleton::setName(const string& name) {
  m_name = name;
}
const string& Singleton::getName() {
  return m_name;
}