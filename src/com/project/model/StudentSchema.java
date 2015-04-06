package com.project.model;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;
import com.project.util.*;

public class StudentSchema {

	public int insertIntoSTUDENTS(
			String FIRSTNAME,  
			String LASTNAME, 
			String GENDER,
			java.sql.Date DOB,
			String EMAIL,
			String PASSWORD,
			String PHONE,
			String ADDRESS,
			String CITY,
			String STATE,
			int ZIP,
			String COUNTRY) 
					throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = DataBase.LabConnection();
			query = conn.prepareStatement("insert into STUDENT " +
					"(FIRSTNAME, LASTNAME, GENDER, DOB, EMAIL, PASSWORD, PHONE, ADDRESS, CITY, STATE, ZIP, COUNTRY) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?) ");

			query.setString(1, FIRSTNAME);
			query.setString(2, LASTNAME);
			query.setString(3, GENDER);
			query.setDate(4, DOB);
			query.setString(5, EMAIL);
			query.setString(6, PASSWORD);
			query.setString(7, PHONE);
			query.setString(8, ADDRESS);
			query.setString(9, CITY);
			query.setString(10, STATE);
			query.setInt(11, ZIP);
			query.setString(12, COUNTRY);

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

	public JSONArray queryAllStudents() throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		Json converter = new Json();
		JSONArray json = new JSONArray();

		try {
			conn = DataBase.LabConnection();
			query = conn.prepareStatement("SELECT FIRSTNAME, LASTNAME, GENDER, DOB, EMAIL, PHONE, ADDRESS, CITY, STATE, ZIP, COUNTRY " +
					"FROM STUDENT");

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

	public int deleteStudent(String EMAIL, String PASSWORD) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;
		int recordsAffected = 0;
		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before deleting data.
			 */

			conn = DataBase.LabConnection();
			query = conn.prepareStatement("delete from STUDENT " +
					"where EMAIL = ? and PASSWORD = ?");

			query.setString(1, EMAIL);
			query.setString(2, PASSWORD);
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

	public JSONArray getCourses(String email) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		Json converter = new Json();
		JSONArray json = new JSONArray();

		try {
			conn = DataBase.LabConnection();

			query = conn.prepareStatement("SELECT COURSE.NAME, COURSE.DESCRIPTION " +
					"FROM COURSE "+
					"JOIN REGISTER "+ 
					"ON COURSE.ID = REGISTER.COURSE_ID "+
					"JOIN STUDENT "+ 
					"ON STUDENT.ID = REGISTER.STUDENT_ID " +
					"where STUDENT.EMAIL = ? ");

			query.setString(1, email); //protect against sql injection
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

	public JSONArray getStudent(String email) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		Json converter = new Json();
		JSONArray json = new JSONArray();

		try {
			conn = DataBase.LabConnection();
			query = conn.prepareStatement("SELECT ID, FIRSTNAME, LASTNAME, GENDER, DOB, EMAIL, PHONE, ADDRESS, CITY, STATE, ZIP, COUNTRY " +
					"FROM STUDENT "+
					"where EMAIL = ? ");

			query.setString(1, email); //protect against sql injection
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
}
