package twins.OperationsAPI;

import java.util.Date;


/*{
    "operationId":{
	"space":"2021b.twins",
	"id":"99"
},
"type":"demoType",
"item":{
        "itemId":{
            "space":"2021b.twins",
	        "id":"99"
        }
    },
"createdTimestamp":"2021-03-07T09:55:05.248+0000",
"invokedBy":{
	"userId":{
		"space":"2021b.twins",
		"email":"user2@demo.com"
}
},
"operationAttributes":{
	"key1":"can be set to any value you wish",
	"key2":"you can also name the attributes any name you like",
	"key3":58,
	"key4":false
}
}*/
public class OperationBoundary {
	
	private OperationId operationId;
	private String type;
	private Item item;
	private Date createdTimestamp;
	private InvokedBy invokedBy;
	private OperationAttributes operationAttributes;
	
	
	public OperationBoundary() {	
	}
	
	public OperationBoundary(OperationId operationId, String type,
			Item item, Date createTimestamp, InvokedBy invokedBy,
			OperationAttributes operationAttributes) {
		super();
		this.operationId = operationId;
		this.type = type;
		this.item = item;
		this.createdTimestamp = createTimestamp;
		this.invokedBy = invokedBy;
		this.operationAttributes = operationAttributes;
	}



	public OperationId getOperationId() {
		return operationId;
	}


	public void setOperationId(OperationId operationId) {
		this.operationId = operationId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}

	
	public Date getCreateTimestamp() {
		return createdTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createdTimestamp = createTimestamp;
	}

	public InvokedBy getInvokedBy() {
		return invokedBy;
	}



	public void setInvokedBy(InvokedBy invokedBy) {
		this.invokedBy = invokedBy;
	}



	public OperationAttributes getOperationAttributes() {
		return operationAttributes;
	}



	public void setOperationAttributes(OperationAttributes operationAttributes) {
		this.operationAttributes = operationAttributes;
	}
	
	
}
