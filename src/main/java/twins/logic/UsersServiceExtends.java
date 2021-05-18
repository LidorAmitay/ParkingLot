package twins.logic;

import java.util.List;

import twins.userAPI.UserBoundary;

public interface UsersServiceExtends extends UsersService {
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail, int page, int size);
}
