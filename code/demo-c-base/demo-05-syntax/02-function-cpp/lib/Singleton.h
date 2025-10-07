#pragma once

#include <string>

using namespace std;

class Singleton {
private:
  static Singleton* m_instance;
  Singleton() {}
  ~Singleton() {}
public:
  Singleton(const Singleton& obj) = delete;
  Singleton& operator=(const Singleton&) = delete;
  static Singleton* getInstance();
private:
  string m_name;
public:
  void setName(const string& name);
  const string& getName();
};
