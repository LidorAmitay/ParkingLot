package twins.logic;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import twins.data.DigitalItemDao;
import twins.data.ItemEntity;
import twins.digitalItemsAPI.CreatedBy;
import twins.digitalItemsAPI.ItemBoundary;
import twins.digitalItemsAPI.ItemId;
import twins.userAPI.UserId;

@Service
public class ItemsServiceJpa implements ItemsService {
	
	private String appName;
	private DigitalItemDao digitalItemDao;
	private EntityConverter entityConverter;
	
	
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


	@Override
	@Transactional
	public ItemBoundary createItem(String userSpace, String userEmail, ItemBoundary item) { 
		if(item.getLocation() == null)
			throw new RuntimeException("could not create an item with null location ");// NullPointerException
		
		else if(item.getLocation().getLat() == null || item.getLocation().getLng() == null)
			throw new RuntimeException("could not create an item with null location ");// NullPointerException
		
		if (item.getName()==null)
			throw new RuntimeException("could not create an item with null item name ");// NullPointerException
		if (item.getType()==null)
			throw new RuntimeException("could not create an item with null item type ");// NullPointerException
		
		item.setItemId(new ItemId(appName,UUID.randomUUID().toString()));
		item.setCreatedBy(new CreatedBy(new UserId(userSpace,userEmail)));
		item.setCreatedTimestamp(new Date());
		ItemEntity ie = this.entityConverter.fromBoundary(item);
		ie = this.digitalItemDao.save(ie);
		return this.entityConverter.toBoundary(ie);
	}
	
	
	@Override
	@Transactional
	public ItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
			ItemBoundary update) {
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
			// TODO have server return status 404 here
			throw new RuntimeException("could not find item " + itemId);// NullPointerException
		}	
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		Optional<ItemEntity> optionalEntity = this.digitalItemDao.findById(itemSpace+"@@"+itemId);
		if(optionalEntity.isPresent()) {
			ItemEntity ie = optionalEntity.get();
			return this.entityConverter.toBoundary(ie);
		}else {
			// TODO have server return status 404 here
			throw new RuntimeException("could not find item " + itemId);// NullPointerException
		}
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllItems(String userSpace, String userEmail) {
		Iterable<ItemEntity>  allEntities = this.digitalItemDao
				.findAll();
			
		return StreamSupport
			.stream(allEntities.spliterator(), false) // get stream from iterable
			.map(this.entityConverter::toBoundary)
			.collect(Collectors.toList());
	}
	
	
	@Override
	@Transactional
	public void deleteAllItems(String adminSpace, String adminEmail) {
		this.digitalItemDao
		.deleteAll();
	}
	
}
