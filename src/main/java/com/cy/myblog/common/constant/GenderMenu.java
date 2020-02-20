package com.cy.myblog.common.constant;

public enum GenderMenu {

	MALE(1, "男"),
	FEMALE(0, "女");
	private int value ; 
	private String name ;
	private GenderMenu(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
}
