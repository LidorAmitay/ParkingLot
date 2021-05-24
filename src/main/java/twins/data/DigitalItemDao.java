package twins.data;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

public interface DigitalItemDao extends PagingAndSortingRepository<ItemEntity, String>{

	public List<ItemEntity> findAllByLatAndLng(
			@Param("lat") long lat,
			@Param("lng") long lng,			
			Pageable pageable);
	
	public List<ItemEntity> findAllByItemParentAndActive(
			@Param("parentId") ItemEntity parentId,
			@Param("active") boolean active,			
			Pageable pageable);
	
	public List<ItemEntity> findAllByActive(
			@Param("active") boolean active,			
			Pageable pageable);

	public List<ItemEntity> findAllByTypeAndActive(
			@Param("type") String type,
			@Param("active") boolean active,			
			Pageable pageable);


}
