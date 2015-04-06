package com.project.model;

import java.sql.Connection;

import javax.naming.*;
import javax.sql.*;

public class DataBase {

	private static DataSource DBObject = null;
	private static Context context = null;


	private static DataSource DBConn() throws Exception {

		if (DBObject != null) {
			return DBObject;
		}

		try {
			if (context == null) {
				context = new InitialContext();
			}

			DBObject = (DataSource) context.lookup("StudentLabOracle");

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return DBObject;

	}

	public static Connection LabConnection() {
		Connection conn = null;
		try {
			conn = DBConn().getConnection();
			return conn;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
