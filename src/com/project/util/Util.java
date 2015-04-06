package com.project.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
	public java.sql.Date convertToDate(String date, String form){
		DateFormat format = new SimpleDateFormat(form, Locale.ENGLISH);
		Date date1 = null;
		try {
			date1 = format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		System.out.println(date1);
		
		java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
		return sqlDate;
	}

	public int convertGender(String GENDER) {
		if(GENDER.toUpperCase().equals("MALE")){
			return 1;
		}else if(GENDER.toUpperCase().equals("FEMALE")){
			return 2;
		}
		return 0;
	}
}
