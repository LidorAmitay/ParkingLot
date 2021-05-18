package twins.operationsAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twins.logic.OperationsServiceExtends;


@RestController
public class OperationsAPIController {
	
	private OperationsServiceExtends operationsService; 
	 
	@Autowired
	public void setOperationsService(OperationsServiceExtends operationsService) {
		this.operationsService = operationsService;
	}
	
	//Input - Operation Boundary with null operationId
	//Output - Any JSON
	@RequestMapping(  
			path = "/twins/operations", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE,//Output
			consumes = MediaType.APPLICATION_JSON_VALUE)//Input
		public Object InvokeOperationOnItem (@RequestParam(name = "size", required = false, defaultValue = "10") int size,
				@RequestParam(name = "page", required = false, defaultValue = "0") int page, @RequestBody OperationBoundary operation) {
			return this.operationsService.invokeOperation(operation, page, size); 
		}
	
	//Input - Operation Boundary with null operationId
	//Output - Operation Boundary with operationId
	@RequestMapping(
			path = "/twins/operations/async", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE,//Output
			consumes = MediaType.APPLICATION_JSON_VALUE)//Input
		public Object ASynchronousOperation (@RequestBody OperationBoundary operation) {
			return this.operationsService.invokeAsynchronousOperation(operation);
		}

}
