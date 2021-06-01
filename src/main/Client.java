package main;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Client {

	public final static int MAX_PORTS = 8;

	public static final String ROOM_NAMES[]= {
			"A1",
			"A2",
			"A3",
			"A4",
			"A5",
			"A6",
			"A7",
			"A8"
			};

	public static final String NON_EXISTENT_EMPLOYEE = "Non existent employee";
	public static final String ACCESS_GRANTED = "Access Granted";
	public static final String ACCESS_DENIED = "Access Denied";

	static SerialPort availablePort[] = new SerialPort[MAX_PORTS];
	static List<DataListener> listenerList = new ArrayList<DataListener>();

	static String stringBuffer;
	public static Operation opTry = new Operation();

	private static final class DataListener implements SerialPortDataListener {

		public void sendByteImmediately(byte b) throws Exception {
			int i = listenerList.indexOf(this);
			availablePort[i].writeBytes(new byte[] { b }, 1);
		}

		@Override
		public int getListeningEvents() {
			return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
		}

		@Override
		public void serialEvent(SerialPortEvent event) {

			int i = listenerList.indexOf(this);

			if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
				return;

			byte[] newData = new byte[availablePort[i].bytesAvailable()];
			int numRead = availablePort[i].readBytes(newData, newData.length);
			stringBuffer = new String(newData, 0, numRead);
	
			try {
				if (stringBuffer.length() == 7) {
					
					String roomVerifier=""+stringBuffer.charAt(0);
					
					opTry.doOperation(stringBuffer.substring(1, 5), ROOM_NAMES[Integer.parseInt(roomVerifier)]);

					boolean sendMsgBack = opTry.isAccessFlag();
		
					byte buffer;
					if (sendMsgBack == true) {
						buffer = 1;
						System.out.println("ACCESS GRANTED");
						
					} else {
						buffer = 2;
						System.out.println("ACCESS DENIED");
					}
					
					newData = new byte[availablePort[i].bytesAvailable()];
					sendByteImmediately(buffer);

				}
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			
			connectionForDatabases(opTry, "jdbc:mysql://localhost/badge");
			connectionForDatabases(opTry, "jdbc:mysql://localhost/room");
			connectionForDatabases(opTry, "jdbc:mysql://localhost/history");

			for (int portIndex = 0; portIndex < MAX_PORTS; portIndex++) {
				
				availablePort[portIndex] = SerialPort.getCommPorts()[portIndex];
				availablePort[portIndex].openPort();
				System.out.println("COM port open: " + availablePort[portIndex].getDescriptivePortName());
				
				listenerList.add(new DataListener());
				availablePort[portIndex].addDataListener(listenerList.get(portIndex));
				System.out.println("Event Listener open.");
			}

		} catch (Throwable e) {
			
			try {
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e1){	
				
			}
		}
	}

	public static void connectionForDatabases(Operation op, String database)
			throws ClassNotFoundException, SQLException, IOException {
		
		Badge.connectToDatabase(op, database);
		Room.connectToDatabase(op, database);
		History.connectToDatabase(op, database);
		
	}

	public static void populateBadgeList(Operation op, ResultSet rs) throws SQLException {
		
		String badgeId;
		String function;
		int badgeCode;

		while (rs.next()) {
		
			badgeId = rs.getString(1);
			function = rs.getString(2);
			badgeCode = rs.getInt(3);

			Badge badgeObject = new Badge(badgeId, function, badgeCode);
			
			op.badgeList.add(badgeObject);

		}
	}

	public static void populateRoomList(Operation op, ResultSet rs) 
		throws SQLException {
		
		String roomId;
		String badgeId;
		
		while (rs.next()) {
			
			roomId = rs.getString(1);
			badgeId = rs.getString(2);
			
			Room roomObject = new Room(roomId, badgeId);
			op.roomList.add(roomObject);
		}
	}

	public static void populateHistoryList(Operation op, ResultSet rs) throws SQLException {
		String roomId;
		String function;
		int badgeCode;
		Date accessDate;
		String verdict;

		op.historyList= new ArrayList<History>();
		while (rs.next()) {
			
			roomId = rs.getString(2);
			function = rs.getString(3);
			badgeCode = rs.getInt(4);
			accessDate = rs.getDate(5);
			verdict = rs.getString(6);
			
			History historyObject = new History(roomId, function, badgeCode, accessDate, verdict);
			op.historyList.add(historyObject);
		}
	}
}