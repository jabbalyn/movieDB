package movieDB.db;

import java.sql.Connection;
import java.sql.SQLException;

import movieDB.core.Actor;
import movieDB.core.DuplicateException;

import java.util.Collection;

public interface ActorDAO {

	public long addActor(Actor actor) throws SQLException, DuplicateException;
	
	// returns null if no actor was found
	public Actor getActorById(long id) throws SQLException;
	
	public Collection<Actor> getActors(String name) throws SQLException;
	
	// public void persist() throws SQLException;
}
