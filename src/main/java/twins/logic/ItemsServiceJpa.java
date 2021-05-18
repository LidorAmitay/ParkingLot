package twins.logic;


import java.util.Date;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import twins.data.DigitalItemDao;
import twins.data.ItemEntity;
import twins.data.UserDao;
import twins.data.UserEntity;
import twins.data.UserRole;
import twins.digitalItemsAPI.CreatedBy;
import twins.digitalItemsAPI.ItemBoundary;
import twins.digitalItemsAPI.ItemId;
import twins.digitalItemsAPI.Location;
import twins.userAPI.UserId;

@Service
public class ItemsServiceJpa implements UpdatedItemsService {
	
	private String appName;
	private DigitalItemDao digitalItemDao;
	private EntityConverter entityConverter;
	private UserDao userDao;

	
	
	//Constructor
	public ItemsServiceJpa() {
	}
	
	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String appName) {
		this.appName = appName;
	}
	
	@Autowired
	public void setDigitalItemDao(DigitalItemDao digitalItemDao) {
		this.digitalItemDao = digitalItemDao;
	}

	@Autowired
	public void setEntityConverter(EntityConverter entityConverter) {
		this.entityConverter = entityConverter;
	}
	
	@Autowired
	public void setUserdao(UserDao userdao) {
		this.userDao = userdao;
	}


	@Override
	@Transactional
	public ItemBoundary createItem(String userSpace, String userEmail, ItemBoundary item) { 
		
		Optional<UserEntity> optionalUser = this.userDao.findById(userSpace + "@@" + userEmail);
		if(optionalUser.isPresent())
			if(!(optionalUser.get().getRole().equals(UserRole.MANAGER))) 
				throw new RuntimeException("Only a manager can create item ");// NullPointerException
		
		if(item.getLocation() == null)
			throw new RuntimeException("could not create an item with null location ");// NullPointerException
		
		else if(item.getLocation().getLat() == null || item.getLocation().getLng() == null)
			throw new RuntimeException("could not create an item with null location ");// NullPointerException
		
		if (item.getActive()==null)
			throw new RuntimeException("could not create an item with null active field ");// NullPointerException
		if (item.getName()==null)
			throw new RuntimeException("could not create an item with null item name ");// NullPointerException
		if (item.getType()==null)
			throw new RuntimeException("could not create an item with null item type ");// NullPointerException
		
		item.setItemId(new ItemId(appName,UUID.randomUUID().toString()));
		item.setCreatedBy(new CreatedBy(new UserId(userSpace,userEmail)));
		item.setCreatedTimestamp(new Date());
		// TODO add total profit attribute 
		//item.getItemAttributes().put("Profit", 0);
		ItemEntity ie = this.entityConverter.fromBoundary(item);
		ie = this.digitalItemDao.save(ie);
		
		if(item.getType().equals("parkingLot")) {
				
			if(item.getItemAttributes().containsKey("priceOfParking")== false ) 
				throw new RuntimeException("could not create an parkinglot with null priceOfParking ");
			
			if(item.getItemAttributes().containsKey("numOfParking")== false ) 
				throw new RuntimeException("could not create an parkinglot with null numOfParking ");
			
			createParkingLot( userSpace,  userEmail,(int)item.getItemAttributes().get("numOfParking"),item );
				
		}
		
		
		
		return this.entityConverter.toBoundary(ie);
	}
	
	
	private void createParkingLot(String userSpace, String userEmail, int numOfParking,ItemBoundary parkinglot) {
		
			
		for (int i = 0; i < numOfParking; i++) {
			
			ItemBoundary parkingspot = new ItemBoundary();
			parkingspot.setActive(true); 
			parkingspot.setCreatedBy(new CreatedBy(new UserId(userSpace, userEmail)));
			parkingspot.setType("parkingspot");
			parkingspot.setName("Parking number : " +i );
			parkingspot.setLocation(new Location(parkinglot.getLocation().getLat(), parkinglot.getLocation().getLng()));
			parkingspot = createItem(userSpace, userEmail, parkingspot);
			bindItemToItem(parkinglot.getItemId().getSpace()+"@@"+parkinglot.getItemId().getId(),
					parkingspot.getItemId().getSpace() + "@@" + parkingspot.getItemId().getId());
			
		}
		
		
	}

	@Override
	@Transactional
	public ItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
			ItemBoundary update) {
		// Permission check
		Optional<UserEntity> optionalUser = this.userDao.findById(userSpace + "@@" + userEmail);
		if(optionalUser.isPresent())
			if(!(optionalUser.get().getRole().equals(UserRole.MANAGER))) 
				throw new RuntimeException("Only a manager can update item ");// NullPointerException
		
		Optional<ItemEntity> optionalEntity = this.digitalItemDao.findById(itemSpace+"@@"+itemId);
		
		if(optionalEntity.isPresent()) {
			ItemEntity entity = optionalEntity.get();
			if (update.getActive()!=null )
				entity.setActive(update.getActive());
			if (update.getLocation()!=null) {
				if (update.getLocation().getLat()!=null)
					entity.setLat(update.getLocation().getLat());
				if (update.getLocation().getLng()!=null)
					entity.setLng(update.getLocation().getLng());
			}
			if (update.getName()!=null)
				entity.setName(update.getName());
			if (update.getType()!=null)
				entity.setType(update.getType());
			if (update.getItemAttributes()!= null)
				entity.setItemAttributes(this.entityConverter.fromMapToJson(update.getItemAttributes())); //Marshaling map to json
			entity = this.digitalItemDao.save(entity);
			return this.entityConverter.toBoundary(entity);
		} else {
			throw new ItemNotFoundException("could not find item " + itemId);// NullPointerException
		}	
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		
		Optional<UserEntity> optionalUser = this.userDao.findById(userSpace + "@@" + userEmail);
		UserEntity user;
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
			if(user.getRole().equals(UserRole.ADMIN))
				throw new RuntimeException("Admin permission cannot get items");
		}else
			throw new RuntimeException("Can't find user with space : " + userSpace + "and id : "+ userEmail);
		
		Optional<ItemEntity> optionalEntity = this.digitalItemDao.findById(itemSpace+"@@"+itemId);
		if(optionalEntity.isPresent()) {
			ItemEntity ie = optionalEntity.get();
			if(ie.getActive())
				return this.entityConverter.toBoundary(ie);
			
			else if(user.getRole().equals(UserRole.MANAGER))
				return this.entityConverter.toBoundary(ie);
			else
				throw new ItemNotFoundException("could not find item " + itemId);
			
		}else {
			throw new ItemNotFoundException("could not find item " + itemId);// NullPointerException
		}
		
	}
	
	
	@Override //Old
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllItems(String userSpace, String userEmail) {
		// TODO add permission check manager or player
		Iterable<ItemEntity>  allEntities = this.digitalItemDao
				.findAll();
			
		return StreamSupport
			.stream(allEntities.spliterator(), false) // get stream from iterable
			.map(this.entityConverter::toBoundary)
			.collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllItems(String userSpace, String userEmail, int page, int size) {
		// TODO add permission check manager or player
		Iterable<ItemEntity>  allEntities = this.digitalItemDao
				.findAllByActive(true, PageRequest.of(page, size, Direction.DESC, "name"));
			
		return StreamSupport
			.stream(allEntities.spliterator(), false) // get stream from iterable
			.map(this.entityConverter::toBoundary)
			.collect(Collectors.toList());
	}
	
	
	@Override
	@Transactional
	public void deleteAllItems(String adminSpace, String adminEmail) {
		Optional<UserEntity> optionalUser = this.userDao.findById(adminSpace + "@@" + adminEmail);
		if(optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if(admin.getRole().equals(UserRole.ADMIN))
				this.digitalItemDao.deleteAll();
			else
				throw new RuntimeException("Only user with ADMIN role can delete all items");
		}else
			throw new RuntimeException("Can't find user with space : " + adminSpace + 
					" and id : " + adminEmail);		
		
	}

	@Override
	@Transactional
	public void bindItemToItem(String parentId, String childId) {
		
		ItemEntity parentItem = this.digitalItemDao
				.findById(parentId)
				.orElseThrow(()->new ItemNotFoundException("could not find parent item by id: " + parentId));

		ItemEntity childItem = this.digitalItemDao
				.findById(childId)
				.orElseThrow(()->new ItemNotFoundException("could not find child item by id: " + childId));

		parentItem.addItem(childItem);
		
		this.digitalItemDao.save(parentItem);
		this.digitalItemDao.save(childItem);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllChildren(String parentId) {
		ItemEntity parentItem = this.digitalItemDao
				.findById(parentId)
				.orElseThrow(()->new ItemNotFoundException("could not find parent item by id: " + parentId));
		
		return parentItem
				.getItemChildren() // Set<ItemEntity>
				.stream() // Stream<ItemEntity>
				.map(this.entityConverter::toBoundary)// Stream<ItemEntity>
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ItemBoundary> getAllParents(String childId) {
		ItemEntity childItem = this.digitalItemDao
				.findById(childId)
				.orElseThrow(()->new ItemNotFoundException("could not find parent item by id: " + childId));
		
		if (childItem.getItemParent() != null) {
			return Optional.of(
				this.entityConverter
					.toBoundary(childItem.getItemParent()));
		}else {
			return Optional.empty();
		}
	}
	
}
