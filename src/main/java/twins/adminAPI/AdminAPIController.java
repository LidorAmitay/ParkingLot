package twins.adminAPI;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twins.digitalItemsAPI.ItemId;
import twins.logic.ItemsService;
import twins.logic.OperationsService;
import twins.logic.UsersService;
import twins.logic.UsersServiceMockup;
import twins.operationsAPI.InvokedBy;
import twins.operationsAPI.Item;
import twins.operationsAPI.OperationBoundary;
import twins.operationsAPI.OperationId;
import twins.userAPI.UserBoundary;
import twins.userAPI.UserId;


@RestController
public class AdminAPIController {
	private UsersService userService;
	private ItemsService itemService;
	private OperationsService operationsService;
	
	@Autowired
	public AdminAPIController(UsersService userService, ItemsService itemService, OperationsService operationsService) {
		super();
		this.userService = userService;
		this.itemService = itemService;
		this.operationsService = operationsService;
	}
	
		@RequestMapping(
				path = "/twins/admin/users/{userSpace}/{userEmail}", 
				method = RequestMethod.DELETE)
			public void deleteUsersInSpace (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				userService.deleteAllUsers(space, email);
			}
		@RequestMapping(
				path = "/twins/admin/items/{userSpace}/{userEmail}", 
				method = RequestMethod.DELETE)
			public void deleteItemsInSpace (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				itemService.deleteAllItems(space, email);
			}
		@RequestMapping(
				path = "/twins/admin/operations/{userSpace}/{userEmail}", 
				method = RequestMethod.DELETE)
			public void deleteOperationsInSpace (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				operationsService.deleteAllOperations(space, email);
			}
		@RequestMapping(
				path = "/twins/admin/users/{userSpace}/{userEmail}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
			public UserBoundary[] exportAllUsers (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				return userService.getAllUsers(space, email).toArray(new UserBoundary[0]);
			}
		@RequestMapping(
				path = "/twins/admin/operations/{userSpace}/{userEmail}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
			public OperationBoundary[] exportAllOperations (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				return operationsService.getAllOperations(space, email).toArray(new OperationBoundary[0]);
			}
}
