package com.project.util;

import java.sql.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Validation {

	public JSONArray ValidateStudentData(String incomingData){
		JSONArray jsonArray = new JSONArray();
		Util UtilObj = new Util();


		try {
			JSONObject partsData = new JSONObject(incomingData);
			
			JSONObject json_fn = validateCharacters(partsData.optString("FIRSTNAME"), "First Name");
			if(json_fn.get("code").equals("400")){
				jsonArray.put(json_fn);
			}
			
			JSONObject json_ln = validateCharacters(partsData.optString("LASTNAME"), "Last Name");
			if(json_ln.get("code").equals("400")){
				jsonArray.put(json_ln);
			}
			
			JSONObject json_gender = validateGender(partsData.optString("GENDER"));
			if(json_gender.get("code").equals("400")){
				jsonArray.put(json_gender);
			}
			
			String dob_string = partsData.optString("DOB_MONTH")+"/"+partsData.optString("DOB_DAY")+"/"+partsData.optString("DOB_YEAR");
			System.out.println("PDEBUG: dob_string "+dob_string);
			java.sql.Date DOB = UtilObj.convertToDate(dob_string,"MMM/dd/yyyy");
			JSONObject json_dob = validateDATE(DOB, "Date of Birth","MMM/dd/yyyy");
			if(json_dob.get("code").equals("400")){
				jsonArray.put(json_dob);
			}
			
			JSONObject json_email = validateEmail(partsData.optString("EMAIL"));
			if(json_email.get("code").equals("400")){
				jsonArray.put(json_email);
			}
			
			JSONObject json_pw = validateEmpty(partsData.optString("PASSWORD"),"Password");
			if(json_pw.get("code").equals("400")){
				jsonArray.put(json_pw);
			}
			
			JSONObject json_phone = validatePhone(partsData.optString("PHONE"));
			if(json_phone.get("code").equals("400")){
				jsonArray.put(json_phone);
			}
			
			JSONObject json_addr = validateEmpty(partsData.optString("ADDRESS"),"Address");
			if(json_addr.get("code").equals("400")){
				jsonArray.put(json_addr);
			}
			
			JSONObject json_city = validateCharacters(partsData.optString("CITY"), "City");
			if(json_city.get("code").equals("400")){
				jsonArray.put(json_city);
			}
			
			JSONObject json_state = validateCharacters(partsData.optString("STATE"), "State");
			if(json_state.get("code").equals("400")){
				jsonArray.put(json_state);
			}
			
			JSONObject json_zip = validateZIP(partsData.optString("ZIP"));
			if(json_zip.get("code").equals("400")){
				jsonArray.put(json_zip);
			}
			
			JSONObject json_country = validateCharacters(partsData.optString("COUNTRY"), "Country");
			if(json_country.get("code").equals("400")){
				jsonArray.put(json_country);
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	public JSONArray ValidateCourseData(String incomingData){
		JSONArray jsonArray = new JSONArray();
		Util UtilObj = new Util();


		try {
			JSONObject partsData = new JSONObject(incomingData);
			
			JSONObject json_cc = validateEmpty(partsData.optString("CODE"), "Course CODE");
			if(json_cc.get("code").equals("400")){
				jsonArray.put(json_cc);
			}
			
			JSONObject json_name = validateEmpty(partsData.optString("NAME"), "Course Name");
			if(json_name.get("code").equals("400")){
				jsonArray.put(json_name);
			}
			
			JSONObject json_dept = validateEmpty(partsData.optString("DEPT"), "Department Name");
			if(json_dept.get("code").equals("400")){
				jsonArray.put(json_dept);
			}
			
			JSONObject json_prof = validateEmpty(partsData.optString("PROFESSOR"), "Course Professor");
			if(json_prof.get("code").equals("400")){
				jsonArray.put(json_prof);
			}
			
			JSONObject json_email = validateEmail(partsData.optString("PROFESSOR_EMAIL"));
			if(json_email.get("code").equals("400")){
				jsonArray.put(json_email);
			}
			
			JSONObject json_ppw = validateEmpty(partsData.optString("PROFESSOR_PWD"),"Professor Password");
			if(json_ppw.get("code").equals("400")){
				jsonArray.put(json_ppw);
			}
			
			JSONObject json_seats = validateNumber(partsData.optString("SEATS"), "Course Seats");
			if(json_seats.get("code").equals("400")){
				jsonArray.put(json_seats);
			}
			
			String reg_string = partsData.optString("REGDATE_MONTH")+"/"+partsData.optString("REGDATE_DAY")+"/"+partsData.optString("REGDATE_YEAR");
			java.sql.Date REGDATE = UtilObj.convertToDate(reg_string,"MMM/dd/yyyy");
			System.out.println("PDEBUG: dob_string "+reg_string);
			JSONObject json_regdate = validateDATE(REGDATE, "Class Registration Date","MMM/dd/yyyy");
			if(json_regdate.get("code").equals("400")){
				jsonArray.put(json_regdate);
			}
			
			String start_string = partsData.optString("STARTDATE_MONTH")+"/"+partsData.optString("STARTDATE_DAY")+"/"+partsData.optString("STARTDATE_YEAR");
			System.out.println("PDEBUG: dob_string "+start_string);
			java.sql.Date STARTDATE = UtilObj.convertToDate(start_string,"MMM/dd/yyyy");
			JSONObject json_startdate = validateDATE(STARTDATE, "Class Start Date","MMM/dd/yyyy");
			if(json_startdate.get("code").equals("400")){
				jsonArray.put(json_startdate);
			}
	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	public JSONArray ValidateRegisterData(String incomingData){
		JSONArray jsonArray = new JSONArray();

		try {
			JSONObject partsData = new JSONObject(incomingData);
			
			JSONObject json_email = validateEmail(partsData.optString("EMAIL"));
			if(json_email.get("code").equals("400")){
				jsonArray.put(json_email);
			}
			
			JSONObject json_pw = validateEmpty(partsData.optString("PASSWORD"),"Password");
			if(json_pw.get("code").equals("400")){
				jsonArray.put(json_pw);
			}
			
			JSONObject json_sid = validateNumber(partsData.optString("COURSE_ID"), "COURSE ID INVALID");
			if(json_sid.get("code").equals("400")){
				jsonArray.put(json_sid);
			}
	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	public JSONArray ValidateDeleteStudentData(String incomingData){
		JSONArray jsonArray = new JSONArray();

		try {
			JSONObject partsData = new JSONObject(incomingData);
			
			JSONObject json_email = validateEmail(partsData.optString("EMAIL"));
			if(json_email.get("code").equals("400")){
				jsonArray.put(json_email);
			}
			
			JSONObject json_pw = validateEmpty(partsData.optString("PASSWORD"),"Password");
			if(json_pw.get("code").equals("400")){
				jsonArray.put(json_pw);
			}
	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	public JSONArray ValidateDeleteCourseData(String incomingData){
		JSONArray jsonArray = new JSONArray();

		try {
			JSONObject partsData = new JSONObject(incomingData);
			
			JSONObject json_email = validateEmail(partsData.optString("PROFESSOR_EMAIL"));
			if(json_email.get("code").equals("400")){
				jsonArray.put(json_email);
			}
			
			JSONObject json_pw = validateEmpty(partsData.optString("PROFESSOR_PWD"),"Password");
			if(json_pw.get("code").equals("400")){
				jsonArray.put(json_pw);
			}
			
			JSONObject json_cc = validateEmpty(partsData.optString("CODE"), "Course CODE");
			if(json_cc.get("code").equals("400")){
				jsonArray.put(json_cc);
			}
	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	private JSONObject validateDATE(Date dOB, String type, String Format) {
		JSONObject jsonObject = new JSONObject();
		try {
			if(dOB==null){
				jsonObject.put("code", "400");
				jsonObject.put("message", type+": Please select "+type);
			}else{
				jsonObject.put("code", "200");
				jsonObject.put("message", "null");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private JSONObject validateCharacters(String FirstName, String Type){
		JSONObject jsonObject = new JSONObject();
		String regex = "^[a-zA-Z]+$";
		try {
			if(FirstName.equals("")){
				jsonObject.put("code", "400");
				jsonObject.put("message", Type+": Cannot be empty");
			}else if(FirstName.length()>30){
				jsonObject.put("code", "400");
				jsonObject.put("message", Type+": Too long. Max 30 Characters");
			}else if(!FirstName.matches(regex)){
				jsonObject.put("code", "400");
				jsonObject.put("message", Type+": Only characters are allowed");
			}else{
				jsonObject.put("code", "200");
				jsonObject.put("message", "null");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	private JSONObject validateNumber(String Number, String Type){
		JSONObject jsonObject = new JSONObject();
		String regex = "^[-+]?[0-9]*\\.?[0-9]+$";
		try {
			if(Number.equals("")){
				jsonObject.put("code", "400");
				jsonObject.put("message", Type+": Cannot be empty");
			}else if(!Number.matches(regex)){
				jsonObject.put("code", "400");
				jsonObject.put("message", Type+": Only Numbers are allowed");
			}else{
				jsonObject.put("code", "200");
				jsonObject.put("message", "null");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	private JSONObject validateZIP(String zip){
		JSONObject jsonObject = new JSONObject();
		String regex = "^[-+]?[0-9]*\\.?[0-9]+$";
		try {
			if(zip.equals("")){
				jsonObject.put("code", "400");
				jsonObject.put("message", "ZIP: Cannot be empty");
			}else if(zip.length()!=5){
				jsonObject.put("code", "400");
				jsonObject.put("message", "ZIP: Invalid length");
			}else if(!zip.matches(regex)){
				jsonObject.put("code", "400");
				jsonObject.put("message", "ZIP: Invalid ZIP");
			}else{
				jsonObject.put("code", "200");
				jsonObject.put("message", "null");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	private JSONObject validateEmail(String Email){
		JSONObject jsonObject = new JSONObject();
		String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		try {
			if(Email.equals("")){
				jsonObject.put("code", "400");
				jsonObject.put("message", "Email: Cannot be empty");
			}else if(!Email.matches(regex)){
				jsonObject.put("code", "400");
				jsonObject.put("message", "Email: Invalid");
			}else{
				jsonObject.put("code", "200");
				jsonObject.put("message", "null");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	private JSONObject validatePhone(String Phone){
		JSONObject jsonObject = new JSONObject();
		String regex = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";;
		try {
			if(Phone.equals("")){
				jsonObject.put("code", "400");
				jsonObject.put("message", "Phone: Cannot be empty");
			}else if(Phone.length()!=10){
				jsonObject.put("code", "400");
				jsonObject.put("message", "Phone: Invalid length");
			}else if(!Phone.matches(regex)){
				jsonObject.put("code", "400");
				jsonObject.put("message", "Phone: Invalid");
			}else{
				jsonObject.put("code", "200");
				jsonObject.put("message", "null");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	private JSONObject validateEmpty(String data, String type){
		JSONObject jsonObject = new JSONObject();
		try {
			if(data.equals("")){
				jsonObject.put("code", "400");
				jsonObject.put("message", type+": Cannot be empty");
			}else{
				jsonObject.put("code", "200");
				jsonObject.put("message", "null");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private JSONObject validateGender(String GENDER) {
		JSONObject jsonObject = new JSONObject();
		try {
			if(!(GENDER.toUpperCase().equals("MALE"))&&!(GENDER.toUpperCase().equals("FEMALE"))){
				jsonObject.put("code", "400");
				jsonObject.put("message", "Gender: Valid values are male or female");
			}else{
				jsonObject.put("code", "200");
				jsonObject.put("message", "null");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
