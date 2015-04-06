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

@Path("/courses")
public class Course {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryAllCourses() throws Exception {

		Response rb = null;	
		JSONArray json = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		try {

			CourseSchema course_db = new CourseSchema();

			json = course_db.queryAllCourses();
			if(json.length()<=0){
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to retrieve course data");
				jsonObject.put("data", "null");
				return Response.status(400).entity(jsonObject.toString()).build();
			}
			
			jsonObject.put("code", "200");
			jsonObject.put("status", "success");
			jsonObject.put("message", "Course data retrieved successfully");
			jsonObject.put("count", json.length());
			jsonObject.put("data", json);
			rb = Response.ok(jsonObject.toString()).build();

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return rb;
	}

	@Path("/{course}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnStudents(
				@PathParam("course") String course) 
				throws Exception {
		
		System.out.println("incomingData: " + course);
		
		String returnString = null;
		
		JSONObject json = new JSONObject();
		
		try {
			
			CourseSchema course_db = new CourseSchema();
			
			json = course_db.getStudents(course);
			returnString = json.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCourse(String incomingData) throws Exception {

		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONArray val = new JSONArray();
		
		JSONObject jsonObject = new JSONObject();
		
		CourseSchema course_db = new CourseSchema();
		Util UtilObj = new Util();
		Validation validate = new Validation();

		try {
			JSONObject partsData = new JSONObject(incomingData);
			System.out.println( "incomingData: " + incomingData);
			val = validate.ValidateCourseData(incomingData);
			
			for (int i = 0; i < val.length(); i++) {
				if(val.getJSONObject(i).get("code").equals("400")){
					returnString = val.toString();
					return Response.status(400).entity(returnString).build();
				}
			}
			
			System.out.println( "jsonData: " + partsData.toString() );

			String reg_string = partsData.optString("REGDATE_MONTH")+"/"+partsData.optString("REGDATE_DAY")+"/"+partsData.optString("REGDATE_YEAR");
			String start_string = partsData.optString("STARTDATE_MONTH")+"/"+partsData.optString("STARTDATE_DAY")+"/"+partsData.optString("STARTDATE_YEAR");
			
			java.sql.Date REGDATE = UtilObj.convertToDate(reg_string,"MMM/dd/yyyy");
			java.sql.Date STARTDATE = UtilObj.convertToDate(start_string,"MMM/dd/yyyy");
			int SEATS = Integer.parseInt(partsData.optString("SEATS"));
			
			JSONArray course_check = course_db.getCourse(partsData.optString("CODE"));
			
			if(course_check.length()>0){
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to add course. Course already exists.");
				jsonObject.put("data", "null");
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			}
			
			int http_code = course_db.insertIntoCOURSE(
					partsData.optString("CODE"), 
					partsData.optString("NAME"),
					partsData.optString("DEPT"),
					partsData.optString("DESCRIPTION"),
					partsData.optString("PROFESSOR"),
					partsData.optString("PROFESSOR_EMAIL"),
					partsData.optString("PROFESSOR_PWD"),
					SEATS,
					partsData.optString("TIME"),
					REGDATE,
					STARTDATE,
					partsData.optString("INFO"));

			if( http_code == 200 ) {
				JSONArray course_data = course_db.getCourse(partsData.optString("CODE"));
				jsonObject.put("code", "200");
				jsonObject.put("status", "success");
				jsonObject.put("message", "Course has been added successfully");
				jsonObject.put("data", course_data);

				returnString = jsonArray.put(jsonObject).toString();
			} else {
				jsonObject.put("code", "500");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to add Course");
				jsonObject.put("data", "null");
				return Response.status(500).entity(jsonObject.toString()).build();
			}

			System.out.println( "returnString: " + returnString );

		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}

	@DELETE
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCOURSE(String incomingData) 
					throws Exception {

		System.out.println("incomingData: " + incomingData);

		String PROFESSOR_EMAIL;
		String PROFESSOR_PWD;
		String CODE;
		
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		JSONArray val = new JSONArray();
		Validation validate = new Validation();
		
		CourseSchema course_db = new CourseSchema();

		try {

			JSONObject partsData = new JSONObject(incomingData);
			
			PROFESSOR_EMAIL = partsData.optString("PROFESSOR_EMAIL");
			PROFESSOR_PWD = partsData.optString("PROFESSOR_PWD");
			CODE = partsData.optString("CODE");
			
			val = validate.ValidateDeleteCourseData(incomingData);
			for (int i = 0; i < val.length(); i++) {
				if(val.getJSONObject(i).get("code").equals("400")){
					returnString = val.toString();
					return Response.status(400).entity(returnString).build();
				}
			}
			
			JSONArray student_check = course_db.getCourse(partsData.optString("CODE"));
			
			if(student_check.length()<=0){
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to delete Student. Course does not exist");
				jsonObject.put("data", "null");
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			}

			http_code = course_db.deleteCourse(PROFESSOR_EMAIL, PROFESSOR_PWD, CODE);

			if(http_code == 200) {
				jsonObject.put("code", "200");
				jsonObject.put("status", "success");
				jsonObject.put("message", "Course has been deleted successfully");
				jsonObject.put("data", "null");
				returnString = jsonArray.put(jsonObject).toString();
			} else if(http_code == 400) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to delete course.Please check delete data.");
				jsonObject.put("data", "null");
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else {
				jsonObject.put("code", "500");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to delete course");
				jsonObject.put("data", "null");
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(500).entity(returnString).build();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}

}
