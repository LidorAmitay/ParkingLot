package demo;

//{
//"space":"2021b.twins",
//"email":"user@demo.com"
//}

public class userIdBoundary {
	
	private String space;
	private String email;

	public userIdBoundary() {
	}

	public userIdBoundary(String space, String email) {
		super();
		this.space = space;
		this.email = email;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
