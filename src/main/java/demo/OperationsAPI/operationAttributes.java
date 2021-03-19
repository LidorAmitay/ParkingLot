package demo.OperationsAPI;

import java.util.Map;

public class operationAttributes {
	
private Map<String, Object> map;
	
	public operationAttributes() {
		
	}
	
	public operationAttributes(Map<String, Object> map) {
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
