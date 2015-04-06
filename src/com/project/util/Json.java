package com.project.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.sql.ResultSet;

public class Json {

	/**
	 * This will convert database records into a JSON Array
	 * Simply pass in a ResultSet from a database connection and it
	 * loop return a JSON array.
	 * 
	 * It important to check to make sure that all DataType that are
	 * being used is properly encoding.
	 * 
	 * 
	 * @param rs - database ResultSet
	 * @return - JSON array
	 * @throws Exception
	 */
	public JSONArray toJSONArray(ResultSet rs) throws Exception {

        JSONArray json = new JSONArray(); //JSON array that will be returned

        try {

        	 //we will need the column names, this will save the table meta-data like column nmae.
             java.sql.ResultSetMetaData rsmd = rs.getMetaData();

             //loop through the ResultSet
             while( rs.next() ) {
            	 
            	 //figure out how many columns there are
                 int numColumns = rsmd.getColumnCount();
                 //each row in the ResultSet will be converted to a JSON Object
                 JSONObject obj = new JSONObject();

                 //loop through all the columns and place them into the JSON Object
                 for (int i=1; i<numColumns+1; i++) {

                     String column_name = rsmd.getColumnName(i);

                     if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
                    	 obj.put(column_name, rs.getArray(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                    	 obj.put(column_name, rs.getInt(column_name));
                    	
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                    	 obj.put(column_name, rs.getBoolean(column_name));
                    	
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                    	 obj.put(column_name, rs.getBlob(column_name));
                    	
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                    	 obj.put(column_name, rs.getDouble(column_name)); 
                    	
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                    	 obj.put(column_name, rs.getFloat(column_name));
                    	
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                    	 obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                    	 obj.put(column_name, rs.getNString(column_name));
                    	
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                    	 obj.put(column_name, rs.getString(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                    	 obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                    	 obj.put(column_name, rs.getInt(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                    	 obj.put(column_name, rs.getDate(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                    	 obj.put(column_name, rs.getTimestamp(column_name));
                     }
                     else if(rsmd.getColumnType(i)==java.sql.Types.NUMERIC){
                    	 obj.put(column_name, rs.getBigDecimal(column_name));
                      }
                     else {
                    	 obj.put(column_name, rs.getObject(column_name));
                     } 

                    }//end foreach
                 
                 json.put(obj);

             }//end while

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json; //return JSON array
	}
}
