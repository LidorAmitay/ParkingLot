package twins.OperationsAPI;

import twins.UserId;

public class InvokedBy {
	private UserId userId;
	
	public InvokedBy() {
		
	}

	public InvokedBy(twins.UserId userId) {
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
