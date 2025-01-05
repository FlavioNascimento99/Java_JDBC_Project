package database;

public class DatabaseIntegrityException extends RuntimeException {
	private static final long SerialVersionUID = 1L;
	
	public DatabaseIntegrityException(String message) {
		super(message);
	}
}
