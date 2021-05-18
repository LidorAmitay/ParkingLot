package twins.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface DigitalItemDao extends PagingAndSortingRepository<ItemEntity, String>{

	public List<ItemEntity> findAllByLocation(
			@Param("lat") long lat,
			@Param("lng") long lng,			
			Pageable pageable);
	
	public List<ItemEntity> findAllByParent(
			@Param("parentId") String parentId,
			@Param("active") boolean active,			
			Pageable pageable);

}
