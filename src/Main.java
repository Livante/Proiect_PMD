import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;


public class Main {

    static SerialPort comPort;
    static String stringBuffer;

    private static final class DataListener implements SerialPortDataListener
    {
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }

        @Override
        public void serialEvent(SerialPortEvent event)
        {
            //System.out.println("In event listener.");
            if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                return;
            //System.out.println("Past event type check.");
            byte[] newData = new byte[comPort.bytesAvailable()];
            int numRead = comPort.readBytes(newData, newData.length);
            stringBuffer = new String(newData,0,numRead);
            //System.out.println("Read " + numRead + " bytes.");
            System.out.println(stringBuffer);


        }
    }

    static public void main(String[] args)
    {
        comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        System.out.println("COM port open: " + comPort.getDescriptivePortName());
        DataListener listener = new DataListener();
        comPort.addDataListener(listener);
        System.out.println("Event Listener open.");
    }
}