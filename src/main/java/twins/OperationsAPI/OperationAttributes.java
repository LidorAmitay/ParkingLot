package twins.OperationsAPI;

import java.util.Map;

public class OperationAttributes {
	
	private Map<String, Object> map;
	
	public OperationAttributes() {
		
	}
	
	public OperationAttributes(Map<String, Object> map) {
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
