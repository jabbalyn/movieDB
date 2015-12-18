package movieDB.db;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import java.util.Collection;

import movieDB.core.Actor;

public class JDBCDAOFactoryTest {

	DAOFactory daoFactory;
	
	@Before
	public void setupFactory() throws Exception {
		String databaseURL = "jdbc:mysql://localhost/test?user=root&password=$hollywood1945";
		daoFactory = new JDBCDAOFactory(databaseURL);
	}
	
	@Test(expected=SQLException.class)
	public void testCreateJDBCDAOFactoryWithEmptyStringInput() throws Exception {
		daoFactory = new JDBCDAOFactory("");
	}
	
	@Test(expected=SQLException.class)
	public void testCreateJDBCDAOFactoryWithNullInput() throws Exception {
		daoFactory = new JDBCDAOFactory(null);
	}
	
	@Test
	public void testCreateJDBCDAOFactory() {
		Assert.assertNotNull("newly-created daoFactory shouldn't be null", daoFactory);
	}
	
	@Test
	public void testCreateActorDAO() throws Exception {
		ActorDAO actorDAO = daoFactory.createActorDAO();
		if (actorDAO != null) {
			Assert.assertNotNull("createActorDAO should never return a null object / should create one if necessary",
								actorDAO);
		}
	}
		
	@Test
	public void testGetActorsFromActorDAO() throws Exception {
		ActorDAO actorDAO = daoFactory.createActorDAO();
		Collection<Actor> actors = actorDAO.getActors("");
		Assert.assertTrue("getActors(emptyString) should return empty collection of actors", actors.size() == 0);
	}
	
	
}
