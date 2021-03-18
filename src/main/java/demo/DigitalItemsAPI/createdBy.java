package demo.DigitalItemsAPI;

import demo.userId;

//"user":{
//			"space":"2021b.twins",
//			"email":"user2@demo.com" } 
public class createdBy {
	
	private userId userId;

	public createdBy() {
	}
	public createdBy(userId userId) {
		super();
		this.userId = userId;
	}

	public userId getUserId() {
		return userId;
	}

	public void setUserId(userId userId) {
		this.userId = userId;
	}
	
}
