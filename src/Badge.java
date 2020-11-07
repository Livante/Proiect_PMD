
public class Badge {

	private String badgeId;
	private String function;
	private int badgeCode;

	public Badge(String badgeId, String function, int badgeCode) {
		this.badgeId = badgeId;
		this.function = function;
		this.badgeCode = badgeCode;
	}
	
	public String getBadgeId() {
		return badgeId;
	}
	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
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
	
	
	
	
}
