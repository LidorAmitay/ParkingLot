package demo;

//"space" : "2021.items"
//"id" : "99"
public class itemId {
	
	private String space;
	private long id;
	
	public itemId() {
	}
	
	public itemId(String space, long id) {
		super();
		this.space = space;
		this.id = id;
	}
	
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	

}
