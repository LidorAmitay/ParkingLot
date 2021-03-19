package demo;

// Java Bean used as Boundary - contains a default constructor + get/set
// JSON: { "message":"hello world"}
public class MessageBoundary {
	private String message;
	private String name;
	
	public MessageBoundary() {
	}

	public MessageBoundary(String message, String name) {
		super();
		this.message = message;
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
