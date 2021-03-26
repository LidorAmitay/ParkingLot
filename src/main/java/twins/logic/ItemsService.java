package twins.logic;

import java.util.List;

import twins.digitalItemsAPI.ItemBoundary;


public interface ItemsService {

	public ItemBoundary createItem(String userSpace, String userEmail, ItemBoundary item);

	public ItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId, ItemBoundary update);

	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId);

	public List<ItemBoundary> getAllItems(String userSpace, String userEmail);
	
	public void deleteAllItems(String adminSpace, String adminEmail);
	
}
