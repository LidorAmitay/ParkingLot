package twins.data;

import twins.userAPI.UserBoundary;
import twins.userAPI.UserId;

public class EntityConverterImplementation implements EntityConverter{

	@Override
	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary ue = new UserBoundary();
		ue.setAvatar(entity.getAvatar());
		ue.setRole(entity.getRole());
		ue.setUserId(new UserId(entity.getUsername(), entity.getEmail()));
		ue.setUsername(entity.getUsername());
		
		return ue;
	}

	@Override
	public UserEntity fromBoundary(UserBoundary boundary) {
		UserEntity ub = new UserEntity();
		ub.setAvatar(boundary.getAvatar());
		ub.setEmail(boundary.getUserId().getEmail());
		ub.setRole(boundary.getRole());
		ub.setSpace(boundary.getUserId().getSpace());
		ub.setUsername(boundary.getUsername());
		return ub;
	}


	
	
	

}
