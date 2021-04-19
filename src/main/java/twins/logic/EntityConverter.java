package twins.logic;

import java.util.Map;

import twins.data.ItemEntity;
import twins.data.OperationEntity;
import twins.data.UserEntity;
import twins.digitalItemsAPI.ItemBoundary;
import twins.operationsAPI.OperationBoundary;
import twins.userAPI.UserBoundary;

public interface EntityConverter {
	
	public UserBoundary toBoundary(UserEntity entity);

	public UserEntity fromBoundary(UserBoundary boundary);
	
	public ItemBoundary toBoundary(ItemEntity entity);

	public ItemEntity fromBoundary(ItemBoundary boundary);
	 
	public OperationBoundary toBoundary(OperationEntity entity);

    public OperationEntity fromBoundary(OperationBoundary boundary);
	
    public String fromMapToJson (Map<String, Object> value); // marshalling: Java->JSON
	
	public Map<String, Object> fromJsonToMap (String json); // unmarshalling: JSON->Java

}
