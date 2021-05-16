package twins.digitalItemsAPI;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twins.logic.UpdatedItemsService;


@RestController
public class ItemsRelatedApi {
	private UpdatedItemsService itemsService;

	@Autowired
	public ItemsRelatedApi(UpdatedItemsService itemsService) {
		super();
		this.itemsService = itemsService;
	}
	
	
	 
	@RequestMapping(method = RequestMethod.PUT,
			path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addResponseToMessage (
			@PathVariable("itemSpace") String itemSpace, 
			@PathVariable("itemId") String itemId, 
			@RequestBody ItemId itemIdBoundary) {
		this.itemsService.bindItemToItem(itemSpace + "@@" + itemId, itemIdBoundary.getSpace() + "@@" + itemIdBoundary.getId());
	}
	

	@RequestMapping(path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getResponses (
			@PathVariable("itemSpace") String itemSpace, 
			@PathVariable("itemId") String itemId ){
		return this.itemsService
			.getAllChildren(itemSpace + "@@" + itemId)
			.toArray(new ItemBoundary[0]);
	}

	@RequestMapping(path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/parents",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getOriginals (
			@PathVariable("itemSpace") String itemSpace, 
			@PathVariable("itemId") String itemId ){
		Optional<ItemBoundary> getParent = this.itemsService.getAllParents(
				itemSpace + "@@" + itemId);
		if (getParent.isPresent())
			return new ItemBoundary[] {getParent.get()};
		else
			return new ItemBoundary[0];
		
		}
	}
	

