package twins.operationsAPI;

import twins.digitalItemsAPI.ItemId;

public class Item {
	private ItemId itemId;

	public Item() {
	}

	public Item(ItemId itemId) {
		super();
		this.itemId = itemId;
	}

	public ItemId getItemId() {
		return itemId;
	}

	public void setItemId(ItemId itemId) {
		this.itemId = itemId;
	}
	
}
