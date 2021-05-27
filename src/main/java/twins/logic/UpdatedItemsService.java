package twins.logic;

import java.util.List;
import java.util.Optional;

import twins.digitalItemsAPI.ItemBoundary;

public interface UpdatedItemsService extends ItemsService{
	
	public void bindItemToItem(String userId, String parentId, String childId);
	
	public List<ItemBoundary> getAllChildren(String userId, String parentId);
	
	public Optional<ItemBoundary> getAllParents(String userId, String childId);
	
	public List<ItemBoundary> getAllItems(String userSpace, String userEmail, int page, int size);
	
	

}
