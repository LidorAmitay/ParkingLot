package twins.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twins.data.EntityConverter;
import twins.data.UserEntity;
import twins.userAPI.UserBoundary;



@Service
public class UsersServiceMockup implements UsersService{
	
	private Map<String, UserEntity> users;
	private EntityConverter entityConverter;
	
	//Constructor
	public UsersServiceMockup(EntityConverter entityConverter) {
		super();
		this.users = Collections.synchronizedMap(new HashMap<>());
	}
	
	@Autowired
	public void setEntityConverter(EntityConverter entityConverter) {
		this.entityConverter = entityConverter;
	}
	
	@Override
	public UserBoundary createUser(UserBoundary user) {
		UserEntity entity = this.entityConverter.fromBoundary(user);
		this.users.put(entity.getEmail(), entity);
		return this.entityConverter.toBoundary(entity);
	}

	@Override
	public UserBoundary login(String userSpace, String userEmail) {
		UserEntity entity = this.users.get(userEmail);
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
		UserEntity entity = this.users.get(userEmail);
		
		if(entity != null) {
			entity.setAvatar(update.getAvatar());
			entity.setRole(update.getRole());
			entity.setUsername(update.getUsername());
			return update;
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
