package twins.AdminAPI;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twins.UserBoundary;
import twins.UserId;
import twins.DigitalItemsAPI.ItemId;
import twins.OperationsAPI.InvokedBy;
import twins.OperationsAPI.Item;
import twins.OperationsAPI.OperationAttributes;
import twins.OperationsAPI.OperationBoundary;
import twins.OperationsAPI.OperationId;


@RestController
public class AdminAPIController {
	
		@RequestMapping(
				path = "/twins/admin/users/{userSpace}/{userEmail}", 
				method = RequestMethod.DELETE)
			public void deleteUsersInSpace (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				System.err.println("(STUB) successfully deleted all users in space");
			}
		@RequestMapping(
				path = "/twins/admin/items/{userSpace}/{userEmail}", 
				method = RequestMethod.DELETE)
			public void deleteItemsInSpace (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				System.err.println("(STUB) successfully deleted all items in space");
			}
		@RequestMapping(
				path = "/twins/admin/operations/{userSpace}/{userEmail}", 
				method = RequestMethod.DELETE)
			public void deleteOperationsInSpace (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				System.err.println("(STUB) successfully deleted all operations in space");
			}
		@RequestMapping(
				path = "/twins/admin/users/{userSpace}/{userEmail}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
			public UserBoundary[] exportAllUsers (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				System.err.println("(STUB) successfully exported all users in space");
				return new UserBoundary[] {new UserBoundary(new UserId("2021b.users","user@email.com"),"MANAGER","Demo User","J"), new UserBoundary(new UserId("2021b.users","user2@email.com"),"EMPLOYEE","Demo User2","O")};
			}
		@RequestMapping(
				path = "/twins/admin/operations/{userSpace}/{userEmail}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
			public OperationBoundary[] exportAllOperations (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				System.err.println("(STUB) successfully exported all operations in space");
				return new OperationBoundary[] { new OperationBoundary(new OperationId("2021.twins","451"), "operation type",
						new Item(new ItemId("2021b.twins","99")), new Date(), new InvokedBy(new UserId("2021.twins","user@email.com")),
						new OperationAttributes()) , new OperationBoundary(new OperationId("2021.twins","452"), "operation type",
								new Item(new ItemId("2021b.twins","100")), new Date(), new InvokedBy(new UserId("2021.twins","user2@email.com")),
								new OperationAttributes())};
			}
}
