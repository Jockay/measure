package com.measure.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.measure.entities.Measure;
import com.measure.model.RespMeasureByYear;
import com.measure.model.RespMeasureCountForYear;
import com.measure.model.RespMeasureForYearByMonths;

public interface SrvcMeasure {

	public List<Measure> getAllMeasures();
	
	public List<Measure> getMeasuresByDateInterval(Date dateFrom, Date dateTo);
	
	public RespMeasureByYear getSumOfMeasuresForYear(String year);
	public String getSumOfMeasurementsForYearStr(HttpServletRequest request, String deviceId, String year);
	
	public RespMeasureForYearByMonths getMeasuresForYearByMonths(String year);
	public String getMeasurementsForYearByMonthsStr(String year);
	
	public RespMeasureCountForYear getMeasureCountForYear(String year);
	public String getMeasurementCountForYearStr(String year);

	public String addMeasurement(String date, String value);

	public boolean checkAndManageClient(HttpServletRequest request, String deviceId);
	
	public String removeClientIpAndDeviceId(String ip, String deviceId);
	
}
