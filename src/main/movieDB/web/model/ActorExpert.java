package movieDB.web.model;

import java.sql.SQLException;
import java.util.Collection;

import movieDB.db.DAOFactory;
import movieDB.db.JDBCDAOFactory;
import movieDB.db.ActorDAO;
import movieDB.db.NotInDataStoreException;
import movieDB.db.FailedUpdateException;

import movieDB.core.Actor;

public class ActorExpert {

	private ActorDAO actorDAO = null;
	
	// constructor throws NullPointerException if actorDAO is null
	public ActorExpert(ActorDAO actorDAO) {
		if (actorDAO == null) throw new NullPointerException();
		else setActorDAO(actorDAO);
	}
	
	private void setActorDAO(ActorDAO actorDAO) {
		this.actorDAO = actorDAO;
	}
	
	public ActorDAO getActorDAO() {
		return actorDAO;
	}
	
	public Collection<Actor> getActorsWithName(String name) throws Exception {
		return actorDAO.getActors(name);
	}
	
	public Actor getActorById(long id) throws SQLException {
		return actorDAO.getActorById(id);
	}
	
	// TODO: remove references to SQLException, replace with something generic and non-SQL specific

	public void persist(Actor actor) throws NotInDataStoreException, FailedUpdateException, SQLException {
		actorDAO.persist(actor);
	}
	
}
