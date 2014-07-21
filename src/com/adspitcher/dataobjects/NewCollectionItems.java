package com.adspitcher.dataobjects;

import android.graphics.Bitmap;

public class NewCollectionItems {

	private Bitmap newcollection_icon;
	private String newcollection_text;
	private int newcollection_views;
	private int newcollection_reviews;
	private String newcollection_brandorstore;
	private String newcollection_location;
	private int newcollection_rating;
	
	public NewCollectionItems(String text, String brandorstore, String location, int views, int reviews, int rating){
		this.newcollection_text = text;
		this.newcollection_brandorstore = brandorstore;
		this.newcollection_location = location;
		this.newcollection_views = views;
		this.newcollection_reviews = reviews;
		this.newcollection_rating = rating;
	}

	public Bitmap getNewcollection_icon() {
		return newcollection_icon;
	}

	public void setNewcollection_icon(Bitmap newcollection_icon) {
		this.newcollection_icon = newcollection_icon;
	}

	public String getNewcollection_text() {
		return newcollection_text;
	}

	public void setNewcollection_text(String newcollection_text) {
		this.newcollection_text = newcollection_text;
	}

	public int getNewcollection_views() {
		return newcollection_views;
	}

	public void setNewcollection_views(int newcollection_views) {
		this.newcollection_views = newcollection_views;
	}

	public int getNewcollection_reviews() {
		return newcollection_reviews;
	}

	public void setNewcollection_reviews(int newcollection_reviews) {
		this.newcollection_reviews = newcollection_reviews;
	}

	public String getNewcollection_brandorstore() {
		return newcollection_brandorstore;
	}

	public void setNewcollection_brandorstore(String newcollection_brandorstore) {
		this.newcollection_brandorstore = newcollection_brandorstore;
	}

	public String getNewcollection_location() {
		return newcollection_location;
	}

	public void setNewcollection_location(String newcollection_location) {
		this.newcollection_location = newcollection_location;
	}

	public int getNewcollection_rating() {
		return newcollection_rating;
	}

	public void setNewcollection_rating(int newcollection_rating) {
		this.newcollection_rating = newcollection_rating;
	}
	
	
}
