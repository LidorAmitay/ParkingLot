package twins.adminAPI;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twins.data.OperationsDao;
import twins.digitalItemsAPI.ItemId;
import twins.logic.ItemsService;
import twins.logic.OperationsService;
import twins.logic.OperationsServiceExtends;
import twins.logic.UpdatedItemsService;
import twins.logic.UsersService;
import twins.logic.UsersServiceExtends;
import twins.logic.UsersServiceMockup;
import twins.operationsAPI.InvokedBy;
import twins.operationsAPI.Item;
import twins.operationsAPI.OperationBoundary;
import twins.operationsAPI.OperationId;
import twins.userAPI.UserBoundary;
import twins.userAPI.UserId;


@RestController
public class AdminAPIController {
	private UsersServiceExtends userService;
	private UpdatedItemsService itemService;
	private OperationsServiceExtends operationsService;
	
	@Autowired
	public AdminAPIController() {
	}
	
	@Autowired	
	public void setUsersServiceExtends(UsersServiceExtends userService) {
		this.userService = userService;
	}
	@Autowired	
	public void setUpdatedItemsService(UpdatedItemsService itemService) {
		this.itemService = itemService;
	}
	@Autowired	
	public void setOperationsServiceExtends(OperationsServiceExtends operationsService) {
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
			public UserBoundary[] exportAllUsers (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email, @RequestParam(name = "size", required = false, defaultValue = "10") int size,
					@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
				return userService.getAllUsers(space, email, page, size).toArray(new UserBoundary[0]);
			}

		@RequestMapping(
				path = "/twins/admin/operations/{userSpace}/{userEmail}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
			public OperationBoundary[] exportAllOperations (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email, @RequestParam(name = "size", required = false, defaultValue = "10") int size,
					@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
				return operationsService.getAllOperations(space, email, page, size).toArray(new OperationBoundary[0]);
			}

}
