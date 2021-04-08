package twins.operationsAPI;

import twins.digitalItemsAPI.ItemId;

public class Item {
	private ItemId itemid;

	public Item() {
	}

	public Item(ItemId itemid) {
		super();
		this.itemid = itemid;
	}

	public ItemId getItemid() {
		return itemid;
	}

	public void setItemid(ItemId itemid) {
		this.itemid = itemid;
	}
	
}