package twins.DigitalItemsAPI;

import twins.UserAPI.UserId;

//"user":{
//			"space":"2021b.twins",
//			"email":"user2@demo.com" } 
public class CreatedBy {
	
	private UserId userId;

	public CreatedBy() {
	}
	public CreatedBy(UserId userId) {
		super();
		this.userId = userId;
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}
	
}
