package twins.data;

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
	

}