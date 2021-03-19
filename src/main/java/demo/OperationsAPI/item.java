package demo.OperationsAPI;

import demo.DigitalItemsAPI.itemId;

public class item {
	private itemId itemid;

	public item() {
	}

	public item(itemId itemid) {
		super();
		this.itemid = itemid;
	}

	public itemId getItemid() {
		return itemid;
	}

	public void setItemid(itemId itemid) {
		this.itemid = itemid;
	}
	
}
