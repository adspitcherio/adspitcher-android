package com.adspitcher.dataobjects;

public class BrandDataObject {
	
	private int id;
	private String name, username, password_digest, email;
	private int rating;
	private int location_id, store_id;
	public BrandDataObject(int id, String name, String username,
			String password_digest, String email, int rating, int location_id,
			int store_id) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password_digest = password_digest;
		this.email = email;
		this.rating = rating;
		this.location_id = location_id;
		this.store_id = store_id;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword_digest() {
		return password_digest;
	}
	public String getEmail() {
		return email;
	}
	public int getRating() {
		return rating;
	}
	public int getLocation_id() {
		return location_id;
	}
	public int getStore_id() {
		return store_id;
	}

	
}
