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

public class ActorDAOJdbcImpl implements ActorDAO {

	private Connection conn = null;
	private Statement stmt = null;
	
	public ActorDAOJdbcImpl(Connection conn) {
		setConnection(conn);
	}
	
	private void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	private Statement createDefaultStatement(Connection connection) throws SQLException {
		// TODO: move the stmt creation to an open() method; add a close() method to close and release
		// the conn and stmt objects
		return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_UPDATABLE);
	}
	
	private void close(Statement stmt, ResultSet rs) throws SQLException {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
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
