package com.autoskola.instruktori.model;

public class NavDrawerItem {

	private String title, count;
	private Integer icon;

	public NavDrawerItem() {
	}

	public NavDrawerItem(String title, int icon, String count) {
		this.title = title;
		this.icon = icon;
		this.count = count;
	}
	
	public NavDrawerItem(String title, int icon) {
		this.title = title;
		this.icon = icon;
		this.count = "";
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getIcon() {
		return this.icon;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIcon(Integer icon) {
		this.icon = icon;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
