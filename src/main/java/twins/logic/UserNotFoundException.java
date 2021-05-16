package twins.logic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

	
	private static final long serialVersionUID = 5223332024042116055L;
	
	public UserNotFoundException() {
	}

	public UserNotFoundException(String id) {
		super(id);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

	public UserNotFoundException(String id, Throwable cause) {
		super(id, cause);
	}
	
}
