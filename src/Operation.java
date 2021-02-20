import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Operation {
	
//	public static final String ADMIN="123456";
//	public static final String HISTORY="654321";
	
	
	public List <Room> roomList= new ArrayList <Room>();
	public List <Badge> badgeList= new ArrayList <Badge>();
	private boolean accessFlag=false;
	Keyboard scanner=new Keyboard();
	
	
	
	public boolean isAccessFlag() {
		return accessFlag;
	}


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
	

	public void doOperation(String badgeCode1, String sala) throws IOException, ClassNotFoundException, SQLException {
				
			boolean existingEmp=false;
	
//			afis();
			
				
			String function = "";
			String badgeId="";
			String badgeCode=""+badgeCode1;
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
					if(Client.SALA_A1.equals(iterRoom.getRoomId())) {
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
					accessFlag=false;
					System.out.println("ACCESS DENIED");
				}	
			}
			else 
			{
				accessFlag=false;
				System.out.println("NON EXISTENT EMPLOYEE");
			}
			FileWriter fw=new FileWriter("History_"+Client.SALA_A1+".html",true);
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

	  

