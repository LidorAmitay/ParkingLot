package twins.logic;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import twins.data.EntityConverter;
import twins.data.OperationEntity;
import twins.operationsAPI.OperationBoundary;
import twins.operationsAPI.OperationId;
@Service
public class OperationsServiceMockup implements OperationsService{
	 
	@Value("${spring.application.name}")
	private String space;
	private Map<String,OperationEntity> operationsMap;
	private EntityConverter entityConvert;
 
	@Autowired
	public void setEntityConvert(EntityConverter entityConvert) {
		this.entityConvert = entityConvert;
	}
	
	
	
	@Override
	public Object invokeOperations(OperationBoundary operation) {
		String newId = UUID.randomUUID().toString();
		operation.setOperationId(new OperationId(space,newId));
		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		this.operationsMap.put(entity.getOperationSpaceId(), entity);
		operation = this.entityConvert.toBoundary(entity);
		return operation;
	}

	@Override
	public OperationBoundary invokeAsynchronousOperation(OperationBoundary operation) {
		String newId = UUID.randomUUID().toString();
		operation.setOperationId(new OperationId(space,newId));
		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		this.operationsMap.put(entity.getOperationSpaceId(), entity);
		operation = this.entityConvert.toBoundary(entity);
		return operation;
		
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
