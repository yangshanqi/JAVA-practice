package com.fraiday.hellojdbc;
import java.sql.*;

public class FormalVersionTest {
	
	// comment
	private static final String url = "jdbc:mysql://localhost/mydata";
	private static final String user = "root";
	private static final String passwrd = "ysq123";
	
	public static void main (String[] args){
		
		Connection con = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String driverName = "com.mysql.jdbc.Driver";
		
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Wrong driver name given!");
		}
		
		try {
			con = DriverManager.getConnection(url, user, passwrd);
			statement = con.createStatement();
			String sqlStat = "select * from deptinfo";
			resultSet = statement.executeQuery(sqlStat);
			
			while (resultSet.next()){
				System.out.println(resultSet.getString("deptno"));
				System.out.println(resultSet.getString("deptname"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{			
			try {
				if (resultSet!=null){
					resultSet.close();
					// GC
					resultSet = null;
				}
				if (statement != null){
					statement.close();
					statement = null;
				}
				if (con != null){
					con.close();
					con = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}

}
