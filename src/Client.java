import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Client {

	public final static int MAX_PORTS = 2;
	
    public static final String SALA_A1 = "A1";
    public static final String SALA_A2 = "A2";
    public static final String SALA_A3 = "A3";
    public static final String SALA_A4 = "A4";
    public static final String SALA_A5 = "A5";
    public static final String SALA_A6 = "A6";
    public static final String SALA_A7 = "A7";
    public static final String SALA_A8 = "A8";

    static SerialPort availablePort[] = new SerialPort[MAX_PORTS];
    static List<DataListener> listenerList = new ArrayList<DataListener>();
    
    static String stringBuffer;
	public static Operation opTry=new Operation();
    
	private static final class DataListener implements SerialPortDataListener{
		
		public void sendByteImmediately(byte b) throws Exception {
			int i=listenerList.indexOf(this);
			availablePort[i].writeBytes(new byte[]{b}, 1);
		}


		@Override
		public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
		
		@Override
		public void serialEvent(SerialPortEvent event){
		
			int i=listenerList.indexOf(this);
		
			if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
				return;
			
			byte[] newData = new byte[availablePort[i].bytesAvailable()];
			int numRead = availablePort[i].readBytes(newData, newData.length);
			stringBuffer = new String(newData,0,numRead);
			System.out.println(stringBuffer);
			System.out.println(stringBuffer.length());
			try {
				if(stringBuffer.length()==7) {
					System.out.println("String buffer: "+stringBuffer.substring(1, 5)+" Sala: "+stringBuffer.charAt(0));
					switch(stringBuffer.charAt(0)) {
					
					case '1':
						opTry.doOperation(stringBuffer.substring(1, 5), SALA_A1);
						break;
						
					case '2':
						opTry.doOperation(stringBuffer.substring(1, 5), SALA_A2);
						break;
						
					case '3':
						opTry.doOperation(stringBuffer.substring(1, 5), SALA_A3);
						break;
						
					case '4':
						opTry.doOperation(stringBuffer.substring(1, 5), SALA_A4);
						break;
						
					case '5':
						opTry.doOperation(stringBuffer.substring(1, 5), SALA_A5);
						break;
						
					case '6':
						opTry.doOperation(stringBuffer.substring(1, 5), SALA_A6);
						break;
						
					case '7':
						opTry.doOperation(stringBuffer.substring(1, 5), SALA_A7);
						break;
						
					case '8':
						opTry.doOperation(stringBuffer.substring(1, 5), SALA_A8);
						break;
					}
					
					boolean sendMsgBack=opTry.isAccessFlag();
					System.out.println(opTry.isAccessFlag());
					byte buffer;
					if(sendMsgBack==true) {
						buffer= 1;
						System.out.println("GRANTED");
						System.out.println("BUFFER: 1");
						newData = new byte[availablePort[i].bytesAvailable()];
						sendByteImmediately(buffer);
					}else {
						buffer=2;
						System.out.println("DENIED");
						System.out.println("BUFFER: 2");
						newData = new byte[availablePort[i].bytesAvailable()];
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
			try{
				connectionForDatabases(opTry,"jdbc:mysql://localhost/badge");
				connectionForDatabases(opTry,"jdbc:mysql://localhost/room");
				  
				for(int portIndex=0; portIndex<MAX_PORTS; portIndex++) {
					availablePort[portIndex]=SerialPort.getCommPorts()[portIndex];	
					availablePort[portIndex].openPort();
			        System.out.println("COM port open: " + availablePort[portIndex].getDescriptivePortName());
			        listenerList.add(new DataListener());
			        availablePort[portIndex].addDataListener(listenerList.get(portIndex));
			        System.out.println("Event Listener open.");
				}
							
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
		
		if(database.contains("room")){
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