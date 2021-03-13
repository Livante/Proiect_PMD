package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
public class History {

	private String roomId; 
	private String function;
	private int badgeCode;
	private Date accessDate; 
	private String verdict;
	
	public History(String roomId, String function, int badgeCode, Date accessDate, String verdict) {
		this.roomId=roomId;
		this.function=function;
		this.badgeCode=badgeCode;
		this.accessDate=accessDate;
		this.verdict=verdict;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public int getBadgeCode() {
		return badgeCode;
	}

	public void setBadgeCode(int badgeCode) {
		this.badgeCode = badgeCode;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getVerdict() {
		return verdict;
	}

	public void setVerdict(String verdict) {
		this.verdict = verdict;
	}
	
	public static void connectToDatabase(Operation op, String database) throws ClassNotFoundException, SQLException, IOException {
		if (database.contains("history")) {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(database, "root", "");
			System.out.println();
			System.out.println("Database is connected !");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  history");
			Client.populateHistoryList(op, rs);
			conn.close();
		}
	}
	
	public static void writeInDb(Operation op, String database, String roomId, String function, int badgeCode, String verdict) {
		try {
			
		if (database.contains("history")) {
			Calendar calendar = Calendar.getInstance();
			Date date=new Date();
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(database, "root", "");
			
			System.out.println(roomId+" "+function+" "+badgeCode+" "+verdict+" "+date);
			String query = " insert into history (roomId, function, badgeCode, accessDate, verdict)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
//			preparedStmt.setString(2, roomId);
//			preparedStmt.setString(3, function);
//			preparedStmt.setInt(4, badgeCode);
//			preparedStmt.setDate(5, (java.sql.Date) date);
//			preparedStmt.setString(6, verdict);
//			
			preparedStmt.setString(1, roomId);
			preparedStmt.setString(2, function);
			preparedStmt.setInt(3, badgeCode);
			preparedStmt.setDate(4, (java.sql.Date) date);
			preparedStmt.setString(5, verdict);
			
			
			preparedStmt.executeUpdate(query);
		
			conn.close();
		}
		
		}catch(Exception e) {}
	}
}
