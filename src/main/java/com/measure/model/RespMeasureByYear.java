package com.measure.model;

public class RespMeasureByYear {

	private String year;
	private Integer total;
	
	public RespMeasureByYear(String year, Integer total) {
		super();
		this.year = year;
		this.total = total;
	}
	public RespMeasureByYear() {
		super();
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
