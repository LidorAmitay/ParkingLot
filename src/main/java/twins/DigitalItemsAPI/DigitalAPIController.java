package twins.DigitalItemsAPI;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twins.UserId;

@RestController
public class DigitalAPIController {
	
	@RequestMapping(
		path = "/twins/items/{userSpace}/{userEmail}", 
		method = RequestMethod.POST, 
		produces = MediaType.APPLICATION_JSON_VALUE,//Output
		consumes = MediaType.APPLICATION_JSON_VALUE)//Input
	public ItemBoundary createNewItem (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,@RequestBody ItemBoundary item) {
		item.setItemId(new ItemId("2021b.item","90"));
		System.err.println("(STUB) successfully written item data to database  " + "id : " + item.getItemId().getId());
		return item;
	}
	
	@RequestMapping(
			path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", 
			method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public void updateItem (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,@PathVariable("itemSpace") String itemSpace,@PathVariable("itemId") long itemId,@RequestBody ItemBoundary item) {
			System.err.println("(STUB) successfully updated item data to database  " + "id : " + itemId);
		}
	@RequestMapping(
			path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
		public ItemBoundary retrieveItem (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,@PathVariable("itemSpace") String itemSpace,@PathVariable("itemId") long itemId) {
			System.err.println("(STUB) successfully retrieved item data");
			return new ItemBoundary(new ItemId("2021b.item","8"),"Demo TYPE","demo item",true,new Date(),new CreatedBy(new UserId("2021.twins","user@email.com")),new Location(32.115139,34.817804),new ItemAttributes());
		}
	@RequestMapping(
			path = "/twins/items/{userSpace}/{userEmail}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
		public ItemBoundary[] getAllItems (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
			System.err.println("(STUB) successfully got all the items from the database");
			return new ItemBoundary[] {new ItemBoundary(new ItemId("2021b.item","8"),"Demo TYPE","demo item",true,new Date(),new CreatedBy(new UserId("2021.twins","user@email.com")),new Location(32.115139,34.817804),new ItemAttributes()) , new ItemBoundary(new ItemId("2021b.item","8"),"Demo TYPE","demo item",true,new Date(),new CreatedBy(new UserId("2021.twins","user@email.com")),new Location(32.115139,34.817804),new ItemAttributes())};
			       
		}
}