package com.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.project.util.Json;

public class CourseSchema {

	public JSONArray queryAllCourses() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		Json converter = new Json();
		JSONArray json = new JSONArray();

		try {
			conn = DataBase.LabConnection();
			query = conn.prepareStatement("SELECT ID, CODE, NAME, DEPT, DESCRIPTION, PROFESSOR, PROFESSOR_EMAIL, SEATS, TIME, REGDATE, STARTDATE, INFO " +
					"from COURSE");

			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}



	public int deleteCourse(String PROFESSOR_EMAIL, String PROFESSOR_PWD, String CODE) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		int recordsAffected = 0;

		try {
			conn = DataBase.LabConnection();
			query = conn.prepareStatement("delete from COURSE " +
					"where PROFESSOR_EMAIL = ? and PROFESSOR_PWD = ? AND CODE = ?");

			query.setString(1, PROFESSOR_EMAIL);
			query.setString(2, PROFESSOR_PWD);
			query.setString(3, CODE);

			recordsAffected = query.executeUpdate();
			if(recordsAffected>0){
				query.close();
				return 200;
			}
			query.close();

		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}

		return 400;
	}



	public int insertIntoCOURSE(
			String CODE,  
			String NAME, 
			String DEPT,
			String DESCRIPTION,
			String PROFESSOR,
			String PROFESSOR_EMAIL,
			String PROFESSOR_PWD,
			int SEATS,
			String TIME,
			java.sql.Date REGDATE,
			java.sql.Date STARTDATE,
			String INFO
			) 
					throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = DataBase.LabConnection();
			query = conn.prepareStatement("insert into COURSE " +
					"(CODE, NAME, DEPT, DESCRIPTION, PROFESSOR, PROFESSOR_EMAIL,PROFESSOR_PWD,  SEATS, TIME, REGDATE, STARTDATE, INFO) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?) ");

			query.setString(1, CODE);
			query.setString(2, NAME);
			query.setString(3, DEPT);
			query.setString(4, DESCRIPTION);
			query.setString(5, PROFESSOR);
			query.setString(6, PROFESSOR_EMAIL);
			query.setString(7, PROFESSOR_PWD);
			query.setInt(8, SEATS);
			query.setString(9, TIME);
			query.setDate(10, REGDATE);
			query.setDate(11, STARTDATE);
			query.setString(12, INFO);

			query.executeUpdate();
			query.close();

		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) 
				conn.close();
		}

		return 200;
	}

	public JSONObject getStudents(String course) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		JSONArray json = new JSONArray();
		JSONObject obj=new JSONObject();

		Json converter = new Json();

		try {
			conn = DataBase.LabConnection();

			query = conn.prepareStatement("SELECT STUDENT.FIRSTNAME, STUDENT.LASTNAME, STUDENT.EMAIL " +
					"FROM COURSE "+
					"JOIN REGISTER "+ 
					"ON COURSE.ID = REGISTER.COURSE_ID "+
					"JOIN STUDENT "+ 
					"ON STUDENT.ID = REGISTER.STUDENT_ID " +
					"where COURSE.CODE = ? ");

			query.setString(1, course); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			if(json.length()<=0){
				obj.put("code", "400");
				obj.put("status", "error");
				obj.put("message", "Unable to retrieve student data");
				obj.put("data", "null");
			}else{
				obj.put("code", "200");
				obj.put("status", "success");
				obj.put("message", "Student data retrieved successfully");
				obj.put("count", json.length());
				obj.put("data", json);
				query.close(); //close connection

				query = conn.prepareStatement("SELECT SEATS,REGDATE " +
						"FROM COURSE "+
						"where COURSE.CODE = ? ");
				query.setString(1, course); //protect against sql injection
				ResultSet rs1 = query.executeQuery();
				int seats = 0;
				String regdate=null;
				if(rs1.next()) {
					seats = rs1.getInt(1);
					regdate = rs1.getString(2);
				}
				int avail_seats = seats-json.length();

				query.close(); //close connection

				obj.put("available_seats", avail_seats);
				obj.put("seats_registered", json.length());
				obj.put("last_day_for_registration", regdate);
			}

		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return obj;
		}
		catch(Exception e) {
			e.printStackTrace();
			return obj;
		}
		finally {
			if (conn != null) conn.close();
		}

		return obj;
	}

	public JSONArray getCourse(String code) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		JSONArray json = new JSONArray();
		Json converter = new Json();

		try {
			conn = DataBase.LabConnection();

			query = conn.prepareStatement("SELECT ID, CODE, NAME, DEPT, DESCRIPTION, PROFESSOR, PROFESSOR_EMAIL, SEATS, TIME, REGDATE, STARTDATE, INFO " +
					"FROM COURSE "+
					"where CODE = ? ");

			query.setString(1, code); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}

		return json;
	}
}
