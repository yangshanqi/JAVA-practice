package com.fraiday.hellojdbc;

import java.sql.*;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public class TestCallableStat {
	
//  the p1 procedure in sql	
/*	delimiter //
	mysql> create procedure p1
	    -> (in a int, in b int, out c int, inout temp int)
	    -> begin
	    -> set c=a+b;
	    -> set temp = temp+1;
	    -> end;
	    -> //  */
	
	public static void main(String[] args) {
		Connection conn= null;
		CallableStatement callStat = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/mydata","root","ysq123");
			callStat = conn.prepareCall("call p1 (?,?,?,?)");
			callStat.registerOutParameter(3, Type.INT);
			callStat.registerOutParameter(4, Type.INT);
			callStat.setInt(1,3);
			callStat.setInt(2,4);
			callStat.setInt(4,8);
			callStat.execute();
			System.out.println(callStat.getInt(3));
			System.out.println(callStat.getInt(4));			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if (callStat != null){
					callStat.close();
				}
				if (conn != null){
					conn.close();
				}				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
