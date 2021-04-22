package twins.logic;

import java.util.List;
import java.util.Optional;

import twins.digitalItemsAPI.ItemBoundary;

public interface UpdatedItemsService extends ItemsService{
	
	public void bindItemToItem(String parentId, String childId);
	
	public List<ItemBoundary> getAllChildren(String parentId);
	
	public Optional<ItemBoundary> getAllParents(String childId);
	
	

}
