package com.adspitcher.dataobjects;

public class CategoryDataObject {

	private int id;
	private String name;
	public CategoryDataObject(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	
}
