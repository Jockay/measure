package com.measure.model;

import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class RespMeasureForYearByMonths {

	private String year;
	private Map<String, Integer> monthlyValues;
	
	public RespMeasureForYearByMonths(String year, Map<String, Integer> monthlyValues) {
		super();
		this.year = year;
		this.monthlyValues = monthlyValues;
		
	}
	
	public RespMeasureForYearByMonths() {
		super();
		init();
	}

	public void init() {
		this.monthlyValues = new LinkedHashMap<String, Integer>();
//		DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
//        String[] months = dfs.getMonths();
//        for(int i = 0; i < months.length; i++) {
//        	this.monthlyValues.put(months[i], 0);
//        }
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Map<String, Integer> getMonthlyValues() {
		return monthlyValues;
	}
	public void setMonthlyValues(Map<String, Integer> monthlyValues) {
		this.monthlyValues = monthlyValues;
	}
	
}
