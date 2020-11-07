
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
	
	

}
