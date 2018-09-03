package com.measure.rest;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.measure.entities.Measure;
import com.measure.model.RespMeasureByYear;
import com.measure.model.RespMeasureForYearByMonths;
import com.measure.repositories.RepoMeasure;
import com.measure.service.SrvcMeasure;
import com.measure.service.SrvcMeasureImpl;

@RestController
@RequestMapping("/rest")
public class RSMeasure {
	
	@Autowired 
	private SrvcMeasure srvcMeasure;
	
	@Autowired
	public RepoMeasure repoMeasure;
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping(value = "/getmeasurementforyear/{year}/{deviceId}", method = RequestMethod.GET)
	public String getMeasurementForYear(@PathVariable("year") String year, @PathVariable("deviceId") String deviceId, HttpServletRequest request) {
		return srvcMeasure.getSumOfMeasurementsForYearStr(request, deviceId, year);
	}
	
	@RequestMapping(value = "/getmeasurementforyearbymonths/{year}", method = RequestMethod.GET)
	public String getMeasurementForYearByMonths(@PathVariable("year") String year) {
		return srvcMeasure.getMeasurementsForYearByMonthsStr(year);
	}
	
	@RequestMapping(value = "/getmeasurementcountforyear/{year}", method = RequestMethod.GET)
	public String getMeasureCountForYear(@PathVariable("year") String year) {
		return srvcMeasure.getMeasurementCountForYearStr(year);
	}
	
	@RequestMapping(value = "/addmeasurement/{date}/{value}", method = RequestMethod.GET)
	public String addMeasurement(@PathVariable("date") String date, @PathVariable("value") String value, HttpServletRequest request) {
		return srvcMeasure.addMeasurement(date, value);
	}
	
	@RequestMapping(value = "/unregisterClient/{clientIp}/{deviceId}", method = RequestMethod.GET)
	public String unregisterClientAndDevice(@PathVariable("clientIp") String clientIp, @PathVariable("deviceId") String deviceId) {
		return srvcMeasure.removeClientIpAndDeviceId(clientIp, deviceId);
	}
	
}
