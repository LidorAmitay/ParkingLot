package twins.data;

import java.util.Date;

import twins.operationsAPI.InvokedBy;
import twins.operationsAPI.Item;
import twins.operationsAPI.OperationAttributes;
import twins.operationsAPI.OperationId;

public class OperationEntity {
	private String OperationSpace;
	private String OperationId;
	private String type;
	private String ItemSpace;
	private String ItemId;
	private Date createdTimestamp;
	private InvokedBy invokedBy;
	private OperationAttributes operationAttributes;
	
	public OperationEntity() {
		super();
	}
	
	public String getOperationSpace() {
		return OperationSpace;
	}
	public void setOperationSpace(String operationSpace) {
		OperationSpace = operationSpace;
	}
	public String getOperationId() {
		return OperationId;
	}
	public void setOperationId(String operationId) {
		OperationId = operationId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getItemSpace() {
		return ItemSpace;
	}
	public void setItemSpace(String itemSpace) {
		ItemSpace = itemSpace;
	}
	public String getItemId() {
		return ItemId;
	}
	public void setItemId(String itemId) {
		ItemId = itemId;
	}
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
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
