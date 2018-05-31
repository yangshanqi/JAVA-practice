package com.fraiday.hellojdbc;
import java.sql.*;

public class TestBatch {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stat = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/mydata","root","ysq123");
			stat = conn.createStatement();
			stat.addBatch("insert into deptinfo2 values (1,'game','mike')");
			stat.addBatch("insert into deptinfo2 values (2,'hw','jacky')");
			stat.addBatch("insert into deptinfo2 values (3,'software','jason')");
			stat.executeBatch();
		/*
		 * 	preparedStat = conn.prepareStatement("insert into deptinfo2 values (?,?,?)");
		 *  preparedStat.setInt (1,1);
		 *  preparedStat.setString (2,'game');
		 *  preparedStat.setString (3,'mike');
		 *  preparedStat.addBatch();
		 */
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if (stat != null){
					stat.close();
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
