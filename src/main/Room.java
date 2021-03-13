package main;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Room {
	
	private String roomId;
	private String badgeId;
	
	public Room(String roomId, String badgeId) {
		this.roomId = roomId;
		this.badgeId = badgeId;
	}
	
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getBadgeId() {
		return badgeId;
	}
	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
	}
	
	public static void connectToDatabase(Operation op, String database) throws ClassNotFoundException, SQLException, IOException {
		if (database.contains("room")) {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(database, "root", "");
			System.out.println();
			System.out.println("Database is connected !");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  room");
			Client.populateRoomList(op, rs);
			conn.close();
		}
	}
}
