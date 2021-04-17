package twins.data;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import twins.operationsAPI.InvokedBy;
import twins.operationsAPI.Item;
import twins.operationsAPI.OperationId;
@Entity
@Table(name = "Operations")
public class OperationEntity {
	private String OperationSpaceId;
	private String type;
	private String ItemSpaceId;
	private Date createdTimestamp;
	private String invokedBySpaceEmail;
	private Map<String, Object> operationAttributesMap;
	
	public OperationEntity() {
	}
	
	@Id
	public String getOperationSpaceId() {
		return OperationSpaceId;
	}

	public void setOperationSpaceId(String operationSpaceId) {
		OperationSpaceId = operationSpaceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItemSpaceId() {
		return ItemSpaceId;
	}

	public void setItemSpaceId(String itemSpaceId) {
		ItemSpaceId = itemSpaceId;
	}
	@Transient
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getInvokedBySpaceEmail() {
		return invokedBySpaceEmail;
	}

	public void setInvokedBySpaceEmail(String invokedBySpaceEmail) {
		this.invokedBySpaceEmail = invokedBySpaceEmail;
	}
	@Transient
	public Map<String, Object> getOperationAttributesMap() {
		return operationAttributesMap;
	}

	public void setOperationAttributesMap(Map<String, Object> operationAttributesMap) {
		this.operationAttributesMap = operationAttributesMap;
	}




}
