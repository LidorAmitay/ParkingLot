package twins.digitalItemsAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twins.logic.ItemsService;
import twins.logic.UpdatedItemsService;


@RestController
public class DigitalAPIController {
	private UpdatedItemsService itemService;
	
	@Autowired	
	public void setUpdatedItemService(UpdatedItemsService itemService) {
		this.itemService = itemService;
	}
	
	
	
	@RequestMapping(
		path = "/twins/items/{userSpace}/{userEmail}", 
		method = RequestMethod.POST, 
		produces = MediaType.APPLICATION_JSON_VALUE,//Output
		consumes = MediaType.APPLICATION_JSON_VALUE)//Input
	public ItemBoundary createNewItem (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,@RequestBody ItemBoundary item) {
		return this.itemService.createItem(space, email, item);
	}
	
	@RequestMapping(
			path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", 
			method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public void updateItem (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,@PathVariable("itemSpace") String itemSpace,@PathVariable("itemId") String itemId,@RequestBody ItemBoundary item) {
			this.itemService.updateItem(space, email, itemSpace, itemId, item);
		}
	
	@RequestMapping(
			path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
		public ItemBoundary retrieveItem (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,@PathVariable("itemSpace") String itemSpace,@PathVariable("itemId") String itemId) {
			ItemBoundary ib = this.itemService.getSpecificItem(space, email, itemSpace, itemId);
			return ib;
		}
	
	@RequestMapping(
			path = "/twins/items/{userSpace}/{userEmail}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
		public ItemBoundary[] getAllItems (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,
				@RequestParam(name = "size", required = false, defaultValue = "10") int size,
				@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
			return this.itemService.getAllItems(space, email, page, size).toArray(new ItemBoundary[0]);
		}

}