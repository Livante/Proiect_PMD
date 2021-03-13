package java;

import java.util.Date;

public class History {

	private String roomId; 
	private String function;
	private int badgeCode;
	private Date accessDate; 
	private String verdict;
	
	public History(String roomId, String function, int badgeCode, Date accessDate, String verdict) {
		this.roomId=roomId;
		this.function=function;
		this.badgeCode=badgeCode;
		this.accessDate=accessDate;
		this.verdict=verdict;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getVerdict() {
		return verdict;
	}

	public void setVerdict(String verdict) {
		this.verdict = verdict;
	}
	
	
}
