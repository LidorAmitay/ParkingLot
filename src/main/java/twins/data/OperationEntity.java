package twins.data;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Operations")
public class OperationEntity {
	private String OperationSpaceId;
	private String type;
	private String ItemSpaceId;
	private Date createdTimestamp;
	private String invokedBySpaceEmail;
	private String operationAttributes;
	
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
	@Temporal(TemporalType.TIMESTAMP)
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
	
	
	public String getOperationAttributes() {
		return operationAttributes;
	}
	
	
	public void setOperationAttributes(String operationAttributesMap) {
		this.operationAttributes = operationAttributesMap;
	}




}
