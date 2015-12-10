package movieDB.db;

import movieDB.core.Actor;
import movieDB.core.DuplicateException;
//import movieDB.db.UtilSQL;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import java.sql.DriverManager;
import java.sql.Connection;

import java.util.Calendar;
import java.util.Collection;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ActorDAOJdbcImplTest {

	private static Connection conn;
	private static MySqlConnection dbUnitConnection;
	private static ActorDAO actorDAO;
	
	public final String HARRISON_FORD = "Harrison Ford";
	
	private String SINGLE_ACTOR_XML_FILE = "./singleActor.xml";
	private String SINGLE_ACTOR_ID_TOKEN_XML_FILE = "./singleActor_IdToken.xml";
	private String TWO_ACTORS_XML_FILE = "./twoActors.xml";
	private String THREE_ACTORS_SAME_NAME_DIFF_BIRTHDATES = "ThreeActorsSameNameDiffBirthdates.xml";
	
	@BeforeClass
	public static void setupDatabase() throws Exception {

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + 
					"user=root&password=$hollywood1945");
		dbUnitConnection = new MySqlConnection(conn, null);
	
		actorDAO = new ActorDAOJdbcImpl(conn);
		//actorDAO.setConnection(conn);
		
	}
	
	@AfterClass
	public static void closeDatabase() throws Exception {
		if (conn != null) {
			conn.close();
			conn = null;
		}
		if (dbUnitConnection != null) {
			dbUnitConnection.close();
			dbUnitConnection = null;
		}
	}
	
	protected IDataSet getDataSet(String name) throws Exception {
		InputStream inputStream = getClass().getResourceAsStream(name);
		Assert.assertNotNull("file " + name + " not found in classpath", inputStream);
		InputStreamReader reader = new InputStreamReader(inputStream);
		FlatXmlDataSet dataset = new FlatXmlDataSetBuilder().build(reader);
		return dataset;
	}
	
	private IDataSet deleteFromDatabaseUsingDataSet(String dataset_xml_file) throws Exception {
		IDataSet expectedDataSet = getDataSet(dataset_xml_file);
		DatabaseOperation.DELETE_ALL.execute(dbUnitConnection, expectedDataSet);	
		return expectedDataSet;
	}
	
	private IDataSet getReplacedDataSet(String xml_file_location, long id) throws Exception {
		IDataSet originalDataSet = getDataSet(xml_file_location);
		return getReplacedDataSet(originalDataSet, id);
	}
	
	private IDataSet getReplacedDataSet(IDataSet originalDataSet, long id) {
		
		ReplacementDataSet replacementDataSet = new ReplacementDataSet(originalDataSet);
		replacementDataSet.addReplacementObject("[ID]", id);
		return replacementDataSet;
	}
	
	private void compareStoredActorsAgainst(IDataSet expectedDataSet) throws Exception {

		IDataSet actualDataSet = dbUnitConnection.createDataSet(new String[] {"actor"});
		Assertion.assertEquals(expectedDataSet, actualDataSet);
	}
	
	@Test
	public void testActorGetActorByIDSingleActorWithReadback() throws Exception {
		
		IDataSet expectedDataSet = getDataSet(SINGLE_ACTOR_XML_FILE);
		DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, expectedDataSet);
		
		long expectedID = 1;
		Actor storedActor = actorDAO.getActorById(expectedID);

		Assert.assertNotNull("a single actor retured getActorByID() shouldn't be null", storedActor);
		Assert.assertEquals("the only actor inserted should have ID of 1", expectedID, storedActor.getID());
		Assert.assertEquals("name of initial and stored actors must be the same", HARRISON_FORD, storedActor.getName());
		
		// birthdate should be equivalent to '1943-7-13' in SQL format
		Calendar birthdate = storedActor.getBirthDate();
		Assert.assertTrue(birthdate.get(Calendar.YEAR) == 1943 - 1);
		Assert.assertTrue(birthdate.get(Calendar.MONTH) == 7 - 1);
		Assert.assertTrue(birthdate.get(Calendar.DAY_OF_MONTH) == 13);
	}
	
	@Test
	public void testActorGetActorByIDReturnsNullWhenSingleActorIdNotExistant() throws Exception {
		
		IDataSet expectedDataSet = getDataSet(SINGLE_ACTOR_XML_FILE);
		DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, expectedDataSet);
		
		long expectedID = 123456789;
		Actor storedActor = actorDAO.getActorById(expectedID);
		Assert.assertNull("stored actor with non-existent ID should return null actor", storedActor);
		
	}
	
	@Test
	public void testActorGetActorByIDReturnsNullAfterDeletingActor() throws Exception {
		
		IDataSet expectedDataSet = getDataSet(SINGLE_ACTOR_XML_FILE);
		DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, expectedDataSet);
		DatabaseOperation.DELETE_ALL.execute(dbUnitConnection, expectedDataSet);	
		
		long expectedID = 1;
		Actor storedActor = actorDAO.getActorById(expectedID);
		Assert.assertNull("getByID search for a deleted actor should return null actor", storedActor);
	}
	
	@Test
	public void testAddActorNotInDatabase_CheckActorIDWasReturnedAndSet_IgnoreIdColumn() throws Exception {
		
		IDataSet expectedDataSet = deleteFromDatabaseUsingDataSet(SINGLE_ACTOR_XML_FILE);
		
		Actor actor = new Actor("Harrison Ford", UtilsSQL.getCalendarFromSQLDate("1943-7-13"));
		long newID = actorDAO.addActor(actor);
		Assert.assertTrue("actor ID should match the new ID used in storage", actor.getID() == newID);		
		
		IDataSet actualDataSet = dbUnitConnection.createDataSet();
		// ignore checking the id column since multiple actor insertions may cause the database's 
		// auto-generated id's to increase and change with each insertion; however, we can still check the 
		// actor's name and birthdate
		Assertion.assertEqualsIgnoreCols(expectedDataSet, actualDataSet, "actor", new String[] {"actorID"});
	}
	
	@Test
	public void testAddActorNotInDatabase_CheckActorIDWasReturnedAndSet_UsingIDToken() throws Exception {
	
		deleteFromDatabaseUsingDataSet(SINGLE_ACTOR_XML_FILE);
		
		Actor actor = new Actor("Harrison Ford", UtilsSQL.getCalendarFromSQLDate("1943-7-13"));
		long newID = actorDAO.addActor(actor);
		Assert.assertTrue("actor ID should match the new ID used in storage", actor.getID() == newID);		
		
		IDataSet expectedDataSetWithIDToken = getReplacedDataSet(SINGLE_ACTOR_ID_TOKEN_XML_FILE, newID);
		compareStoredActorsAgainst(expectedDataSetWithIDToken);
	}
	
	@Test(expected=DuplicateException.class)
	public void testAddActorWithDuplicate_UsingIDToken() throws Exception {
	
		IDataSet expectedDataSet = getDataSet(SINGLE_ACTOR_XML_FILE);	
		DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, expectedDataSet);
		
		// expectedID below must match the xml file's id #
		long expectedID = 1;
		Actor storedActor = actorDAO.getActorById(expectedID);
		IDataSet expectedDataSetWithIDToken = getReplacedDataSet(SINGLE_ACTOR_ID_TOKEN_XML_FILE, 
																storedActor.getID());
		compareStoredActorsAgainst(expectedDataSetWithIDToken);
		long failedOperationID = actorDAO.addActor(storedActor);
	}
	
	@Test
	public void testAddActorWithNullInput_DBisUnchanged() throws Exception {
		IDataSet expectedDataSet = getDataSet(SINGLE_ACTOR_XML_FILE);	
		DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, expectedDataSet);
		
		long failedOperationID = actorDAO.addActor(null);
		Assert.assertTrue("trying to write a null actor will return a -1 error", failedOperationID == -1);
		compareStoredActorsAgainst(expectedDataSet);
	}
	
	@Test
	public void testGetActorsWithSameName_DifferentBirthdates() throws Exception {
		
		IDataSet expectedDataSet = getDataSet(THREE_ACTORS_SAME_NAME_DIFF_BIRTHDATES);
		DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, expectedDataSet);
		
		Collection<Actor> actors = actorDAO.getActors(HARRISON_FORD);
		Assert.assertTrue("there are three Harrison Ford actors with different birthdates",
							actors.size() == 3);	
		for (Actor actor: actors) {
			Assert.assertEquals("name for three actors should be Harrison Ford",
								HARRISON_FORD, actor.getName());
		}
		compareStoredActorsAgainst(expectedDataSet);
	}

	@Test
	public void testGetActorsWithNullInput() throws Exception {

		IDataSet expectedDataSet = getDataSet(THREE_ACTORS_SAME_NAME_DIFF_BIRTHDATES);
		DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, expectedDataSet);
		
		Collection<Actor> actors = actorDAO.getActors(null);
		Assert.assertTrue("getActors(null) should lead to zero results",actors.size() == 0);
		compareStoredActorsAgainst(expectedDataSet);
	}

	@Test
	public void testGetActorsWithEmptyStringInput() throws Exception {
		IDataSet expectedDataSet = getDataSet(THREE_ACTORS_SAME_NAME_DIFF_BIRTHDATES);
		DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, expectedDataSet);
		
		Collection<Actor> actors = actorDAO.getActors("");
		Assert.assertTrue("getActors(null) should lead to zero results",actors.size() == 0);
		compareStoredActorsAgainst(expectedDataSet);
	}
	// test get actor (name) with name=null or name=''
	
	@Test
	public void testGetActorsWithRealNameButEmptyTable() throws Exception {
		IDataSet expectedDataSet = getDataSet(THREE_ACTORS_SAME_NAME_DIFF_BIRTHDATES);
		DatabaseOperation.DELETE_ALL.execute(dbUnitConnection, expectedDataSet);
		
		Collection<Actor> actors = actorDAO.getActors("Harrison Ford");
		Assert.assertTrue("getActors(name) from empty actor table should lead to zero results",actors.size() == 0);
	}
}
