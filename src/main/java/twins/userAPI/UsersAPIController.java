package twins.userAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twins.data.UserRole;
import twins.logic.UsersService;

@RestController
public class UsersAPIController {
		private UsersService userService;
		
		@Autowired	
		public void setUserService(UsersService userService) {
			this.userService = userService;
		}
		
		
		
		@RequestMapping(
			path = "/twins/users", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
		
		public UserBoundary insertUserDataToDatabase (@RequestBody NewUserDetails user) {
			
			/* 
			 * 
			 * ADD CRRECTION CHECKS FOR INPUT
			 * 
			 * 
			 * 
			 * */
			
			UserBoundary ub = new UserBoundary(new UserId("2021b.twins",user.getEmail()),user.getRole(),user.getUsername(),user.getAvatar());
			return userService.createUser(ub);
			
		}
		
		@RequestMapping(
				path = "/twins/users/login/{userSpace}/{userEmail}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
			
			public UserBoundary loginUserAndRetrieve (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email) {
				return this.userService.login(space, email);
			}
		
		@RequestMapping(
				path = "/twins/users/{userSpace}/{userEmail}", 
				method = RequestMethod.PUT,
				consumes = MediaType.APPLICATION_JSON_VALUE)
			
			public void updateUser (@PathVariable("userSpace") String space,@PathVariable("userEmail") String email,@RequestBody UserBoundary user) {
				this.userService.updateUser(space, email, user);
			}
		
		
		

}
