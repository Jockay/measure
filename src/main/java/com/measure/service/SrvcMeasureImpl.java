package com.measure.service;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.measure.entities.Client;
import com.measure.entities.Measure;
import com.measure.model.RespMeasureByYear;
import com.measure.model.RespMeasureCountForYear;
import com.measure.model.RespMeasureForYearByMonths;
import com.measure.repositories.RepoClients;
import com.measure.repositories.RepoMeasure;

@org.springframework.stereotype.Service
public class SrvcMeasureImpl implements SrvcMeasure {

	@Autowired
	public RepoMeasure repoMeasure;
	
	@Autowired
	public RepoClients repoClients;
	
	public String[] monthNames;
	
	@Autowired
	public void initMonthNames() {
		DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
        this.monthNames = dfs.getMonths();
	}
	
	public List<Measure> getAllMeasures() {
		return repoMeasure.findAll();
	}
	
		
	public List<Measure> getMeasuresByDateInterval(Date dateFrom, Date dateTo) {
//		return null;
		return repoMeasure.find(dateFrom, dateTo);
	}
	
	public List<Measure> getMeasuresForYear(String year) {
		try {
			String strDateTo = year + "-12-31T23-59-59";
			return repoMeasure.find(new SimpleDateFormat("yyyy").parse(year), new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss").parse(strDateTo));
		} catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Measure>();
		}
	}
	
	public RespMeasureByYear getSumOfMeasuresForYear(String year) {
		try {
			List<Measure> response = getMeasuresForYear(year);
			return new RespMeasureByYear(year, sumMeasureValues(response));
		} catch(Exception e) {
			e.printStackTrace();
			return new RespMeasureByYear();
		}
		
	}
	
	
	
	public String getSumOfMeasurementsForYearStr(HttpServletRequest request, String deviceId, String year) {
		if( ! checkAndManageClient(request, deviceId)) {
			return "The clientIp (" + request.getRemoteAddr() + ") and deviceId (" + deviceId + ") pair already uses the application. You have to unregister this pair, to use your current clientIp and deviceId pair."; 
		}
		RespMeasureByYear response = getSumOfMeasuresForYear(year);
		return "year = " + response.getYear() + ", total = " + response.getTotal();
	}
	
	public Integer sumMeasureValues(List<Measure> list) {
		Integer result = 0;
		for(Measure m : list) {
			result += m.getMeasureValue();
		}
		return result;
	}
	
	public RespMeasureForYearByMonths getMeasuresForYearByMonths(String year) {
		try {
			RespMeasureForYearByMonths result = new RespMeasureForYearByMonths();
			result.setYear(year);
			List<Measure> response = getMeasuresForYear(year);
			for(int i = 1; i <= 12; i++) {
				List<Measure> monthMeasures = getMeasuresForMonth(response, year, i);
				Integer sumOfMonthMeasures = sumMeasureValues(monthMeasures);
				result.getMonthlyValues().put(monthNames[i-1], sumOfMonthMeasures);
			}
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			return new RespMeasureForYearByMonths();
		}
	}
	
	public String getMeasurementsForYearByMonthsStr(String year) {
		RespMeasureForYearByMonths response = getMeasuresForYearByMonths(year);
		
		StringBuilder result = new StringBuilder("");
		result.append("year = " + response.getYear());
		for(String key : response.getMonthlyValues().keySet()) {
			result.append(", " +  key + " = " + response.getMonthlyValues().get(key));
		}
		
		return result.toString();
	}
	
	public List<Measure> getMeasuresForMonth(List<Measure> measures, String year, Integer monthNumber) {
		try {
			List<Measure> result = new ArrayList<>();
			String month = (monthNumber < 10) ? "0" + monthNumber : String.valueOf(monthNumber);
			String nextMonth = ((monthNumber + 1) < 10) ? "0" + (monthNumber + 1) : String.valueOf(monthNumber + 1);
			String strDateFrom = year + "-" + month + "-01";
			String strDateTo = year + "-" + nextMonth + "-01";
			if(monthNumber == 12) {
				strDateTo = year + "-12-31";
			}
			Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(strDateFrom);
			Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(strDateTo);
			for(Measure m : measures) {
				Date date = m.getMeasureDate();
				if(date.after(dateFrom) && date.before(dateTo)) {
					result.add(m);
				}
//				measures.remove(m);
			}
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Measure>();
		}
	}

	public RespMeasureCountForYear getMeasureCountForYear(String year) {
		RespMeasureCountForYear result = new RespMeasureCountForYear();
		List<Measure> response = getMeasuresForYear(year);
		result.setYear(year);
		result.setCount(response.size());
		return result;
	}
	
	public String getMeasurementCountForYearStr(String year) {
		RespMeasureCountForYear response = getMeasureCountForYear(year);
		return "year = " + response.getYear() + ", total = " + response.getCount();
	}

	@Override
	public String addMeasurement(String date, String value) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
			Measure measure = new Measure();
			measure.setId(measure.getId());
			measure.setMeasureDate(formatter.parse(date));
			measure.setMeasureValue(Integer.valueOf(value));
			repoMeasure.save(measure);
			repoMeasure.flush();
			return "0 - Add measurement succeed!";
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return "-1 - Add measurement failed! The value must be integer (NumberFormatException).";
		} catch(ParseException e) {
			e.printStackTrace();
			return "-2 - Add measurement failed! The date pattern must be yyyy-MM-ddTHH-mm-ss (ParseException).";
		} catch(Exception e) {
			e.printStackTrace();
			return "-999 - Add measurement failed! Unknown error.";
		}
	}

	public boolean isClientIpAndDeviceIdPairFree(String ip, String deviceId) {
		List<Client> response = repoClients.findByClientIp(ip);
		if(response.size() > 0) {
			return false;
		}
		return true;
	}
	

	public boolean saveClientIpAndDeviceId(String ip, String deviceId) {
		try {
			Client client = new Client();
			client.setClientIp(client.getClientIp());
			client.setClientIp(ip);
			client.setDeviceId(deviceId);
			repoClients.save(client);
			repoClients.flush();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String removeClientIpAndDeviceId(String ip, String deviceId) {
		try {
			Client client = new Client();
			client.setClientIp(client.getClientIp());
			client.setClientIp(ip);
			client.setDeviceId(deviceId);
			repoClients.delete(client);
			repoClients.flush();
			return "0 - Succeed";
		} catch(Exception e) {
			e.printStackTrace();
			return "-1 - Failed";
		}
	}

	public boolean checkAndManageClient(HttpServletRequest request, String deviceId) {
		String cliendIp = request.getRemoteAddr();
		if( ! isClientIpAndDeviceIdPairFree(cliendIp, deviceId)) {
//			return "The clientIp (" + cliendIp + ") and deviceId (" + deviceId + ") pair already uses the application. You have to unregister this pair, to use your current clientIp and deviceId pair.";
			return false;
		} 
		if(saveClientIpAndDeviceId(cliendIp, deviceId)) {
			return true;
		}
		return false;
	}

	
	
}
