package movieDB.db;

public class FailedUpdateException extends Exception {

	public FailedUpdateException(String s) {
		super(s);
	}
	
	public FailedUpdateException(Exception ex) {
		super(ex);
	}
}
