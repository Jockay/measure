package com.measure.model;

public class RespMeasureCountForYear {

	private String year;
	private Integer count;
	
	public RespMeasureCountForYear(String year, Integer count) {
		super();
		this.year = year;
		this.count = count;
	}
	public RespMeasureCountForYear() {
		super();
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
