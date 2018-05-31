package com.fraiday.hellojdbc;

import java.sql.*;

public class TestTransaction {

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
			conn.setAutoCommit(false);
			
			stat = conn.createStatement();
			stat.execute("insert into deptinfo2 values (4,'airplane','jordan')");
			stat.execute("insert into deptinfo2 values (5, 'bicycle', 'yang')");
			
			conn.commit();
			conn.setAutoCommit(false);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		    try {
				if (conn!= null){
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if (stat != null){
					stat.close();
				}
				if (conn !=null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
