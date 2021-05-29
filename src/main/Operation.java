package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Operation {

//ADMIN="123456";
//HISTORY="654321";

	public List<Room> roomList = new ArrayList<Room>();
	public List<Badge> badgeList = new ArrayList<Badge>();
	public List<History> historyList;
	private boolean accessFlag = false;

	public boolean isAccessFlag() {
		return accessFlag;
	}

	public void doOperation(String badgeCodeAndRoomNumber, String sala) throws IOException, ClassNotFoundException, SQLException {

		boolean existingEmp = false;
		accessFlag = false;

		String function = "";
		String badgeId = "";
		String badgeCode = "" + badgeCodeAndRoomNumber;
		for (Badge iterBadge : badgeList) {
			if (iterBadge.getBadgeCode() == Integer.parseInt(badgeCode)) {
				function = iterBadge.getFunction();
				badgeId = iterBadge.getBadgeId();
				existingEmp = true;
				break;
			}
		}
		if (existingEmp) {
			int badgeIdFromRoom = 0;
			int badgeIdNum = Integer.parseInt(badgeId);

			for (Room iterRoom : roomList) {
				if (sala.equals(iterRoom.getRoomId())) {
					badgeIdFromRoom = Integer.parseInt(iterRoom.getBadgeId());
					if ((badgeIdFromRoom & badgeIdNum) > 0) {
						System.out.println("ACCESS GRANTED");
						accessFlag = true;
						History.writeInDb(Client.opTry, "jdbc:mysql://localhost/history", sala, function, Integer.parseInt(badgeCode), Client.ACCESS_GRANTED);
						break;
					}
				}
			}

			if (!accessFlag) {
				accessFlag = false;
				System.out.println("ACCESS DENIED");
				History.writeInDb(Client.opTry, "jdbc:mysql://localhost/history", sala, function, Integer.parseInt(badgeCode), Client.ACCESS_DENIED);
			}
		} else {
			accessFlag = false;
			function="Non existent";
			History.writeInDb(Client.opTry, "jdbc:mysql://localhost/history", sala, function, Integer.parseInt(badgeCode), Client.NON_EXISTENT_EMPLOYEE);
		}

	}
}
