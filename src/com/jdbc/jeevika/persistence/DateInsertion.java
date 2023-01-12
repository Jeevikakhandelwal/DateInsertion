package com.jdbc.jeevika.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class DateInsertion {
	private static final String SQL_INSERT_QUERY="INSERT INTO PERSON(PNAME,PADDRESS,DOB,DOM,DOJ) VALUES(?,?,?,?,?);";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//JDBC related parameters
		Connection connection=null;
		PreparedStatement pstmt=null;
		
		//To connect with DBE using url,username,password
		String url="jdbc:mysql:///PersonInfo";
		String userName="root";
		String password="root@123";
		Scanner scan=new Scanner(System.in);
		String pname=null,paddress=null,DOB=null,DOM=null,DOJ=null;
		java.text.SimpleDateFormat sdfSDOB=null,sdfSDOM=null;
		java.util.Date udob=null,udom=null;
		java.sql.Date sqlDob=null,sqlDom=null,sqlDoj=null;
		
		try {
			if(scan!=null) {
				System.out.print("Enter the pname:: ");
				pname=scan.next();
				
				System.out.print("Enter the paddress:: ");
				paddress=scan.next();
				
				System.out.print("Enter the DOB(dd-MM-yyyy):: ");
				DOB=scan.next();
				
				System.out.print("Enter the DOM(MM-dd-yyyy):: ");
				DOM=scan.next();
				
				System.out.print("Enter the DOJ(yyyy-MM-dd):: ");
				DOJ=scan.next();
					
			}
			
			//Converting String object to java.util.date
			sdfSDOB=new SimpleDateFormat("dd-MM-yyyy");
			if(DOB!=null)
				udob=sdfSDOB.parse(DOB);
			
			sdfSDOM=new SimpleDateFormat("MM-dd-yyyy");
			if(DOM!=null)
				udom=sdfSDOM.parse(DOM);
			
			//Converting java.util.Date to java.sql.Date
				if(udob!=null)
					sqlDob=new java.sql.Date(udob.getTime());
				
				if(udom!=null)
					sqlDom=new java.sql.Date(udom.getTime());
				
			//Directly converting java.lang,string object into java.sql.Date Object
			if(DOJ!=null)
				sqlDoj=java.sql.Date.valueOf(DOJ);
				
				
				
			//Step1 => Establishing the Connection by providing url, username,password
			connection=DriverManager.getConnection(url,userName,password);
			
			if(connection!=null) 
				pstmt=connection.prepareStatement(SQL_INSERT_QUERY);
			
			if(pstmt!=null) {
				
				//Setting the input value to Pre-Compiled Query
				pstmt.setString(1, pname);
				pstmt.setString(2, paddress);
				
				//Setting the java.sql.Date Object
				pstmt.setDate(3, sqlDob);
				pstmt.setDate(4, sqlDom);
				pstmt.setDate(5, sqlDoj);	
			}
			
			if(pstmt!=null) {
				//Execute the query
				int rowCount=pstmt.executeUpdate();
				if(rowCount==0)
					System.out.print("Record inserstion failed....");
				else
					System.out.print("Record insertion sucessfull....");
			}
			
		}catch(SQLException ex) {
			
			//step4 => Handling Code
			ex.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			//Step5 => Closing all the resources
			if(scan!=null) {
				try {
					scan.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection!=null) {
				try {
					connection.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
