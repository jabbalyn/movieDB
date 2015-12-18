package movieDB.db;

import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.SQLException;

//import org.dbunit.ext.mysql.MySqlConnection;


public class JDBCDAOFactory implements DAOFactory {

	private static final Logger logger = Logger.getLogger(JDBCDAOFactory.class.getName());
	
	// SQL connection-related objects
	Connection conn;

	
	// the DAO's that can be created
	ActorDAO actorDAO;
	
	// the databaseURL String passed in should follow this form: 
	//				protocol + host + "/" + databaseName +"?" + "user=" + userName + "&password=" + password
	// for example, "jdbc:mysql://localhost/test?user=root&password=XXXXX"
	private String databaseURL = null;
	
	public JDBCDAOFactory(String databaseURL) throws ClassNotFoundException, InstantiationException, 
									SQLException, IllegalAccessException {

		setDatabaseURL(databaseURL);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(this.databaseURL);
		} catch (ClassNotFoundException ex) {
			throw new ClassNotFoundException("JDBCDAOFActory could not find the class for the database Driver", ex);
		}	
		catch (SQLException ex) {
			throw new SQLException("JDBCDAOFactory's DriverManager could not get connection to JDBC SQL database", ex);
		} 
	}

	private void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}
	
	// creates an ActorDAO, sets its SQL Connection to that of the JDBCDAOFactory, and returns the ActorDAO
	public ActorDAO createActorDAO() throws SQLException {

		actorDAO = new ActorDAOJdbcImpl(conn);
		//actorDAO.setConnection(conn);
		return actorDAO;
	}
	
	public void close() throws Exception {
		deregisterJDBCDriver();
	}
	
	private void deregisterJDBCDriver() throws Exception {
		
		// DriverManager.getDrivers(): Retrieves an Enumeration with all of the currently loaded JDBC drivers  
		// to which the current caller (this JDBCDAOFactory class) has access (per DriverManager API)
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            DriverManager.deregisterDriver(driver);
        }
	}
}
