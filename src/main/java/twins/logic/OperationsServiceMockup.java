package twins.logic;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twins.data.EntityConverter;
import twins.data.OperationEntity;
import twins.operationsAPI.OperationBoundary;
@Service
public class OperationsServiceMockup implements OperationsService{
	
	private Map<String,OperationEntity> operationsMap;
	private EntityConverter entityConvert;

	@Autowired
	public void setEntityConvert(EntityConverter entityConvert) {
		this.entityConvert = entityConvert;
	}
	
	
	
	@Override
	public Object invokeOperations(OperationBoundary operation) {
		String newId = UUID.randomUUID().toString();
		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		entity.setOperationSpaceId(newId);
		this.operationsMap.put(newId, entity);
		return operation;
	}

	@Override
	public OperationBoundary invokeAsynchronousOperation(OperationBoundary operation) {
		String newId = UUID.randomUUID().toString();
		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		entity.setOperationSpaceId(newId);
		this.operationsMap.put(newId, entity);
		return this.entityConvert.toBoundary(entity);
	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		// TODO Auto-generated method stub
		return this.operationsMap.values().stream().map(this.entityConvert::toBoundary).collect(Collectors.toList());
	}

	@Override
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		operationsMap.clear();
	}

}
