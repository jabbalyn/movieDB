package movieDB.db;

public class NotInDataStoreException extends Exception {

	public NotInDataStoreException(String s) {
		super(s);
	}
	
	public NotInDataStoreException(Exception ex) {
		super(ex);
	}
}
