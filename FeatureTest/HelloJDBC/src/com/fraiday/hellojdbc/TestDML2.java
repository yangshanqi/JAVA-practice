package com.fraiday.hellojdbc;
import java.sql.*;
public class TestDML2 {

	public static void main(String[] args) {
		
		
		if  (args.length != 3){
			System.out.println("There should be 3 arguments given.");
			System.exit(-1);
		}
		
		int deptno = 0;
		String deptname  = args[1];
		String loc = args[2];
		Connection connection =null;
		Statement statement = null;
		
		try {
			deptno = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			throw new RuntimeException ("The first argument should be integer");
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/mydata","root","ysq123");
			statement = connection.createStatement();
			String sqlStat = "insert into deptinfo values ("+deptno+", '"+deptname+"', '"+loc+"')";
			statement.execute(sqlStat);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

}
