package demo;
import java.util.Date;

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
	
	private itemId item;
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimestamp;
	private createdBy maker;
	private location location;
	private itemAttributes itemAtt;
	
	public ItemBoundary() {
	
	}
	public ItemBoundary(itemId item, String type, String name, Boolean active, Date createdTimestamp, createdBy maker,
			demo.location location, itemAttributes itemAtt) {
		super();
		this.item = item;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.maker = maker;
		this.location = location;
		this.itemAtt = itemAtt;
	}

	public itemId getItem() {
		return item;
	}
	public void setItem(itemId item) {
		this.item = item;
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
	public createdBy getMaker() {
		return maker;
	}
	public void setMaker(createdBy maker) {
		this.maker = maker;
	}
	public location getLocation() {
		return location;
	}
	public void setLocation(location location) {
		this.location = location;
	}
	public itemAttributes getItemAtt() {
		return itemAtt;
	}
	public void setItemAtt(itemAttributes itemAtt) {
		this.itemAtt = itemAtt;
	}
	
}
