package com.fraiday.hellojdbc;

import java.sql.*;

public class TestPreparedStat {

	public static void main(String[] args) {
		if (args.length != 3){
			System.out.println("There should be 3 arguments.");
			System.exit(-1);
		}
		
		int deptno = 0;
		String deptname = null;
		String loc = null;
		Connection conn = null;
		PreparedStatement preparedStat = null;
		
		try {
			deptno = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		deptname = args[1];
		loc = args[2];
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/mydata","root","ysq123");
			preparedStat = conn.prepareStatement("insert into deptinfo values (?,?,?)");
			preparedStat.setInt(1, deptno);
			preparedStat.setString(2, deptname);
			preparedStat.setString(3, loc);
			preparedStat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if (preparedStat !=null){
					preparedStat.close();
				}
				if (conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
	}

}
