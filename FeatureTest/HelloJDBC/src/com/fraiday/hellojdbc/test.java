package com.fraiday.hellojdbc;

import java.sql.*;

import com.mysql.jdbc.Connection;

public class test {
	
	private static final String url = "jdbc:mysql://localhost/mydata";
	private static final String user = "root";
	private static final String password = "ysq123";
	
	public static void main (String[] args){
		
		String driverName = "com.mysql.jdbc.Driver";
		Connection con = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			Class.forName(driverName);
			//new com.mysql.jdbc.Driver();
		    
			con = (Connection) DriverManager.getConnection(url, user, password);
			System.out.println("successful connection");
			String selectsql = "select * from deptinfo";
			statement = con.createStatement();
			resultSet = statement.executeQuery(selectsql);
			//Statement.executeUpdate()
			
			 while (resultSet.next()){
				 System.out.println(resultSet.getString("deptno"));
				 
				 //get...				 
				 System.out.println(resultSet.getString("deptname"));
			 }
			
			//close !!!
			resultSet.close();
			statement.close();
			con.close();
			
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		
	}

}
