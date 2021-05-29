package twins.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

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
	
	public Optional<ItemEntity> findByTypeAndUserId(
			@Param("type") String type,
			@Param("userId") String userId);


}
