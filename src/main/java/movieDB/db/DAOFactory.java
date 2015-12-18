package movieDB.db;

public interface DAOFactory {

	// runs any operations that must be completed before deleting this object
	public void close() throws Exception;
	
	// throws Exception if there was an error while creating the ActorDAO
	public ActorDAO createActorDAO() throws Exception;
	
	//public ActorDAO getActorDAO();
	
	/*
	public void createDirectorDAO();
	
	public DirectorDAO getDirectorDAO();
	
	public void createMovieDAO();
	
	public MovieDAO getMovieDAO();
	
	public void createGenreDAO();
	
	public GenreDAO getGenreDAO();
	
	public void createRoleDAO();
	
	public RoleDAO() getRoleDAO();
	*/
	
}
