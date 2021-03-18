package demo.DigitalItemsAPI;

import java.util.Map;

//"key1":"can be set to any value you wish",
//"key2":"you can also name the attributes any name you like",
//"key3":58,
//"key4":false
public class itemAttributes {
	private Map<String, Object> map;
	
	public itemAttributes() {
		
	}
	
	public itemAttributes(Map<String, Object> map) {
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
