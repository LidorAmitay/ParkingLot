package twins.logic;

import java.util.List;

import twins.operationsAPI.OperationBoundary;

public interface OperationsServiceExtends extends OperationsService {

	public Object invokeOperation(OperationBoundary operation,int page , int size);
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail, int page, int size);

}
