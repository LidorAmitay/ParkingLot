package demo.DigitalItemsAPI;

//"space" : "2021.items"
//"id" : "99"
public class itemId {
	
	private String space;
	private long id;
	private static long idCount = 1;


	public itemId() {
	}
	
	public itemId(String space, long id) {
		super();
		this.space = space;
		this.id = id;
	}
	
	public static long newId() {
		return itemId.idCount++;
	}
	
	public static long getIdCount() {
		return idCount;
	}

	public static void setIdCount(long idCount) {
		itemId.idCount = idCount;
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
