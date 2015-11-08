package johnson.jconfig.exceptions;

/**
 * @author Johnson on 07.11.2015.
 */
public class JConfigRException extends RuntimeException {

	private final int errorCode;

	public JConfigRException() {
		super();
		errorCode = -1;
	}

	public JConfigRException(String message) {
		super(message);
		errorCode = -1;
	}

	public JConfigRException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public JConfigRException(Throwable cause) {
		super(cause);
		errorCode = -1;
	}

	public JConfigRException(Throwable cause, int errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public JConfigRException(String message, Throwable cause) {
		super(message, cause);
		errorCode = -1;
	}

	public JConfigRException(String message, Throwable cause, int errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
