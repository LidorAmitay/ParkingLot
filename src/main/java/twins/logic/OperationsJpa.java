package twins.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import twins.data.OperationsDao;
import twins.data.UserDao;
import twins.data.UserEntity;
import twins.data.UserRole;
import twins.data.DigitalItemDao;
import twins.data.ItemEntity;
import twins.data.OperationEntity;
import twins.digitalItemsAPI.ItemBoundary;
import twins.digitalItemsAPI.ItemId;
import twins.operationsAPI.InvokedBy;
import twins.operationsAPI.Item;
import twins.operationsAPI.OperationBoundary;
import twins.operationsAPI.OperationId;
import twins.userAPI.UserBoundary;
import twins.userAPI.UserId;

@Service
public class OperationsJpa implements OperationsServiceExtends {
	private OperationsDao operationDao;
	private String space;
	private EntityConverter entityConvert;
	private UserDao userDao;
	private DigitalItemDao digitalItemDao;

	@Value("${spring.application.name:defaultName}")
	public void setSpringApplicatioName(String space) {
		this.space = space;
	}

	@Autowired
	public void setOperationDao(OperationsDao operationDao) {
		this.operationDao = operationDao;
	}

	@Autowired
	public void setEntityConvert(EntityConverter entityConvert) {
		this.entityConvert = entityConvert;
	}

	@Autowired
	public void setUserdao(UserDao userdao) {
		this.userDao = userdao;
	}

