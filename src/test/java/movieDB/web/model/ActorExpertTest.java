package movieDB.web.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import movieDB.db.DAOFactory;
import movieDB.db.JDBCDAOFactory;
import movieDB.web.model.ActorExpert;
import movieDB.db.ActorDAO;

public class ActorExpertTest {
	
	private ActorDAO actorDAO = null;
	private ActorExpert actorExpert = null;
	
	@Before
	public void initActorDAO() throws Exception {
		String databaseURL = "jdbc:mysql://localhost/test?user=root&password=$hollywood1945";
		actorDAO = new JDBCDAOFactory(databaseURL).createActorDAO();
	}
	
	@Test(expected=NullPointerException.class)
	public void testActorExpertConstructorWithNullInput() {
		actorExpert = new ActorExpert(null);
	}
	
	@Test
	public void testActorExpertConstructorWithActorDAOInput() {
		actorExpert = new ActorExpert(actorDAO);
		Assert.assertNotNull("actorExpert init'd with an actorDAO shouldn't be null", actorExpert);
		Assert.assertNotNull("actorExpert's DAO shoul not be null", actorExpert.getActorDAO());
		Assert.assertTrue("actorExpert's DAO refernce should be the same as what was fed in at actorExpert's creation", 
							actorExpert.getActorDAO() == actorDAO);
		}
	
}
