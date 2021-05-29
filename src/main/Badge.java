package main;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Badge {

	private String badgeId;
	private String function;
	private int badgeCode;

	public Badge(String badgeId, String function, int badgeCode) {
		this.badgeId = badgeId;
		this.function = function;
		this.badgeCode = badgeCode;
	}
	
	public String getBadgeId() {
		return badgeId;
	}
	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
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
	
	public static void connectToDatabase(Operation op, String database) 
			throws ClassNotFoundException, SQLException, IOException {
		if (database.contains("badge")) {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(database, "root", "");
			System.out.println();
			System.out.println("Database is connected !");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM  badge");
			Client.populateBadgeList(op, rs);
			conn.close();
		}
	}
}