	@Autowired
	public void setDigitalItemDao(DigitalItemDao digitalItemDao) {
		this.digitalItemDao = digitalItemDao;
	}

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operation, int page, int size) {

		Optional<UserEntity> optionalUser = this.userDao.findById(operation.getInvokedBy().getUserId().getSpace() + "@@"
				+ operation.getInvokedBy().getUserId().getEmail());
		if (optionalUser.isPresent()) {
			if (optionalUser.get().getRole().equals(UserRole.PLAYER) == false)
				throw new RuntimeException("Only a player can make operations");
		} else
			throw new RuntimeException("Can't find user with space : " + operation.getInvokedBy().getUserId().getSpace()
					+ "and id : " + operation.getInvokedBy().getUserId().getEmail());

		String newId = UUID.randomUUID().toString();
		operation.setOperationId(new OperationId(space, newId));
		operation.setCreatedTimestamp(new Date());
		if (operation.getItem() == null)
			throw new RuntimeException("Can't invoke operation with null item");
		else if (operation.getItem().getItemId() == null)
			throw new RuntimeException("Can't invoke operation with null item id");
		else if (operation.getItem().getItemId().getSpace() == null || operation.getItem().getItemId().getId() == null)
			throw new RuntimeException("Can't invoke operation with null item id or space");

		if (operation.getInvokedBy() == null)
			throw new RuntimeException("Can't invoke operation with null user");
		else if (operation.getInvokedBy().getUserId() == null)
			throw new RuntimeException("Can't invoke operation with null user id");
		else if (operation.getInvokedBy().getUserId().getEmail() == null
				|| operation.getInvokedBy().getUserId().getSpace() == null)
			throw new RuntimeException("Can't invoke operation with null user space or email");

		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		entity = this.operationDao.save(entity);

		switch (operation.getType()) {

		case "enterParking":

			enterParking(operation);
			break;

		case "exitparking":

			double price;
			price = exitparking(operation);
			Map<String, Object> priceJson = new HashMap<>();
			priceJson.put("Price", price);
			return priceJson;
		// break;

		case "searchParking":

			return searchParkingSpot(operation, page, size);

		case "searchParkingLot":

			return searchParkingLot(operation, page, size);

		case "getAllParkingLots":

			return getAllParkingLots(operation, page, size);

		}

		return this.entityConvert.toBoundary(entity);

	}

	private Object getAllParkingLots(OperationBoundary operation, int page, int size) {
		return this.digitalItemDao
				.findAllByTypeAndActive("parkingLot", true, PageRequest.of(page, size, Direction.DESC, "name")).stream()
				.map(this.entityConvert::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional // Old
	public Object invokeOperation(OperationBoundary operation) {

		Optional<UserEntity> optionalUser = this.userDao.findById(operation.getInvokedBy().getUserId().getSpace() + "@@"
				+ operation.getInvokedBy().getUserId().getEmail());
		if (optionalUser.isPresent()) {
			if (optionalUser.get().getRole().equals(UserRole.PLAYER) == false)
				throw new RuntimeException("Only a player can make operations");
		} else
			throw new RuntimeException("Can't find user with space : " + operation.getInvokedBy().getUserId().getSpace()
					+ "and id : " + operation.getInvokedBy().getUserId().getEmail());

		String newId = UUID.randomUUID().toString();
		operation.setOperationId(new OperationId(space, newId));
		operation.setCreatedTimestamp(new Date());
		if (operation.getItem() == null)
			throw new RuntimeException("Can't invoke operation with null item");
		else if (operation.getItem().getItemId() == null)
			throw new RuntimeException("Can't invoke operation with null item id");
		else if (operation.getItem().getItemId().getSpace() == null || operation.getItem().getItemId().getId() == null)
			throw new RuntimeException("Can't invoke operation with null item id or space");

		if (operation.getInvokedBy() == null)
			throw new RuntimeException("Can't invoke operation with null user");
		else if (operation.getInvokedBy().getUserId() == null)
			throw new RuntimeException("Can't invoke operation with null user id");
		else if (operation.getInvokedBy().getUserId().getEmail() == null
				|| operation.getInvokedBy().getUserId().getSpace() == null)
			throw new RuntimeException("Can't invoke operation with null user space or email");

		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		entity = this.operationDao.save(entity);
		return this.entityConvert.toBoundary(entity);

	}

	private double exitparking(OperationBoundary operation) {
		double price = 0;
		Optional<ItemEntity> optionalUser = this.digitalItemDao.findByTypeAndUserId("User",
				operation.getInvokedBy().getUserId().getSpace() + "@@"
						+ operation.getInvokedBy().getUserId().getEmail());
		if (!optionalUser.isPresent())
			throw new ItemNotFoundException(
					"Cannot find userItem : " + operation.getInvokedBy().getUserId().getEmail());

		ItemEntity userItem = optionalUser.get();
		String parkingSpotId = (String) entityConvert.fromJsonToMap(userItem.getItemAttributes()).get("parkingId");
		Optional<ItemEntity> optionalParkingSpot = this.digitalItemDao.findById(parkingSpotId);

		if (optionalParkingSpot.isPresent()) {
			ItemEntity theParkingSpot = optionalParkingSpot.get();
			Map<String, Object> parkingSpotItemAttributes = entityConvert
					.fromJsonToMap(theParkingSpot.getItemAttributes());

			// The price per hour is in the item attributes of the parent item, parkingLot
			// item.
			int pricePerHour;
			pricePerHour = (int) entityConvert.fromJsonToMap(theParkingSpot.getItemParent().getItemAttributes())
					.get("priceOfParking");

			price = calculatePrice((long) parkingSpotItemAttributes.get("EntryTime"), operation.getCreatedTimestamp(),
					pricePerHour);
			// Making the parking spot available, and clear the previous details.
			parkingSpotItemAttributes.put("isAvailable", true);
			parkingSpotItemAttributes.put("EntryTime", null);
			parkingSpotItemAttributes.put("idOperationCreate", null);
			parkingSpotItemAttributes.put("ParkedUser", null);
			theParkingSpot.setItemAttributes(entityConvert.fromMapToJson(parkingSpotItemAttributes));
			digitalItemDao.save(theParkingSpot);

			ItemBoundary userB = entityConvert.toBoundary(userItem);
			Map<String, Object> itemAttributes = userB.getItemAttributes();
			itemAttributes.put("parkingId", null);
			userB.setItemAttributes(itemAttributes);
			userItem = entityConvert.fromBoundary(userB);
			digitalItemDao.save(userItem);
		} else
			throw new ItemNotFoundException(
					"Cannot find parking lot with space : " + operation.getItem().getItemId().getSpace() + "and id : "
							+ operation.getItem().getItemId().getId());
		return price;
	}

	private double calculatePrice(long EntryParking, Date ExitParking, int priceParking) {

		double price = 1;

		long dif = ExitParking.getTime() - EntryParking;
		TimeUnit time = TimeUnit.MINUTES;
		double diffInMinutes = time.convert(dif, TimeUnit.MILLISECONDS);

		price = priceParking * (diffInMinutes / 15);

		return price;
	}

	private void enterParking(OperationBoundary operation) {
		
		String userId = operation.getInvokedBy().getUserId().getSpace() + "@@" 
				+ operation.getInvokedBy().getUserId().getEmail();
		Optional<ItemEntity> optionalUser = this.digitalItemDao.findByTypeAndUserId("User", userId);
		
		ItemEntity user = null;
		Map<String, Object> userAttributes = null;
									
		//checking if the user item exists in the database
		
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
			userAttributes = entityConvert.fromJsonToMap(user.getItemAttributes());
		}
		else
			throw new ItemNotFoundException("User item not found with user id : " + userId);
		
		if (userAttributes.get("parkingId") != null)
			throw new RuntimeException("The Driver is already registered into another parking spot.");
		
		
		Optional<ItemEntity> optionalParkingSpot = this.digitalItemDao 
				.findById(operation.getItem().getItemId().getSpace() + "@@" + operation.getItem().getItemId().getId());
		
		if (optionalParkingSpot.isPresent()) {
			ItemEntity parkingSpot = new ItemEntity();
			parkingSpot = optionalParkingSpot.get();
			
			if (!parkingSpot.getActive())
				throw new ItemNotFoundException(
						"Could not find item space : " + operation.getItem().getItemId().getSpace() + "and id : "
								+ operation.getItem().getItemId().getId());
			
			//checking if the parking spot exists (by active field) and available
			
			Map<String, Object> itemAttributes;
			if (parkingSpot.getItemAttributes() == null) {
				itemAttributes = new HashMap<>();
			} 
			else {
				itemAttributes = entityConvert.fromJsonToMap(parkingSpot.getItemAttributes());
			}
			
			if ((boolean) itemAttributes.get("isAvailable") == false)
				throw new RuntimeException("Parking spot is not available with item id : " + parkingSpot.getItemId());
			
			//updating the parking spot to not available and update the information on the parking spot 
			
			itemAttributes.put("isAvailable", false);
			itemAttributes.put("EntryTime", operation.getCreatedTimestamp());
			itemAttributes.put("idOperationCreate", operation.getOperationId());
			itemAttributes.put("ParkedUser", operation.getInvokedBy().getUserId().getSpace() + "@@"
					+ operation.getInvokedBy().getUserId().getEmail());
			parkingSpot.setItemAttributes(entityConvert.fromMapToJson(itemAttributes));
			
			digitalItemDao.save(parkingSpot);
			
			
			userAttributes.put("parkingId", operation.getItem().getItemId().getSpace() + "@@" + operation.getItem().getItemId().getId());
			user.setItemAttributes(entityConvert.fromMapToJson(userAttributes));
	
			digitalItemDao.save(user);
			
			//updating the user item to save the parking spot that he parked in

		} else
			throw new ItemNotFoundException("Can't find parking spot item with id : "
					+ operation.getItem().getItemId().getSpace() + "@@" + operation.getItem().getItemId().getId());

	}

	private List<ItemBoundary> searchParkingLot(OperationBoundary operation, int page, int size) {

		long lat = (long) operation.getOperationAttributes().get("cityLocationLat");
		long lng = (long) operation.getOperationAttributes().get("cityLocationLng");

		return this.digitalItemDao.findAllByLatAndLng(lat, lng, PageRequest.of(page, size, Direction.DESC, "name"))
				.stream().map(this.entityConvert::toBoundary).collect(Collectors.toList());

	}

	private List<ItemBoundary> searchParkingSpot(OperationBoundary operation, int page, int size) {

		String parkingLotId = operation.getItem().getItemId().getSpace() + "@@"
				+ operation.getItem().getItemId().getId();
		ItemEntity parent;
		Optional<ItemEntity> optionalParent = this.digitalItemDao.findById(parkingLotId);
		if (optionalParent.isPresent()) {
			parent = optionalParent.get();
			boolean checkActivation = parent.getActive();
			if (!checkActivation)
				throw new ItemNotFoundException("Parking lot does not exist");
			ArrayList<ItemBoundary> children = new ArrayList<>();
			for (ItemEntity child : parent.getItemChildren()) {
				checkActivation = child.getActive();
				if (checkActivation) {
					ItemBoundary childBoundary = this.entityConvert.toBoundary(child);
					if ((boolean) childBoundary.getItemAttributes().get("isAvailable") == true) {
						children.add(childBoundary);
					}
				}
			}
			if (children.size() != 0) {
				Collections.sort(children, new Comparator<ItemBoundary>() {
											@Override
											public int compare(ItemBoundary o1, ItemBoundary o2) {
												if (o1.getName().length() != o2.getName().length())
													return o1.getName().length() - o2.getName().length();
												return o1.getName().compareTo(o2.getName());
											}
								});
				return children.subList(page * size, page * size + size);
			}
			return children;

		} else
			return Collections.<ItemBoundary>emptyList();

	}

	@Override
	@Transactional
	public OperationBoundary invokeAsynchronousOperation(OperationBoundary operation) {
		// Permission check
		Optional<UserEntity> optionalUser = this.userDao.findById(operation.getInvokedBy().getUserId().getSpace() + "@@"
				+ operation.getInvokedBy().getUserId().getEmail());
		if (optionalUser.isPresent()) {
			if (optionalUser.get().getRole().equals(UserRole.PLAYER) == false)
				throw new RuntimeException("Only a player can make operations");
		} else
			throw new RuntimeException("Can't find user with space : " + operation.getInvokedBy().getUserId().getSpace()
					+ "and id : " + operation.getInvokedBy().getUserId().getEmail());

		String newId = UUID.randomUUID().toString();
		operation.setOperationId(new OperationId(space, newId));
		operation.setCreatedTimestamp(new Date());

		if (operation.getItem() == null)
			throw new RuntimeException("Can't invoke operation with null item");
		else if (operation.getItem().getItemId() == null)
			throw new RuntimeException("Can't invoke operation with null item id");
		else if (operation.getItem().getItemId().getSpace() == null || operation.getItem().getItemId().getId() == null)
			throw new RuntimeException("Can't invoke operation with null item id or space");
		if (operation.getInvokedBy() == null)
			throw new RuntimeException("Can't invoke operation with null user");
		else if (operation.getInvokedBy().getUserId() == null)
			throw new RuntimeException("Can't invoke operation with null user id");
		else if (operation.getInvokedBy().getUserId().getEmail() == null
				|| operation.getInvokedBy().getUserId().getSpace() == null)
			throw new RuntimeException("Can't invoke operation with null user space or email");

		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		entity = this.operationDao.save(entity);
		return this.entityConvert.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail, int page, int size) {
		Optional<UserEntity> optionalUser = this.userDao.findById(adminSpace + "@@" + adminEmail);
		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN)) {
				Iterable<OperationEntity> allEntities = this.operationDao
						.findAll(PageRequest.of(page, size, Direction.DESC, "type", "OperationSpaceId"));

				return StreamSupport.stream(allEntities.spliterator(), false) // get stream from iterable
						.map(this.entityConvert::toBoundary).collect(Collectors.toList());
			}

			else
				throw new RuntimeException("Only user with ADMIN role can get all operations");
		} else
			throw new RuntimeException("Can't find user with space : " + adminSpace + " and id : " + adminEmail);

	}

	@Override
	@Transactional(readOnly = true)
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		Optional<UserEntity> optionalUser = this.userDao.findById(adminSpace + "@@" + adminEmail);
		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN)) {
				Iterable<OperationEntity> allEntities = this.operationDao.findAll();

				return StreamSupport.stream(allEntities.spliterator(), false) // get stream from iterable
						.map(this.entityConvert::toBoundary).collect(Collectors.toList());
			}

			else
				throw new RuntimeException("Only user with ADMIN role can get all operations");
		} else
			throw new RuntimeException("Can't find user with space : " + adminSpace + " and id : " + adminEmail);

	}

	@Override
	@Transactional
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		Optional<UserEntity> optionalUser = this.userDao.findById(adminSpace + "@@" + adminEmail);
		if (optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if (admin.getRole().equals(UserRole.ADMIN))
				this.operationDao.deleteAll();
			else
				throw new RuntimeException("Only user with ADMIN role can delete all operations");
		} else
			throw new RuntimeException("Can't find user with space : " + adminSpace + " and id : " + adminEmail);

	}

}
