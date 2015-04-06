package com.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegisterSchema {

	public int registerCOURSE(
			String EMAIL,
			String  LASTNAME,
			int COURSE_ID) 
					throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			String code = validateCourseID(COURSE_ID);
			System.out.println("PDEBUG:code "+code);

			if(code.equals("")){
				return 405;
			}

			int STUDENT_ID = validateStudent(EMAIL, LASTNAME);
			System.out.println("PDEBUG:STUDENT_ID "+STUDENT_ID);

			if(STUDENT_ID<=0){
				return 400;
			}

			int already_registered = checkRegistrtaion(STUDENT_ID, COURSE_ID);
			if(already_registered>0){
				return 402;
			}

			conn = DataBase.LabConnection();
			query = conn.prepareStatement("SELECT SEATS " +
					"FROM COURSE "+
					"where ID = ? ");
			query.setInt(1, COURSE_ID); //protect against sql injection
			ResultSet rs1 = query.executeQuery();

			int seats = 0;
			if(rs1.next()) {
				seats = rs1.getInt(1);
			}
			System.out.println("PDEBUG:seats "+seats);

			if(seats==0){
				query.close();
				return 404;
			}

			query.close();

			conn = DataBase.LabConnection();
			query = conn.prepareStatement("SELECT COUNT(*) AS STUDENTS " +
					"FROM REGISTER "+
					"where COURSE_ID = ? ");
			query.setInt(1, COURSE_ID); //protect against sql injection
			ResultSet rs2 = query.executeQuery();

			int students_registered = 0;
			if(rs2.next()) {
				students_registered = rs2.getInt(1);
			}
			System.out.println("PDEBUG:students_registered "+students_registered);

			if(seats==students_registered){
				query.close();
				return 401;
			}
			query.close();

			conn = DataBase.LabConnection();
			query = conn.prepareStatement("SELECT COUNT(*) " +
					"FROM COURSE "+
					"where REGDATE < TRUNC(sysdate) AND ID = ?");
			query.setInt(1, COURSE_ID);
			ResultSet rs4 = query.executeQuery();

			int count = 0;
			if(rs4.next()) {
				count = rs4.getInt(1);
			}
			System.out.println("PDEBUG:PASSED "+count);

			if(count>0){
				query.close();
				return 403;
			}
			query.close();

			conn = DataBase.LabConnection();
			query = conn.prepareStatement("insert into REGISTER " +
					"(STUDENT_ID, COURSE_ID) " +
					"VALUES (?, ?) ");
			query.setInt(1, STUDENT_ID);
			query.setInt(2, COURSE_ID);
			query.executeUpdate();

			query.close();

		} catch (SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			return 400;
		}catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) 
				conn.close();
		}

		return 200;
	}

	public int unregisterCOURSE(String EMAIL,
			String  PASSWORD,
			int COURSE_ID) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {



			int STUDENT_ID = validateStudent(EMAIL, PASSWORD);
			//System.out.println("PDEBUG:STUDENT_ID "+STUDENT_ID);

			if(STUDENT_ID<=0){
				return 400;
			}

			int already_registered = checkRegistrtaion(STUDENT_ID,COURSE_ID);
			if(already_registered<=0){
				return 403;
			}

			String code = validateCourseID(COURSE_ID);
			System.out.println("PDEBUG:code "+code);

			if(code.equals("")){
				return 405;
			}

			conn = DataBase.LabConnection();

			query = conn.prepareStatement("SELECT COUNT(*) " +
					"FROM COURSE "+
					"where STARTDATE < TRUNC(sysdate) AND ID = ?");
			query.setInt(1, COURSE_ID);
			ResultSet rs4 = query.executeQuery();

			int count = 0;
			if(rs4.next()) {
				count = rs4.getInt(1);
			}
			System.out.println("PDEBUG:PASSED "+count);

			if(count>0){
				query.close();
				return 404;
			}

			conn = DataBase.LabConnection();
			query = conn.prepareStatement("delete from REGISTER " +
					"where STUDENT_ID = ? and COURSE_ID = ?");

			query.setInt(1, STUDENT_ID);
			query.setInt(2, COURSE_ID);
			query.executeUpdate();
			query.close();


		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}

		return 200;
	}

	public String validateCourseID(int COURSE_ID){
		PreparedStatement query = null;
		Connection conn = null;
		String code = "";

		try {
			conn = DataBase.LabConnection();
			query = conn.prepareStatement("SELECT CODE " +
					"FROM COURSE "+
					"where ID = ? ");
			query.setInt(1, COURSE_ID);
			ResultSet rs6 = query.executeQuery();

			if(rs6.next()) {
				code = rs6.getString(1);
			}
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return code;
	}

	public int validateStudent(String EMAIL, String PASSWORD){
		PreparedStatement query = null;
		Connection conn = null;
		int SID = 0;

		try {
			conn = DataBase.LabConnection();
			String p = "SELECT ID " +
					"FROM STUDENT "+
					"where EMAIL = ? AND PASSWORD = ?";
			System.out.println(p);
			query = conn.prepareStatement("SELECT ID " +
					"FROM STUDENT "+
					"where EMAIL = ? AND PASSWORD = ?");
			query.setString(1, EMAIL);
			query.setString(2, PASSWORD);
			ResultSet rs7 = query.executeQuery();

			if(rs7.next()) {
				SID = rs7.getInt(1);
			}
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return SID;
	}

	public int checkRegistrtaion(int STUDENT_ID, int COURSE_ID){
		PreparedStatement query = null;
		Connection conn = null;
		int already_registered = 0;

		try {
			conn = DataBase.LabConnection();
			query = conn.prepareStatement("SELECT COUNT(*) FROM REGISTER " +
					"where STUDENT_ID = ? AND COURSE_ID = ?");
			query.setInt(1, STUDENT_ID);
			query.setInt(2, COURSE_ID);
			ResultSet rs3 = query.executeQuery();

			if(rs3.next()) {
				already_registered = rs3.getInt(1);
			}
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return already_registered;
	}

}
