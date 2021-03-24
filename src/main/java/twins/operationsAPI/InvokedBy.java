package twins.operationsAPI;

import twins.userAPI.UserId;

public class InvokedBy {
	private UserId userId;
	
	public InvokedBy() {
		
	}

	public InvokedBy(twins.userAPI.UserId userId) {
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
