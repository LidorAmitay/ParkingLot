package twins.operationsAPI;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OperationsAPIController {
	
	//Input - Operation Boundary with null operationId
	//Output - Any JSON
	@RequestMapping(
			path = "/twins/operations", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE,//Output
			consumes = MediaType.APPLICATION_JSON_VALUE)//Input
		public Object InvokeOperatiomOnItem (@RequestBody OperationBoundary operation) {
			operation.setOperationId(null);
			System.err.println("(STUB) successfully invoked operation on item");
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
			operation.setOperationId(new OperationId("2021b.operation", "1234"));
			System.err.println("(STUB) successfully A-Synchronous operation " + operation.getOperationId());
			return operation;
		}
}
