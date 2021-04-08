package twins.data;

import java.util.Date;
import java.util.Map;


public class ItemEntity {
	
	private String itemId;
	private String userId;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private double lat;
	private double lng;
	private Map<String, Object> itemAttributes;
	
	//Constructor
	public ItemEntity() {
	}
	
	public Map<String, Object> getItemAttribute() {
		return itemAttributes;
	}



	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<String, Object> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(Map<String, Object> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	public void setItemAttribute(Map<String, Object> itemAttribute) {
		this.itemAttributes = itemAttribute;
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
