package twins.logic;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import twins.data.OperationEntity;
import twins.operationsAPI.InvokedBy;
import twins.operationsAPI.Item;
import twins.digitalItemsAPI.ItemId;

import twins.operationsAPI.OperationBoundary;
import twins.operationsAPI.OperationId;
import twins.userAPI.UserId;
@Service
public class OperationsServiceMockup implements OperationsService{
	
	private String space;
	private Map<String,OperationEntity> operationsMap;
	private EntityConverter entityConvert;
	
	public OperationsServiceMockup() {
		super();
		this.operationsMap = Collections.synchronizedMap(new HashMap<>());
;
	}
	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String space) {
		this.space = space;
	}
 
	@Autowired
	public void setEntityConvert(EntityConverter entityConvert) {
		this.entityConvert = entityConvert;
	}
	
	
	
	@Override
	public Object invokeOperation(OperationBoundary operation) {
		String newId = UUID.randomUUID().toString();
		operation.setOperationId(new OperationId(space,newId));
		operation.setCreatedTimestamp(new Date());
		
		if(operation.getItem() == null)
			operation.setItem(new Item(new ItemId()));
		else if(operation.getItem().getItemId() == null)
			operation.getItem().setItemId(new ItemId());
		
		if(operation.getInvokedBy() == null)
			operation.setInvokedBy(new InvokedBy(new UserId()));
		else if(operation.getInvokedBy().getUserId() == null)
			operation.getInvokedBy().setUserId(new UserId());
		
		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		this.operationsMap.put(entity.getOperationSpaceId(), entity);
		return this.entityConvert.toBoundary(entity);
	}

	@Override
	public OperationBoundary invokeAsynchronousOperation(OperationBoundary operation) {
		String newId = UUID.randomUUID().toString();
		operation.setOperationId(new OperationId(space,newId));
		operation.setCreatedTimestamp(new Date());
		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		this.operationsMap.put(entity.getOperationSpaceId(), entity);
		return this.entityConvert.toBoundary(entity);
		
	}
 
	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		
		return this.operationsMap.values().stream().map(this.entityConvert::toBoundary).collect(Collectors.toList());
	}

	@Override
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		operationsMap.clear();
	}

}
