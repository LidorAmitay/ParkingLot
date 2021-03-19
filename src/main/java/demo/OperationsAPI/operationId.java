package demo.OperationsAPI;

public class operationId {
	private String space;
	private long id;
	private static long idCount = 1;
	
	
	public operationId() {
	}


	public operationId(String space, long id) {
		super();
		this.space = space;
		this.id = id;
	}
	
	
	public String getSpace() {
		return space;
	}
	
	public void setSpace(String space) {
		this.space = space;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	public static long newId() {
		return operationId.idCount++;
	}


	public static long getIdCount() {
		return idCount;
	}


	public static void setIdCount(long idCount) {
		operationId.idCount = idCount;
	}
	
	
}

