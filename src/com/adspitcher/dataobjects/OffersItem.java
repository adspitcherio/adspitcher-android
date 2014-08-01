package com.adspitcher.dataobjects;

import android.graphics.Bitmap;

public class OffersItem {
	
	private Bitmap offer_icon;
	private String offer_text;
	private int offer_views;
	private int offer_reviews;
	private String offer_brandorstore;
	private String location;
	private int votesup, votesdown;
	private String startDate, endDate;
	private boolean votedUp, votedDown;
	
	public OffersItem(String text, String brandorstore, String location, int views, int reviews, int ups, int downs){
		this.offer_text = text;
		this.offer_brandorstore = brandorstore;
		this.location = location;
		this.offer_views = views;
		this.offer_reviews = reviews;
		this.votesup = ups;
		this.votesdown = downs;
		setVotedUp(false);
		setVotedDown(false);
	}
	
	public Bitmap getOffer_icon() {
		return offer_icon;
	}
	public void setOffer_icon(Bitmap offer_icon) {
		this.offer_icon = offer_icon;
	}
	public String getOffer_text() {
		return offer_text;
	}
	public void setOffer_text(String offer_text) {
		this.offer_text = offer_text;
	}
	public int getOffer_views() {
		return offer_views;
	}
	public void setOffer_views(int offer_views) {
		this.offer_views = offer_views;
	}
	public int getOffer_reviews() {
		return offer_reviews;
	}
	public void setOffer_reviews(int offer_reviews) {
		this.offer_reviews = offer_reviews;
	}
	public int getVotesup() {
		return votesup;
	}
	public void setVotesup(int votesup) {
		this.votesup = votesup;
	}
	public int getVotesdown() {
		return votesdown;
	}
	public void setVotesdown(int votesdown) {
		this.votesdown = votesdown;
	}

	public String getOffer_brandorstore() {
		return offer_brandorstore;
	}

	public void setOffer_brandorstore(String offer_brandorstore) {
		this.offer_brandorstore = offer_brandorstore;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public boolean isVotedUp() {
		return votedUp;
	}

	public void setVotedUp(boolean votedUp) {
		this.votedUp = votedUp;
	}

	public boolean isVotedDown() {
		return votedDown;
	}

	public void setVotedDown(boolean votedDown) {
		this.votedDown = votedDown;
	}
	
	

}
