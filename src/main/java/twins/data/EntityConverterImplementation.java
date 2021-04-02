package twins.data;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import twins.digitalItemsAPI.CreatedBy;
import twins.digitalItemsAPI.ItemBoundary;
import twins.digitalItemsAPI.ItemId;
import twins.digitalItemsAPI.Location;
import twins.operationsAPI.Item;
import twins.operationsAPI.OperationBoundary;
import twins.operationsAPI.OperationId;
import twins.userAPI.UserBoundary;
import twins.userAPI.UserId;

@Component //*** component or service?
public class EntityConverterImplementation implements EntityConverter{

	@Override
	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary ub = new UserBoundary();
		ub.setAvatar(entity.getAvatar());
		ub.setRole(entity.getRole());
		ub.setUserId(new UserId(entity.getSpace(), entity.getEmail()));
		ub.setUsername(entity.getUsername());
		
		return ub;
	}

	@Override
	public UserEntity fromBoundary(UserBoundary boundary) {
		UserEntity ue = new UserEntity();
		ue.setAvatar(boundary.getAvatar());
		ue.setEmail(boundary.getUserId().getEmail());
		ue.setRole(boundary.getRole());
		ue.setSpace(boundary.getUserId().getSpace());
		ue.setUsername(boundary.getUsername());
		return ue;
	}


	@Override
	public ItemBoundary toBoundary(ItemEntity entity) {
		ItemBoundary ib = new ItemBoundary();
		ib.setActive(entity.getActive());
		ib.setCreatedBy(new CreatedBy(new UserId(entity.getUserSpace(), entity.getEmail())) );
		ib.setCreatedTimestamp(new Date());
		//ib.setItemAttributes(entity.getActive());
		ib.setItemId(new ItemId(entity.getItemSpace(), entity.getId()));
		ib.setLocation(new Location(entity.getLat(), entity.getLng()));
		ib.setName(entity.getName());
		ib.setType(entity.getType());
		
		return ib;
	}

	@Override
	public ItemEntity fromBoundary(ItemBoundary boundary) {
		ItemEntity ie = new ItemEntity();
		ie.setActive(boundary.getActive());
		ie.setCreatedTimestamp(boundary.getCreatedTimestamp());
		ie.setEmail(boundary.getCreatedBy().getUserId().getEmail());
		ie.setId(boundary.getItemId().getId());
		ie.setLat(boundary.getLocation().getLat());
		ie.setLng(boundary.getLocation().getLng());
		ie.setName(boundary.getName());
		ie.setItemSpace(boundary.getItemId().getSpace());
		ie.setUserSpace(boundary.getCreatedBy().getUserId().getSpace());
		ie.setType(boundary.getType());
		return ie;
	}

	@Override
	public OperationBoundary toBoundary(OperationEntity entity) {
		OperationBoundary rv = new OperationBoundary();
		rv.setCreateTimestamp(entity.getCreatedTimestamp());
		rv.setInvokedBy(entity.getInvokedBy());
		rv.setItem(new Item(new ItemId(entity.getItemSpace(),entity.getItemId())));
		rv.setOperationAttributes(entity.getOperationAttributes());
		rv.setOperationId(new OperationId(entity.getOperationSpace(),entity.getOperationId()));
		rv.setType(entity.getType());
		return rv;
	}

	@Override
	public OperationEntity fromBoundary(OperationBoundary boundary) {
		OperationEntity rv = new OperationEntity();
		rv.setCreatedTimestamp(boundary.getCreateTimestamp());
		rv.setInvokedBy(boundary.getInvokedBy());
		rv.setItemId(boundary.getItem().getItemid().getId());
		rv.setItemSpace(boundary.getItem().getItemid().getSpace());
		rv.setOperationAttributes(boundary.getOperationAttributes());
		rv.setOperationId(boundary.getOperationId().getId());
		rv.setOperationSpace(boundary.getOperationId().getSpace());
		rv.setType(boundary.getType());
		
		
		return rv;
	}
	
	
}
