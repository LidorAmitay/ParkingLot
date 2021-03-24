package twins.DigitalItemsAPI;

import java.util.Map;

//"key1":"can be set to any value you wish",
//"key2":"you can also name the attributes any name you like",
//"key3":58,
//"key4":false
public class ItemAttributes {
	private Map<String, Object> map;
	
	public ItemAttributes() {
		
	}
	
	public ItemAttributes(Map<String, Object> map) {
		super();
		this.map = map;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	

}
