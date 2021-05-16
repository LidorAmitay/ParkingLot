package twins.digitalItemsAPI;
import java.util.Date;
import java.util.Map;

/*"itemId":{
	"space":"2021b.twins",
	"id":"99"
},
"type":"demoType",
"name":"demo item",
"active":true,
"createdTimestamp":"2021-03-07T09:55:05.248+0000",
"createdBy":{
	"userId":{
		"space":"2021b.twins",
		"email":"user2@demo.com"
}
},
"location":{
	"lat":32.115139,
	"lng":34.817804
},
"itemAttributes":{
	"key1":"can be set to any value you wish",
	"key2":"you can also name the attributes any name you like",
	"key3":58,
	"key4":false
} */
public class ItemBoundary {
	
	private ItemId itemId;
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimestamp;
	private CreatedBy createdBy;
	private Location location;
	private Map<String, Object> itemAttributes;
	 
	public ItemBoundary() {
	
	}
	
	public ItemBoundary(ItemId itemId, String type, String name, Boolean active, Date createdTimestamp, CreatedBy createdBy,
			Location location, Map<String,Object> itemAttributes) {
		super();
		this.itemId = itemId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.location = location;
		this.itemAttributes = itemAttributes;
	}

	public ItemId getItemId() {
		return itemId;
	}
	public void setItemId(ItemId itemId) {
		this.itemId = itemId;
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
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public CreatedBy getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<String, Object> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(Map<String, Object> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
	
	
}
