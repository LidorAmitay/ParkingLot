package twins.DigitalItemsAPI;

//"space" : "2021.items"
//"id" : "99"
public class ItemId {
	
	private String space;
	private String id;


	public ItemId() {
	}
	
	public ItemId(String space, String id) {
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
