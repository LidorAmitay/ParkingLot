package twins.logic;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import twins.data.UserDao;
import twins.data.UserEntity;
import twins.data.UserRole;
import twins.userAPI.UserBoundary;

@Service
public class UsersServiceJpa implements UsersServiceExtends {

	private String appName;
	private UserDao userDao;
	private EntityConverter entityConverter;

	// Constructor
	public UsersServiceJpa() {
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}

	@Autowired
	public void setEntityConverter(EntityConverter entityConverter) {
		this.entityConverter = entityConverter;
	}
	
	@PostConstruct
	public void init() {
	}

	@Override
	@Transactional
	public UserBoundary createUser(UserBoundary user) {
		try {
			UserRole temp = UserRole.valueOf(user.getRole());
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException("could not create user by role: " + user.getRole());
		}
		//[a-z0-9]+   @   [a-z0-9]+   .   [a-z0-9]+
		Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(user.getUserId().getEmail());
        if(!mat.matches()){
        	throw new RuntimeException("could not create user with this email");
        }
        
		if (user.getAvatar() == null || user.getAvatar() == "")
			throw new RuntimeException("could not create user with no Avatar");
		if (user.getUsername() == null)
			throw new RuntimeException("could not create user with no Username");
		user.getUserId().setSpace(appName);

		UserEntity entity = this.entityConverter.fromBoundary(user);
		entity = this.userDao.save(entity);
		return this.entityConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userSpace, String userEmail) {
		Optional<UserEntity> optionalUser = this.userDao.findById(userSpace + "@@" + userEmail);

		if (optionalUser.isPresent()) {
			UserEntity entity = optionalUser.get();
			UserBoundary boundary = entityConverter.toBoundary(entity);
			return boundary;
		} else {
			throw new UserNotFoundException("Could not find user by space and email: " + userSpace + " , " + userEmail);// NullPointerException
		}
	}

	@Override
	@Transactional
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) {
		
		Optional<UserEntity> optionalUser = this.userDao.findById(userSpace + "@@" + userEmail);

		if (optionalUser.isPresent()) {

			UserEntity entity = optionalUser.get();
			
			if (update.getAvatar() != null)
				entity.setAvatar(update.getAvatar());
			try {
				UserRole temp = UserRole.valueOf(entity.getRole().toString());
				if (update.getRole() != null)
					entity.setRole(UserRole.valueOf(update.getRole().toUpperCase()));

			} catch (IllegalArgumentException ex) {
			}
			if (update.getUsername() != null)
				entity.setUsername(update.getUsername());

			entity = this.userDao.save(entity);
			UserBoundary boundary = entityConverter.toBoundary(entity);
			return boundary;
		} else {
			// TODO have server return status 404 here
			throw new RuntimeException("could not find user");// NullPointerException
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail, int page, int size) {
		Optional<UserEntity> optionalUser = this.userDao.findById(adminSpace + "@@" + adminEmail);
		if(optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if(admin.getRole().equals(UserRole.ADMIN)) { // Permission check
				Iterable<UserEntity>  allEntities = this.userDao
						.findAll(PageRequest.of(page, size, Direction.DESC, "userId"));
					
				return StreamSupport
					.stream(allEntities.spliterator(), false) // get stream from iterable
					.map(this.entityConverter::toBoundary)
					.collect(Collectors.toList());
			}	
			else
				throw new RuntimeException("Only user with ADMIN role can get all users");
		}else
			throw new RuntimeException("Can't find user with space : " + adminSpace + 
					" and id : " + adminEmail);
		
	}

	@Override
	@Transactional
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		Optional<UserEntity> optionalUser = this.userDao.findById(adminSpace + "@@" + adminEmail);
		if(optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if(admin.getRole().equals(UserRole.ADMIN))
				this.userDao.deleteAll();
			else
				throw new RuntimeException("Only user with ADMIN role can delete all users");
		}else
			throw new RuntimeException("Can't find user with space : " + adminSpace + 
					" and id : " + adminEmail);
		
	}
	//not used, old version of method
	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		Optional<UserEntity> optionalUser = this.userDao.findById(adminSpace + "@@" + adminEmail);
		if(optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if(admin.getRole().equals(UserRole.ADMIN)) { // Permission check
				Iterable<UserEntity>  allEntities = this.userDao
						.findAll();
					
				return StreamSupport
					.stream(allEntities.spliterator(), false) // get stream from iterable
					.map(this.entityConverter::toBoundary)
					.collect(Collectors.toList());
			}	
			else
				throw new RuntimeException("Only user with ADMIN role can get all users");
		}else
			throw new RuntimeException("Can't find user with space : " + adminSpace + 
					" and id : " + adminEmail);
		
	}
}
