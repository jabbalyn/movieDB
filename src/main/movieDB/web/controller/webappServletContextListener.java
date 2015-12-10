package movieDB.web.controller;

import javax.servlet.*;

import com.mysql.jdbc.Driver;

import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Collection;
import java.util.logging.FileHandler;

import movieDB.core.Actor;
import movieDB.db.DAOFactory;
import movieDB.db.JDBCDAOFactory;
import movieDB.web.model.ActorExpert;



public class webappServletContextListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(webappServletContextListener.class.getName());

	String dbFactoryClass = null;	
	DAOFactory daoFactory = null;
	
	public webappServletContextListener() {
		try {
			logger.addHandler(new FileHandler("webappServletContextListener.log"));
		} catch (Exception ex) {
			System.out.println("webappServletContextListener couldn't add FileHandler to Logger");
		}
	}
	
	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext ctx = event.getServletContext();
		dbFactoryClass = ctx.getInitParameter("databaseFactoryClass");	
		String databaseURL = ctx.getInitParameter("databaseURL");
		
		try {
			daoFactory = createDAOFactory(dbFactoryClass, databaseURL);
			ctx.setAttribute("movieDatabase", daoFactory);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "exception while reading DAOFactory class from servlet context and initiating the class", ex);
		}

	}
	
	public void contextDestroyed(ServletContextEvent event) {
		try {
			daoFactory.close();
			logger.log(Level.INFO, "Deregistering jdbc driver for: " + Driver.class.getName());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Deregistering jdbc driver for: " + Driver.class.getName(), e);
		}
	}
	
	private DAOFactory createDAOFactory(String factoryClass, String databaseURL) 
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

		DAOFactory daoFactory = null;
		
		// TODO: generalize method for instantiating multiple types of factories (JDBC, Hibernate, etc)
		
		/**

		if (factoryClass.equals("movieDB.db.JDBCDAOFactory")) {
			daoFactory = JDBCDAOFactory.class.getConstructor(String.class).newInstance(databaseURL);
			logger.log(Level.INFO, "built DAOFactory listed in the web.xml: " + factoryClass);
		}
		*/
		
		switch (dbFactoryClass) {
			case "movieDB.db.JDBCDAOFactory": 			
				daoFactory = JDBCDAOFactory.class.getConstructor(String.class).newInstance(databaseURL);
				logger.log(Level.INFO, "built DAOFactory listed in the web.xml: " + factoryClass);
				break;
			default:
				throw new IllegalArgumentException("couldn't find databaseFactoryClass class defined in web.xml: " + dbFactoryClass);
					
			
		}
		return daoFactory;
	}
}
