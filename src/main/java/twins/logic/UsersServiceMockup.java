package twins.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import twins.data.UserEntity;
import twins.data.UserRole;
import twins.userAPI.UserBoundary;



//@Service
public class UsersServiceMockup implements UsersService{
	
	private String appName;
	private Map<String, UserEntity> users;
	private EntityConverter entityConverter;
	
	//Constructor
	public UsersServiceMockup(EntityConverter entityConverter) {
		super();
		this.users = Collections.synchronizedMap(new HashMap<>());
	}
	
	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}
	
	@Autowired
	public void setEntityConverter(EntityConverter entityConverter) {
		this.entityConverter = entityConverter;
	}
	
	@Override
	public UserBoundary createUser(UserBoundary user) {
		try {
		       UserRole temp = UserRole.valueOf(user.getRole());
		    } catch (IllegalArgumentException ex) {  
		    	throw new RuntimeException("could not create user by role: " +user.getRole());
		  }
		if(user.getUserId().getEmail() == null || user.getUserId().getEmail() == "")
			throw new RuntimeException("could not create user with no email");
		
		user.getUserId().setSpace(appName);	
		UserEntity entity = this.entityConverter.fromBoundary(user);
		this.users.put(entity.getUserId(), entity);
		return this.entityConverter.toBoundary(entity);
	}

	@Override
	public UserBoundary login(String userSpace, String userEmail) {
		UserEntity entity = this.users.get(userSpace+"@@"+userEmail);
		if (entity != null) {
			UserBoundary boundary = entityConverter.toBoundary(entity);
			return boundary;
		}else {
			// TODO have server return status 404 here
			throw new RuntimeException("could not find user by email: " + userEmail);// NullPointerException
		}
	}

	@Override
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) {
		UserEntity entity = this.users.get(userSpace+"@@"+userEmail);
		
		if(entity != null) {
			if (update.getAvatar()!=null)
				entity.setAvatar(update.getAvatar());
			try {
			       UserRole temp = UserRole.valueOf(entity.getRole().toString());
			       if (update.getRole()!=null)
			    	   entity.setRole(UserRole.valueOf(update.getRole().toUpperCase()));
			       
			    } catch (IllegalArgumentException ex) {}
			if (update.getUsername()!=null)
				entity.setUsername(update.getUsername());
			return this.entityConverter.toBoundary(entity); //previously returned update
		} else {
			// TODO have server return status 404 here
			throw new RuntimeException("could not find user");// NullPointerException
		}		
	}


	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		return this.users
				.values()
				.stream()
				.map(this.entityConverter::toBoundary)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		this.users
		.clear();	
	}
}
