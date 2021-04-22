package twins.logic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -1534001621518790554L;
	
	public ItemNotFoundException() {
	}

	public ItemNotFoundException(String id) {
		super(id);
	}

	public ItemNotFoundException(Throwable cause) {
		super(cause);
	}

	public ItemNotFoundException(String id, Throwable cause) {
		super(id, cause);
	}
	
}
