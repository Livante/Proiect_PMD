import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Operation {
	
//	public static final String ADMIN="123456";
//	public static final String HISTORY="654321";
	public static final String NUME_SALA="A1";
	
	public List <Room> roomList= new ArrayList <Room>();
	public List <Badge> badgeList= new ArrayList <Badge>();

	Keyboard scanner=new Keyboard();
	
	public void afis() {
		System.out.println("badge:");
		for (Badge iter : badgeList) {
			System.out.println(iter.getBadgeCode()+" "+iter.getBadgeId()+" "+iter.getFunction());
		}
		System.out.println("room:");
		for (Room iter : roomList) {
			System.out.println(iter.getBadgeId()+" "+iter.getRoomId());
		}
	
	}
	

	public void doOperation() throws IOException, ClassNotFoundException, SQLException {
			boolean accessFlag=false;	
			boolean existingEmp=false;
			
//			afis();
			String badgeCode=scanner.readFromMyKeyboard("Enter the code!\n");
			while(badgeCode.length()!=4) {
				badgeCode=scanner.readFromMyKeyboard("Reenter the code!\n");
			}
				
			String function = "";
			String badgeId="";
			for (Badge iterBadge : badgeList) {
				if(iterBadge.getBadgeCode()==Integer.parseInt(badgeCode)) {
					function=iterBadge.getFunction();
					badgeId=iterBadge.getBadgeId();
					existingEmp=true;
					break;
				}
			}
			if(existingEmp) {
				int badgeIdFromRoom=0;
				int badgeIdNum=Integer.parseInt(badgeId);
				
				for (Room iterRoom : roomList) {
					if(NUME_SALA.equals(iterRoom.getRoomId())) {
						badgeIdFromRoom=Integer.parseInt(iterRoom.getBadgeId());
						if((badgeIdFromRoom & badgeIdNum)>0) {
							System.out.println(badgeIdFromRoom );
							System.out.println(badgeIdNum);
							System.out.println(badgeIdFromRoom & badgeIdNum);
							System.out.println("ACCESS GRANTED");
							accessFlag=true;
							break;
						}
					}
				}
			
				if(!accessFlag) {				
					System.err.println("ACCESS DENIED");
				}	
			}
			else {System.err.println("NON EXISTENT EMPLOYEE");
			}
			FileWriter fw=new FileWriter("History_"+NUME_SALA+".html",true);
			writeHTML(badgeCode,accessFlag,function,fw);
			fw.close();
			}
			
	private void writeHTML(String s, boolean accessFlag,String employee, FileWriter pw) throws IOException {
			Date d=new Date(); 
			if(!accessFlag) {
				pw.write("<p><font color=\"red\">");
				pw.write("\nDate "+d+" Persons code "+s+" employee: "+employee+" ACCESS DENIED\n");
				pw.write("</br>");
				pw.write("</font></p>");
				pw.write("<p><font color=\"red\">");	
			}
			else {
				pw.write("<p><font color=\"green\">");
				pw.write("\nDate "+d+" Persons code "+s+" employee: "+employee+" ACCESS GRANTED\n");
				pw.write("</br>");
				pw.write("<p><font color=\"green\">");
			}
	}
}

	  

