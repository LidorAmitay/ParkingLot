package twins.logic;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import twins.data.OperationsDao;
import twins.data.UserDao;
import twins.data.UserEntity;
import twins.data.UserRole;
import twins.data.DigitalItemDao;
import twins.data.ItemEntity;
import twins.data.OperationEntity;
import twins.digitalItemsAPI.ItemId;
import twins.operationsAPI.InvokedBy;
import twins.operationsAPI.Item;
import twins.operationsAPI.OperationBoundary;
import twins.operationsAPI.OperationId;
import twins.userAPI.UserId;

@Service
public class OperationsJpa implements OperationsService {
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
	public Object invokeOperation(OperationBoundary operation) {

		Optional<UserEntity> optionalUser = this.userDao.findById(operation.getInvokedBy().getUserId().getSpace() + "@@"
				+ operation.getInvokedBy().getUserId().getEmail());
		if (optionalUser.isPresent()) {
			if (optionalUser.get().getRole().equals(UserRole.PLAYER) == false)
				throw new RuntimeException("Only a player can make operations");
		}
		else
			throw new RuntimeException("Can't find user with space : " + operation.getInvokedBy().getUserId().getSpace() +
					"and id : "+ operation.getInvokedBy().getUserId().getEmail());
		
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
		

		switch (operation.getType()) {

			case "enterParking":
	
				enterParking(operation);
				break;
	
			case "exitparking":
				
				double price;
				price = exitparking(operation);
				break;

		}
		
		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		entity = this.operationDao.save(entity);
		return this.entityConvert.toBoundary(entity);

	}

	private double exitparking(OperationBoundary operation) {
		Optional<ItemEntity> optionalParkingspot = this.digitalItemDao
				.findById(operation.getItem().getItemId().getSpace() + "@@" + operation.getItem().getItemId().getId());
		double price = 0;
		if (optionalParkingspot.isPresent()) {
			ItemEntity parkingSpot = new ItemEntity();
			parkingSpot = optionalParkingspot.get();
			Map<String, Object> itemAttributes = new HashMap<>();
			itemAttributes = entityConvert.fromJsonToMap(parkingSpot.getItemAttributes());
			// The price per hour is in the item attributes of the parent item, parkingLot item.
			int pricePerHour;
			pricePerHour = (int)entityConvert.fromJsonToMap(parkingSpot.getItemParent()
					.getItemAttributes()).get("priceOfParking");
			
			 price = calculatePrice((Date)itemAttributes.get("EntryTime"), operation.getCreatedTimestamp(),
					 pricePerHour);
			 
			itemAttributes.clear();
			parkingSpot.setItemAttributes(entityConvert.fromMapToJson(itemAttributes));
			digitalItemDao.save(parkingSpot);
		}
		return price;
	}

	private double calculatePrice(Date EntryParking, Date ExitParking, int priceParking) {

		double price = 1;

		long dif = ExitParking.getTime() - EntryParking.getTime(); 
		TimeUnit time = TimeUnit.MINUTES;
		double diffInMinutes = time.convert(dif, TimeUnit.MILLISECONDS);

		price = priceParking * (diffInMinutes / 15);

		return price;
	}

	private void enterParking(OperationBoundary operation) {

		Optional<ItemEntity> optionalParkingspot = this.digitalItemDao
				.findById(operation.getItem().getItemId().getSpace() + "@@" + operation.getItem().getItemId().getId());
		if (optionalParkingspot.isPresent()) {
			ItemEntity parkingSpot = new ItemEntity();
			parkingSpot = optionalParkingspot.get();
			Map<String, Object> ItemAttributes;
			if(parkingSpot.getItemAttributes() == null)
				 ItemAttributes = new HashMap<>();
			else
				ItemAttributes = entityConvert.fromJsonToMap(parkingSpot.getItemAttributes());
			ItemAttributes.put("EntryTime", operation.getCreatedTimestamp());
			ItemAttributes.put("idOperationCreate", operation.getOperationId());
			ItemAttributes.put("ParkedUser", operation.getInvokedBy().getUserId().getSpace() + "@@"
					+ operation.getInvokedBy().getUserId().getEmail());
			parkingSpot.setItemAttributes(entityConvert.fromMapToJson(ItemAttributes));
			digitalItemDao.save(parkingSpot);

		}else
			throw new ItemNotFoundException("Can't find parking spot item with id : "
					+ operation.getItem().getItemId().getSpace() + "@@" + operation.getItem().getItemId().getId());

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
		}
		else
			throw new RuntimeException("Can't find user with space : " + operation.getInvokedBy().getUserId().getSpace() +
					"and id : "+ operation.getInvokedBy().getUserId().getEmail());

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
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		Optional<UserEntity> optionalUser = this.userDao.findById(adminSpace + "@@" + adminEmail);
		if(optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if(admin.getRole().equals(UserRole.ADMIN)) {
				Iterable<OperationEntity> allEntities = this.operationDao.findAll();

				return StreamSupport.stream(allEntities.spliterator(), false) // get stream from iterable
						.map(this.entityConvert::toBoundary).collect(Collectors.toList());
			}
				
			else
				throw new RuntimeException("Only user with ADMIN role can get all operations");
		}else
			throw new RuntimeException("Can't find user with space : " + adminSpace + 
					" and id : " + adminEmail);
		
		
		
	}

	@Override
	@Transactional
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		Optional<UserEntity> optionalUser = this.userDao.findById(adminSpace + "@@" + adminEmail);
		if(optionalUser.isPresent()) {
			UserEntity admin = optionalUser.get();
			if(admin.getRole().equals(UserRole.ADMIN))
				this.operationDao.deleteAll();
			else
				throw new RuntimeException("Only user with ADMIN role can delete all operations");
		}else
			throw new RuntimeException("Can't find user with space : " + adminSpace + 
					" and id : " + adminEmail);
		
		
		
		

	}

}
