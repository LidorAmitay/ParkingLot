package twins.logic;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import twins.data.OperationsDao;
import twins.data.OperationEntity;
import twins.digitalItemsAPI.ItemId;
import twins.operationsAPI.InvokedBy;
import twins.operationsAPI.Item;
import twins.operationsAPI.OperationBoundary;
import twins.operationsAPI.OperationId;
import twins.userAPI.UserId;

public class OperationsJpa implements OperationsService {
	private OperationsDao operationDao;
	private String space;
	private EntityConverter entityConvert;

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

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operation) {
		String newId = UUID.randomUUID().toString();
		operation.setOperationId(new OperationId(space, newId));
		operation.setCreatedTimestamp(new Date());

		if (operation.getItem() == null)
			operation.setItem(new Item(new ItemId()));
		else if (operation.getItem().getItemId() == null)
			operation.getItem().setItemId(new ItemId());

		if (operation.getInvokedBy() == null)
			operation.setInvokedBy(new InvokedBy(new UserId()));
		else if (operation.getInvokedBy().getUserId() == null)
			operation.getInvokedBy().setUserId(new UserId());

		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		entity = this.operationDao.save(entity);
		return this.entityConvert.toBoundary(entity);

	}

	@Override
	@Transactional
	public OperationBoundary invokeAsynchronousOperation(OperationBoundary operation) {
		String newId = UUID.randomUUID().toString();
		operation.setOperationId(new OperationId(space, newId));
		operation.setCreatedTimestamp(new Date());

		if (operation.getItem() == null)
			operation.setItem(new Item(new ItemId()));
		else if (operation.getItem().getItemId() == null)
			operation.getItem().setItemId(new ItemId());

		if (operation.getInvokedBy() == null)
			operation.setInvokedBy(new InvokedBy(new UserId()));
		else if (operation.getInvokedBy().getUserId() == null)
			operation.getInvokedBy().setUserId(new UserId());

		OperationEntity entity = this.entityConvert.fromBoundary(operation);
		entity = this.operationDao.save(entity);
		return this.entityConvert.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		Iterable<OperationEntity> allEntities = this.operationDao.findAll();

		return StreamSupport.stream(allEntities.spliterator(), false) // get stream from iterable
				.map(this.entityConvert::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteAllOperations(String adminSpace, String adminEmail) {
		this.operationDao.deleteAll();

	}

}
