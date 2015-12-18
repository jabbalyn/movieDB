package movieDB.db;

import java.sql.Connection;
import java.sql.SQLException;

import movieDB.core.Actor;
import movieDB.core.DuplicateException;

import java.util.Collection;

public interface ActorDAO {

	// TODO: clean up interface and classes so that there's no reference to SQLException (make DAO's generic
	// to include non-SQL datastores). Could call it StorageException?
	
	public long addActor(Actor actor) throws SQLException, DuplicateException;
	
	// returns null if no actor was found
	public Actor getActorById(long id) throws SQLException;
	
	public Collection<Actor> getActors(String name) throws SQLException;
	
	// persists the information in the Actor object, but only if the ID in the object matches the ID of an Actor already stored 
	// in the database / datastore. 
	//
	// throws NotInDataStoreException if actor passed in is null, or if the actor's ID doesn't match the ID of any actor 
	// object in the data store.
	// throws FailedUpdateException if the attempted transaction was rolled back and no update was made
	// throws SQLException if there were any SQL-related errors during the operation.
	
	public void persist(Actor actor) throws NotInDataStoreException, FailedUpdateException, SQLException;
}
