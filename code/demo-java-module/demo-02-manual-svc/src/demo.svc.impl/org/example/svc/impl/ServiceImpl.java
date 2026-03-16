package org.example.svc.impl;

import org.example.svc.Service;

public class ServiceImpl implements Service {
  @Override
  public void use() {
    System.out.println("hello world from ServiceImpl");
  }
}
