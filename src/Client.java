import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class Client {

	static SerialPort comPort;
    static String stringBuffer;
	public static Operation opTry=new Operation();
    
	private static final class DataListener implements SerialPortDataListener
	{
		public void sendByteImmediately(byte b) throws Exception {
			comPort.writeBytes(new byte[]{b}, 1);
		}

		@Override
		public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
		
		@Override
		public void serialEvent(SerialPortEvent event)
		{
			if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
				return;
			byte[] newData = new byte[comPort.bytesAvailable()];
			int numRead = comPort.readBytes(newData, newData.length);
			stringBuffer = new String(newData,0,numRead);
			System.out.println(stringBuffer);
			try {
				if(stringBuffer.length()==6) {
					System.out.println("String buffer: "+stringBuffer.substring(0, 4));
					opTry.doOperation(stringBuffer.substring(0, 4));
					boolean sendMsgBack=opTry.isAccessFlag();
					System.out.println(opTry.isAccessFlag());
					byte buffer;
					if(sendMsgBack==true) {
						buffer= 1;
						System.out.println("GRANTED");
						System.out.println("BUFFER: 1");
						newData = new byte[comPort.bytesAvailable()];
						sendByteImmediately(buffer);
					}else {
						buffer=2;
						System.out.println("DENIED");
						System.out.println("BUFFER: 2");
						newData = new byte[comPort.bytesAvailable()];
						sendByteImmediately(buffer);
					}
					
				}
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    
	public static void main(String[] args) {
			try
			{
//				Operation op=new Operation();
//				connectionForDatabases(op,"jdbc:mysql://localhost/badge");
//				connectionForDatabases(op,"jdbc:mysql://localhost/room");

				connectionForDatabases(opTry,"jdbc:mysql://localhost/badge");
				connectionForDatabases(opTry,"jdbc:mysql://localhost/room");
				
				comPort = SerialPort.getCommPorts()[0];
		        comPort.openPort();
		        System.out.println("COM port open: " + comPort.getDescriptivePortName());
		        DataListener listener = new DataListener();
		        comPort.addDataListener(listener);
		        System.out.println("Event Listener open.");
			
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
