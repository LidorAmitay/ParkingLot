package twins.data;

import java.util.Date;


public class ItemEntity {
	
	private String space;
	private String email;
	private String id;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private double lat;
	private double lng;
	//private ItemAttributes itemAttributes; what to do with it?
	
	/*
	1) What we have to do with the spaces of the user and the item? it is different?
	2) What we have to do with the ItemAttributes?
	*/
	
	
	//Constructor
	public ItemEntity() {
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
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getActive() {
		return active;
	}
	
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	
}
