package com.cy.test;

import org.junit.jupiter.api.Test;

public class TestCommon {
	@Test
	public void test_null() {
		System.out.println(null!=null);
	}
	
	@Test
	public void test_split() {
		String str = "/abc" ;
		String[] ss = str.split("/");
		System.out.println("length="+ss.length);
		for (String s : ss) {
			System.out.println(s);
		}
	}
}
