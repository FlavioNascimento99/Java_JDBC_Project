package database;

public class DatabaseException extends RuntimeException {
	
	private static final long SerialVersionUID = 1L;
	
	public DatabaseException(String message) {
		super(message);
	}
}
