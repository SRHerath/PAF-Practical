package com;
import java.sql.*;
public class Payment {

	//A common method to connect to the DB
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con= DriverManager.getConnection("jdbc:mysql://localhost:3306/elecrtrogrid",
		 		"root", "#Group20"); 
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	
	
	//insert payment
	public String insertPayment(String cname, String cno, String exmnth, String exyr,String amount, String cvv, String tdate)
	{ 
	 Connection con = connect(); 
	 
	 String output = "";
	 
	try
	 { 
	 
	 if (con == null) 
	 { 
		 return "Error while connecting to the database"; 
	 } 
	 // create a prepared statement
	 String query = "insert into payment"
	 + "(`PayID`,`CardName`,`CardNumber`,`Ex_Month`,`Ex_Year`,`Amount`, `CV`, `Date`)"
	 + " values (?, ?, ?, ?, ?, ?, ?, ?)"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(2, cname); 
	 preparedStmt.setString(3, cno); 
	 preparedStmt.setString(4, exmnth); 
	 preparedStmt.setString(5, exyr); 
	 preparedStmt.setDouble(6, Double.parseDouble(amount)); 
	 preparedStmt.setString(7, cvv);
	 preparedStmt.setString(8, tdate);
	
	 //execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 
	 String newRecord = readPayment(); 
		output = "{\"status\":\"success\", \"data\": \"" + newRecord + "\"}";
 	
	} catch(Exception e) {
		output = "{\"status\":\"error\", \"data\": \"Failed to insert the record\"}";
		System.err.println(e.getMessage());
	 } 
	return output; 
	}
	
	
	
	//read all payment details
	public String readPayment()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Owner Name</th><th>Card No</th>" +
	 "<th>Exp. Month</th><th>Exp. Year</th>" +
	 "<th>Amount</th><th>CVC/CVV</th><th>Date</th>" +
	 "<th>Update</th><th>Remove</th></tr>";

	 String query = "select * from payment";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String PayID = Integer.toString(rs.getInt("PayID"));
	 String CardName = rs.getString("CardName");
	 String CardNumber = rs.getString("CardNumber");
	 String Ex_Month = rs.getString("Ex_Month");
	 String Ex_Year = rs.getString("Ex_Year");
	 String Amount = Double.toString(rs.getDouble("Amount"));
	 String CV = rs.getString("CV");
	 String Date = rs.getString("Date");
	 // Add into the html table
	 output += "<tr><td>" + CardName + "</td>";
	 output += "<td>" + CardNumber + "</td>";
	 output += "<td>" + Ex_Month + "</td>";
	 output += "<td>" + Ex_Year + "</td>";
	 output += "<td>" + Amount + "</td>";
	 output += "<td>" + CV + "</td>";
	 output += "<td>" + Date + "</td>";
	 
	 // buttons
	 	output += "<td>"
				+ "<input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-sm btn-secondary' data-payid='" + PayID + "'>"
				+ "</td>" 
				+ "<td>"
				+ "<input name='btnRemove' type='button' value='Remove' class='btn btn-sm btn-danger btnRemove' data-payid='" + PayID + "'>"
				+ "</td></tr>";
	}
	 
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the items.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 } 
	
	
	
	
	
	//Update payment details
	public String updatePayment(String payID,String cname, String cno, String exmnth, String exyr,String amount, String cvv, String tdate)
	{ 
		
	 Connection con = connect(); 
	 String output = ""; 
	try
	 { 
	 
		if (con == null) 
	 { 
			return "Error while connecting to the database"; 
	 } 
		
	 // create a prepared statement
	 String query = "UPDATE payment SET CardName=?,CardNumber=?,Ex_Month=?,Ex_Year=?,Amount=?,CV=?,Date=? WHERE PayID=?"; 
	 
	 // binding values
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 preparedStmt.setString(1, cname); 
	 preparedStmt.setString(2, cno); 
	 preparedStmt.setString(3, exmnth); 
	 preparedStmt.setString(4, exyr); 
	 preparedStmt.setDouble(5, Double.parseDouble(amount)); 
	 preparedStmt.setString(6, cvv);
	 preparedStmt.setString(7, tdate);
	 preparedStmt.setInt(8,Integer.parseInt(payID));
	
	 //execute the statement
	 preparedStmt.execute(); 
	 con.close(); 


	 String newPayment = readPayment(); 
		output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
	
	} catch(Exception e) {
		output = "{\"status\":\"error\", \"data\": \"Failed to update the record\"}";
		System.err.println(e.getMessage());
	 } 
	return output; 
	}
	
	
	
	
	
	//delete payment details
	public String deletePayment(String PayID)
	 {
	 String output = "";
	 Connection con = connect();
	 
	 try
	 {
	 
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 
	 // create a prepared statement
	 String query = "DELETE FROM payment where PayID=?";
	 
	 // binding values
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 preparedStmt.setInt(1, Integer.parseInt(PayID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 
	 String newPayment = readPayment(); 
		output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
	
	} catch(Exception e) {
		output = "{\"status\":\"error\", \"data\": \"Failed to delete the record\"}";
		System.err.println(e.getMessage());
	 } 
	return output; 
	}
}
