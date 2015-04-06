package com.project.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.project.model.*;
import com.project.util.*;

@Path("/register")
public class Register {
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response RegisterCourse(String incomingData) throws Exception {

		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONArray val = new JSONArray();

		JSONObject jsonObject = new JSONObject();
		RegisterSchema register_db = new RegisterSchema();
		Validation validate = new Validation();

		try {
			System.out.println("Register Data: "+incomingData);
			JSONObject partsData = new JSONObject(incomingData);
			val = validate.ValidateRegisterData(incomingData);

			for (int i = 0; i < val.length(); i++) {
				if(val.getJSONObject(i).get("code").equals("400")){
					returnString = val.toString();
					return Response.status(400).entity(returnString).build();
				}
			}

			System.out.println( "jsonData: " + partsData.toString() );

			String EMAIL = partsData.optString("EMAIL");
			String LASTNAME = partsData.optString("PASSWORD");
			int COURSE_ID = Integer.parseInt(partsData.optString("COURSE_ID"));

			int http_code = register_db.registerCOURSE(
					EMAIL,LASTNAME,COURSE_ID);

			if( http_code == 200 ) {
				jsonObject.put("code", "200");
				jsonObject.put("status", "success");
				jsonObject.put("message", "Student successfully registered for the course");
				jsonObject.put("data", "null");
				
				returnString = jsonArray.put(jsonObject).toString();
			}else if( http_code == 400 ) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to register student. Student does not exist");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else if( http_code == 401 ) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to register student. Class is full");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else if( http_code == 402 ) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to register student. Already registered");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else if( http_code == 403 ) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to register student. Registration date is passed");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else if( http_code == 404) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to register student. Students not accepted for course.");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else if( http_code == 405) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to register student. Invalid Course.");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else {
				jsonObject.put("code", "500");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to register student for the course");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			}

		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}

	@DELETE
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response UnRegisterCourse(String incomingData) 
			throws Exception {

		System.out.println("incomingData: " + incomingData);

		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONArray val = new JSONArray();

		JSONObject jsonObject = new JSONObject();
		RegisterSchema register_db = new RegisterSchema();
		Validation validate = new Validation();

		try {

			JSONObject partsData = new JSONObject(incomingData);
			val = validate.ValidateRegisterData(incomingData);

			for (int i = 0; i < val.length(); i++) {
				if(val.getJSONObject(i).get("code").equals("400")){
					returnString = val.toString();
					return Response.status(400).entity(returnString).build();
				}
			}


			String EMAIL = partsData.optString("EMAIL");
			String PASSWORD = partsData.optString("PASSWORD");
			int COURSE_ID = Integer.parseInt(partsData.optString("COURSE_ID"));

			http_code = register_db.unregisterCOURSE(EMAIL, PASSWORD, COURSE_ID);

			if(http_code == 200) {
				jsonObject.put("code", "200");
				jsonObject.put("status", "success");
				jsonObject.put("message", "Student successfully unregistered from the course");
				jsonObject.put("data", "null");

				returnString = jsonArray.put(jsonObject).toString();
			}else if( http_code == 400 ) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to unregister student. Student does not exist");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else if( http_code == 405) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to unregister student. Invalid Course.");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else if( http_code == 403) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to unregister student. Student is not registered for the course");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			} else if( http_code == 404) {
				jsonObject.put("code", "400");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to unregister student. Course has already started");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			}else {
				jsonObject.put("code", "500");
				jsonObject.put("status", "error");
				jsonObject.put("message", "Unable to unregister student from the course");
				jsonObject.put("data", "null");
				
				val.put(jsonObject);
				returnString = val.toString();
				return Response.status(400).entity(returnString).build();
			}

		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}
}
