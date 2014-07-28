package com.adspitcher.dataobjects;

public class LocationDataObject {
	private int id;
	private String name, type;
	public LocationDataObject(int id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	
	
}
