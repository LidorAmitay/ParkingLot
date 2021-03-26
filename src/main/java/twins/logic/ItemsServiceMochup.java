package twins.logic;


import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import twins.data.EntityConverter;
import twins.data.ItemEntity;
import twins.digitalItemsAPI.ItemBoundary;

@Service
public class ItemsServiceMochup implements ItemsService {
	
	private Map<String, ItemEntity> items;
	private EntityConverter entityConverter;
	
	
	
	public ItemsServiceMochup(Map<String, ItemEntity> items) {
		super();
		this.items = items;
	}


	@Override
	public ItemBoundary createItem(String userSpace, String userEmail, ItemBoundary item) {
		ItemEntity ie = this.entityConverter.fromBoundary(item); 
		ie.setId(UUID.randomUUID().toString());
		ie.setSpace(userSpace);
		ie.setEmail(userEmail);
		this.items.put(ie.getId(), ie);
		return this.entityConverter.toBoundary(ie);
	}
	
	
	@Override
	public ItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
			ItemBoundary update) {
		ItemEntity entity = this.items.get(itemId);
		
		if(entity != null) {
			entity.setActive(update.getActive());
			entity.setCreatedTimestamp(update.getCreatedTimestamp());
			entity.setEmail(userEmail);
			entity.setLat(update.getLocation().getLat());
			entity.setLng(update.getLocation().getLng());
			entity.setName(update.getName());
			entity.setType(update.getType());
			return this.entityConverter.toBoundary(entity);
		} else {
			// TODO have server return status 404 here
			throw new RuntimeException("could not find item " + itemId);// NullPointerException
		}	
	}
	
	
	@Override
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		ItemEntity ie = this.items.get(itemId);
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
