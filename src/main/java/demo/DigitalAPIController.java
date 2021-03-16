package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DigitalAPIController {
	
	@RequestMapping(
		path = "/twins/items/{userSpace}/{userEmail}", 
		method = RequestMethod.POST, 
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary createNewItem (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,@RequestBody ItemBoundary item) {
		//item.getItem().setSpace(space);
		System.err.println("(STUB) successfully written item data to database  " + "id : ");
		return item;
	}
}