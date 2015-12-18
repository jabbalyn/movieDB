package movieDB.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GenericDAOJdbcImpl {

	protected Connection conn = null;
	protected Statement stmt = null;
	
	public GenericDAOJdbcImpl(Connection conn) {
		setConnection(conn);
	}
	
	private void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	// creates a default statement, whose ResultSet's will default to type TYPE_FORWARD_ONLY and concurrency
	// CONCUR_READ_ONLY
	protected Statement createDefaultStatement(Connection connection) throws SQLException {
		
		// TODO: move the stmt creation to an open() method; add a close() method to close and release
		// the conn and stmt objects
		return connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY);
	}
	
	// creates a statement whose ResultSet's will have type TYPE_SCROLL_INSENSITIVE and concurrency
	// of type CONCUR_UPDATABLE
	protected Statement createUpdatableStatement(Connection connection) throws SQLException {
		
		// TODO: move the stmt creation to an open() method; add a close() method to close and release
		// the conn and stmt objects
		return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_UPDATABLE);
	}
	
	protected void close(Statement stmt, ResultSet rs) throws SQLException {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
	}
}
