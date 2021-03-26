package twins.data;

import twins.digitalItemsAPI.ItemBoundary;
import twins.userAPI.UserBoundary;

public interface EntityConverter {
	
	public UserBoundary toBoundary(UserEntity entity);

	public UserEntity fromBoundary(UserBoundary boundary);
	
	public ItemBoundary toBoundary(ItemEntity entity);

	public ItemEntity fromBoundary(ItemBoundary boundary);
	

}
