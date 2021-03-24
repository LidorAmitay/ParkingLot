package twins;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersAPIController {
	
		@RequestMapping(
			path = "/twins/users", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
		
		public UserBoundary insertUserDataToDatabase (@RequestBody NewUserDetails user) {
			System.err.println("(STUB) successfully written user data to database");
			return new UserBoundary(new UserId("2021b.twins",user.getEmail()),user.getRole(),user.getUsername(),user.getAvatar());
		}
		
		@RequestMapping(
				path = "/twins/users/login/{userSpace}/{userEmail}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
			
			public UserBoundary loginUserAndRetrieve (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				System.err.println("(STUB) successfully login user ");
				return new UserBoundary(new UserId(space,email),"Team Leader","Tal Goldengoren","T");
			}
		
		@RequestMapping(
				path = "/twins/users/{userSpace}/{userEmail}", 
				method = RequestMethod.PUT,
				consumes = MediaType.APPLICATION_JSON_VALUE)
			
			public void loginUserAndRetrieve (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,@RequestBody UserBoundary user) {
				System.err.println("(STUB) successfully updated user in the database");
			}
		
		
		

}
