package twins.operationsAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twins.logic.OperationsService;


@RestController
public class OperationsAPIController {
	
	private OperationsService operationsService; 
	 
	@Autowired
	public void setOperationsService(OperationsService operationsService) {
		this.operationsService = operationsService;
	}
	
	//Input - Operation Boundary with null operationId
	//Output - Any JSON
	@RequestMapping(  
			path = "/twins/operations", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE,//Output
			consumes = MediaType.APPLICATION_JSON_VALUE)//Input
		public Object InvokeOperationOnItem (@RequestBody OperationBoundary operation) {
			this.operationsService.invokeOperation(operation); 
			return operation; 
		}
	
	//Input - Operation Boundary with null operationId
	//Output - Operation Boundary with operationId
	@RequestMapping(
			path = "/twins/operations/async", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE,//Output
			consumes = MediaType.APPLICATION_JSON_VALUE)//Input
		public Object ASynchronousOperation (@RequestBody OperationBoundary operation) {
			this.operationsService.invokeAsynchronousOperation(operation);
			return operation;
		}
}
