package demo.OperationsAPI;

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
	
	private operationId operationId;
	private String type;
	private item item;
	private Date createTimestamp;
	private invokedBy invokedBy;
	private operationAttributes operationAttributes;
	
	
	public OperationBoundary() {	
	}
	
	public OperationBoundary(operationId operationId, String type,
			item item, Date createTimestamp, invokedBy invokedBy,
			operationAttributes operationAttributes) {
		super();
		this.operationId = operationId;
		this.type = type;
		this.item = item;
		this.createTimestamp = createTimestamp;
		this.invokedBy = invokedBy;
		this.operationAttributes = operationAttributes;
	}



	public operationId getOperationId() {
		return operationId;
	}


	public void setOperationId(operationId operationId) {
		this.operationId = operationId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public item getItem() {
		return item;
	}


	public void setItem(item item) {
		this.item = item;
	}

	
	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public invokedBy getInvokedBy() {
		return invokedBy;
	}



	public void setInvokedBy(invokedBy invokedBy) {
		this.invokedBy = invokedBy;
	}



	public operationAttributes getOperationAttributes() {
		return operationAttributes;
	}



	public void setOperationAttributes(operationAttributes operationAttributes) {
		this.operationAttributes = operationAttributes;
	}
	
	
}
