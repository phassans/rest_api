package com.project.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.project.model.*;
import com.project.util.*;

@Path("/students")
public class Student {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryAllStudents() throws Exception {

		Response rb = null;	
		JSONArray json = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		try {

			StudentSchema student_db = new StudentSchema();

			json = student_db.queryAllStudents();
			if(json.length()<=0){
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to retrieve student data");
				jsonObject.put("data", "null");
				return Response.status(400).entity(jsonObject.toString()).build();
			}

			jsonObject.put("code", "200");
			jsonObject.put("status", "success");
			jsonObject.put("message", "Student data retrieved successfully");
			jsonObject.put("count", json.length());
			jsonObject.put("data", json);
			rb = Response.ok(jsonObject.toString()).build();

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return rb;
	}

	@Path("/{email}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnCourses(
			@PathParam("email") String email) 
					throws Exception {

		Response rb = null;	
		JSONArray json = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {

			StudentSchema student_db = new StudentSchema();

			json = student_db.getCourses(email);
			if(json.length()<=0){
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to retrieve student data");
				jsonObject.put("data", "null");
				return Response.status(400).entity(jsonObject.toString()).build();
			}

			jsonObject.put("code", "200");
			jsonObject.put("status", "success");
			jsonObject.put("message", "Student data retrieved successfully");
			jsonObject.put("count", json.length());
			jsonObject.put("data", json);
			rb = Response.ok(jsonObject.toString()).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return rb;
	}

	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addStudent(String incomingData) throws Exception {

		String returnString = null;

		JSONArray jsonArray = new JSONArray();
		JSONArray val = new JSONArray();

		JSONObject jsonObject = new JSONObject();
		StudentSchema student_db = new StudentSchema();
		Util UtilObj = new Util();
		Validation validate = new Validation();

		try {
			JSONObject partsData = new JSONObject(incomingData);
			System.out.print("PDEBUG JSON DATA: "+incomingData);
			val = validate.ValidateStudentData(incomingData);


			for (int i = 0; i < val.length(); i++) {
				if(val.getJSONObject(i).get("code").equals("400")){
					returnString = val.toString();
					return Response.status(400).entity(returnString).build();
				}
			}

			//System.out.println( "jsonData: " + partsData.toString());

			String dob_string = partsData.optString("DOB_MONTH")+"/"+partsData.optString("DOB_DAY")+"/"+partsData.optString("DOB_YEAR");
			java.sql.Date DOB = UtilObj.convertToDate(dob_string,"MMM/dd/yyyy");

			JSONArray student_check = student_db.getStudent(partsData.optString("EMAIL"));

			if(student_check.length()>0){
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to add Student. Email/Student already exists.");
				jsonObject.put("data", "null");
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			}

			int http_code = student_db.insertIntoSTUDENTS(
					partsData.optString("FIRSTNAME"), 
					partsData.optString("LASTNAME"), 
					partsData.optString("GENDER"),
					DOB,
					partsData.optString("EMAIL"),
					partsData.optString("PASSWORD"),
					partsData.optString("PHONE"),
					partsData.optString("ADDRESS"),
					partsData.optString("CITY"),
					partsData.optString("STATE"),
					80231,
					partsData.optString("COUNTRY"));

			if( http_code == 200 ) {
				JSONArray student_data = student_db.getStudent(partsData.optString("EMAIL"));
				jsonObject.put("code", "200");
				jsonObject.put("status", "success");
				jsonObject.put("message", "Student has been added successfully");
				jsonObject.put("data", student_data);

				returnString = jsonArray.put(jsonObject).toString();
			} else {
				jsonObject.put("code", "500");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to add Student");
				jsonObject.put("data", "null");
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(500).entity(returnString).build();
			}

			//System.out.println( "returnString: " + returnString );

		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}

	@DELETE
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudent(String incomingData) 
			throws Exception {

		//System.out.println("incomingData: " + incomingData);

		String EMAIL;
		String PASSWORD;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONArray val = new JSONArray();

		JSONObject jsonObject = new JSONObject();
		StudentSchema student_db = new StudentSchema();
		Validation validate = new Validation();

		try {

			JSONObject partsData = new JSONObject(incomingData);
			EMAIL = partsData.optString("EMAIL");
			PASSWORD = partsData.optString("PASSWORD");

			val = validate.ValidateDeleteStudentData(incomingData);

			for (int i = 0; i < val.length(); i++) {
				if(val.getJSONObject(i).get("code").equals("400")){
					returnString = val.toString();
					return Response.status(400).entity(returnString).build();
				}
			}

			JSONArray student_check = student_db.getStudent(partsData.optString("EMAIL"));

			if(student_check.length()<=0){
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to delete Student. Student does not exist");
				jsonObject.put("data", "null");
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			}

			http_code = student_db.deleteStudent(EMAIL, PASSWORD);

			if(http_code == 200) {
				jsonObject.put("code", "200");
				jsonObject.put("status", "success");
				jsonObject.put("message", "Student has been deleted successfully");
				jsonObject.put("data", "null");
				returnString = jsonArray.put(jsonObject).toString();
			} else if(http_code == 400) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to delete student. Please check email and password.");
				jsonObject.put("data", "null");
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();

			}else {
				jsonObject.put("code", "500");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to delete Student");
				jsonObject.put("data", "null");
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(jsonObject).build();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}

}
