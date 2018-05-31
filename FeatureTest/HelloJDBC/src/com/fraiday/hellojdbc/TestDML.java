package com.fraiday.hellojdbc;
import java.sql.*;

public class TestDML {

	private static final String url = "jdbc:mysql://localhost/mydata";
	private static final String user = "root";
	private static final String passwrd = "ysq123";
	
	public static void main(String[] args) {
		
		Connection connection = null;
		Statement statement = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException ("can find the driver given!");
		}
		
		try {
			connection = DriverManager.getConnection(url, user, passwrd);
			String sqlStat = "insert into deptinfo values (4,'big data','London')";
			statement = connection.createStatement();
			statement.executeUpdate(sqlStat);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if (statement != null){
					statement.close();
				}			
				if (connection != null){
					connection.close();
				}				
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		

	}

}
