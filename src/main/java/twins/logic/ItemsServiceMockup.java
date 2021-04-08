package twins.logic;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import twins.data.EntityConverter;
import twins.data.ItemEntity;
import twins.digitalItemsAPI.ItemBoundary;

@Service
public class ItemsServiceMockup implements ItemsService {
	
	@Value("${spring.application.name}")
	private String appName;
	private Map<String, ItemEntity> items;
	private EntityConverter entityConverter;
	
	
	
	public ItemsServiceMockup(Map<String, ItemEntity> items) {
		super();
		this.items = items;
	}
	
	@Autowired
	public void setEntityConverter(EntityConverter entityConverter) {
		this.entityConverter = entityConverter;
	}


	@Override
	public ItemBoundary createItem(String userSpace, String userEmail, ItemBoundary item) {
		ItemEntity ie = this.entityConverter.fromBoundary(item); 
		if(item.getLocation() == null)
			throw new RuntimeException("could not create an item with null location ");// NullPointerException
		
		else if(item.getLocation().getLat() == null || item.getLocation().getLng() == null)
			throw new RuntimeException("could not create an item with null location ");// NullPointerException
		ie.setItemId(appName+"@@"+UUID.randomUUID().toString());
		ie.setUserId(userSpace+"@@"+userEmail);
		ie.setCreatedTimestamp(new Date());
		this.items.put(ie.getItemId(), ie);
		return this.entityConverter.toBoundary(ie);
	}
	
	
	@Override
	public ItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
			ItemBoundary update) {
		ItemEntity entity = this.items.get(itemSpace+"@@"+itemId);
		
		if(entity != null) {
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
	
			return this.entityConverter.toBoundary(entity);
		} else {
			// TODO have server return status 404 here
			throw new RuntimeException("could not find item " + itemId);// NullPointerException
		}	
	}
	
	
	@Override
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		ItemEntity ie = this.items.get(itemSpace+"@@"+itemId);
		if(ie != null) {
			return this.entityConverter.toBoundary(ie);
		}else {
			// TODO have server return status 404 here
			throw new RuntimeException("could not find item " + itemId);// NullPointerException
		}
	}
	
	
	@Override
	public List<ItemBoundary> getAllItems(String userSpace, String userEmail) {
		return this.items
				.values()
				.stream()
				.map(this.entityConverter::toBoundary)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public void deleteAllItems(String adminSpace, String adminEmail) {
		this.items
		.clear();
	}
	
}
