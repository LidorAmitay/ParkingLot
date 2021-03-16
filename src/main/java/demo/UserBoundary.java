package demo;

//{
//"clientID":"1",
//"firstName":"Jane",
//"lastName":"Smith",
//"email":"Jane@gmail.com",
//"password":"asdfg",
//"creditCard":{credit} *********
//"arrayOfCars":{cars} *********
//}
public class UserBoundary {
	private userIdBoundary userId;
	private String role;
	private String username;
	private String avatar;
	
	public UserBoundary() {
	}

	public UserBoundary(userIdBoundary userId, String role, String username,String avatar) {
		super();
		this.userId = userId;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}

	public userIdBoundary getUserId() {
		return userId;
	}

	public void setUserId(userIdBoundary userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
	
	