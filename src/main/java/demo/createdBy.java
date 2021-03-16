package demo;
//"user":{
//			"space":"2021b.twins",
//			"email":"user2@demo.com" } 
public class createdBy {
	
	private userIdBoundary user;

	public createdBy() {
	}
	public createdBy(userIdBoundary user) {
		super();
		this.user = user;
	}

	public userIdBoundary getUser() {
		return user;
	}

	public void setUser(userIdBoundary user) {
		this.user = user;
	}
	
}
