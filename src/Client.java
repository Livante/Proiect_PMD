import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.*;
import java.util.*;


public class Client {

	public static void main(String[] args) {
			try
			{
				Operation op=new Operation();
				
				connectionForDatabases(op,"jdbc:mysql://localhost/badge");
				connectionForDatabases(op,"jdbc:mysql://localhost/room");
			}catch(Exception e){
				System.out.print("Do not connect to DB - Error:"+e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		}
	}

	public static void connectionForDatabases(Operation op,String database) throws ClassNotFoundException, SQLException, IOException {
		
		if(database.contains("badge")) {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(database,"root", "");
			System.out.println();
			System.out.println("Database is connected !");
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM  badge");
			populateBadgeList(op, rs);
			conn.close();
			}
		
		if(database.contains("room"))
			
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = null;
					conn = DriverManager.getConnection(database,"root", "");
					System.out.println();
					System.out.println("Database is connected !");
					Statement st=conn.createStatement();
					ResultSet rs=st.executeQuery("SELECT * FROM  room");
					populateRoomList(op, rs);
					conn.close();
					
							
					while(true) {				
						op.doOperation();
					}
				}			
	}

	public static void populateBadgeList(Operation op, ResultSet rs) throws SQLException {
		String badgeId;
		String function;
		int badgeCode;
		
		while(rs.next()) {	
			badgeId =rs.getString(1);
			function = rs.getString(2);
			badgeCode=rs.getInt(3);
			
			Badge badgeObject =new Badge(badgeId,function,badgeCode);
			op.badgeList.add(badgeObject);
			
		
		}
	}
	
	public static void populateRoomList(Operation op, ResultSet rs) throws SQLException {
		String roomId;
		String badgeId;
		
		while(rs.next()) {
			roomId = rs.getString(1);
			badgeId =rs.getString(2);
			
			Room roomObject = new Room(roomId,badgeId);		
			op.roomList.add(roomObject);			
		}
	}
}
