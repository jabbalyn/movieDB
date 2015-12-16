package movieDB.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;

import movieDB.core.Actor;
import movieDB.core.DuplicateException;
import movieDB.db.UtilsSQL;
import movieDB.db.NotInDataStoreException;
import movieDB.db.FailedUpdateException;

public class ActorDAOJdbcImpl extends GenericDAOJdbcImpl implements ActorDAO {

	public ActorDAOJdbcImpl(Connection conn) {
		super(conn);
	}
	
	// method to double check whether an actor with the same attributes (i.e. name, birthdate, etc) has 
	// already been added, irrespective of the actorID associated with it
	private boolean databaseHasActor(Actor actor) throws SQLException {
		
		if (actor == null) return false;
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = createDefaultStatement(conn);
			rs = stmt.executeQuery("SELECT * FROM ACTOR WHERE NAME='" + actor.getName() 
								+ "' AND BIRTHDATE='" + UtilsSQL.getSQLDateFromCalendar(actor.getBirthDate()) + "'");
			if (rs.next()) return true;
			else return false;
		} finally {
			close(stmt, rs);
		}
	}

	// persists the information in the Actor object, but only if the ID in the passed-in object matches the ID  
	// of an Actor already stored in the database / datastore. 
	//
	// throws NotInDataStoreException if actor passed in is null, or if the actor's ID doesn't match the ID of any actor 
	// object in the data store.
	// throws FailedUpdateException if the transaction was attempted but rolled back, and and no update was made
	// throws SQLException if there were any SQL-related errors during the operation.

	public void persist(Actor actor) throws NotInDataStoreException, FailedUpdateException, SQLException {
		
		if (actor == null) throw new NotInDataStoreException("database cannot have a null actor");
		Statement stmt = null;
		ResultSet rs = null; 
		
		// must do two-part transaction: first, get the actor's information from the database, then do
		// a 2nd call to update the record. Therefore, we must turn off auto-commit, make the changes, commit
		// them, and then turn auto-commit back on.

		try {
			stmt = createUpdatableStatement(conn);
			conn.setAutoCommit(false);	
			
			rs = stmt.executeQuery("SELECT ACTORID, NAME, BIRTHDATE FROM ACTOR WHERE actorID= " + actor.getID());			
			if (! rs.isBeforeFirst()) throw new NotInDataStoreException("no actor with ID " + actor.getID()
											+ "could be found in the data store");
			
			// actor's ID exists in the database, so we can now update the actor's information
			rs.next();
			rs.updateString(2, actor.getName());
			rs.updateString(3, UtilsSQL.getSQLDateFromCalendar(actor.getBirthDate()));
			rs.updateRow();
			conn.commit();
			
		} catch(SQLException ex) {
			try {
				conn.rollback();
				throw new FailedUpdateException(ex);
			} catch (SQLException ex2) {
				throw ex2;
			}
		} finally {
			conn.setAutoCommit(true);
			close(stmt, rs);
		}
	}
	
	// inserts actor and, if successful, will automatically set the actor's ID to the ID used in storage.
	// Returns the actor's new ID if the actor was added; returns -1 if the actor was not added
	// or if actor was null
	public long addActor(Actor actor) throws SQLException, DuplicateException {
		
		if (actor == null) return -1;
		if (databaseHasActor(actor)) {
			throw new DuplicateException("adding actor failed: DB already has actor with identical attributes (name, birthdate, etc)");
		}	
		String birthString = UtilsSQL.getSQLDateFromCalendar(actor.getBirthDate());
		
		Statement stmt = null; 
		ResultSet rs = null;	
		try {
			stmt = createDefaultStatement(conn);
			int insertedRowsCount = stmt.executeUpdate(
					"INSERT INTO ACTOR (NAME, BIRTHDATE) VALUES ('" + actor.getName() + "','" + birthString + "')",
					Statement.RETURN_GENERATED_KEYS);
		    
			if (insertedRowsCount == 0) throw new SQLException("actor:" + actor.getName() + ", birth=" 
													+ birthString + " couldn't be inserted into DB");
			if (insertedRowsCount > 1) throw new SQLException("More than one identical actor was inserted into the DB");
			if (insertedRowsCount == 1) {
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					long returnedID = rs.getLong(1);
					actor.setID(returnedID);
					return returnedID;
				} else throw new SQLException("Could not get returned ID for inserted actor:" + actor.getName() 
											+ ", birth=" + birthString);
			}
			return -1;
		} finally {
			close(stmt, rs);
		}
	}

	// returns null if no entry was found with the passed id
	public Actor getActorById(long id) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null; 
		try {
			stmt = createDefaultStatement(conn);
			rs = stmt.executeQuery("SELECT ACTORID, NAME, BIRTHDATE FROM ACTOR WHERE actorID= " + id);
			if (rs == null) return null;
			return readNextActorWithIDFrom(rs);
		} finally {
			close(stmt, rs);
		}

	}
	
	public Collection<Actor> getActors(String name) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Collection<Actor> actors = new Vector<Actor>();
			if ((name == null) || ("".equals(name))) return actors;
			
			stmt = createDefaultStatement(conn);
			rs = stmt.executeQuery("SELECT ACTORID, NAME, BIRTHDATE FROM ACTOR WHERE NAME='" + name + "'");
			
			if (! rs.isBeforeFirst()) return actors;
			while (! rs.isLast()) {
				actors.add(readNextActorWithIDFrom(rs));
			}
			return actors;
		} finally {
			close(stmt, rs);
		}
	}
	
	private static Actor readNextActorWithIDFrom(ResultSet rs) throws SQLException {
		if (! rs.next()) return null;
		
		long storedID = rs.getLong(1);
		String storedName = rs.getString(2);
		Date birthdate = rs.getDate(3);

		Actor foundActor = new Actor(storedName, UtilsSQL.getCalendarFromSQLDate(birthdate));
		foundActor.setID(storedID);
		return foundActor;
	}
	

}
