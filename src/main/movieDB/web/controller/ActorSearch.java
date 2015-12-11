package movieDB.web.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.sql.Date;

import java.util.Vector;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.logging.Level;

import movieDB.core.Actor;
import movieDB.db.ActorDAO;
import movieDB.db.UtilsSQL;
import movieDB.web.model.ActorExpert;
import movieDB.db.DAOFactory;

public class ActorSearch extends HttpServlet {

	private static final Logger logger = Logger.getLogger(ActorSearch.class.getName());
	private DAOFactory daoFactory = null;
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
				throws IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String actorName = request.getParameter("actorName");
		String actorID = request.getParameter("actorID");
		
		try {
			//if DAO null,  exit from this method since won't be able to access the database 
			if (getDAOFactoryFromServletContext() == null) throw new IOException("DAOFactory not found in context");
			 
			Collection<Actor> actors = getActorResults(actorName, actorID);
			request.setAttribute("actors", actors);
			RequestDispatcher view = request.getRequestDispatcher("actorSearch.jsp");
			view.forward(request, response);
			
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "EXCEPTION while doing actorExpert.getActorsWithName():", ex);
			ex.printStackTrace();
			// TODO: is printing stack trace the best solution here?
		}
	}
	
	private Collection<Actor> getActorResults(String actorName, String actorID) throws Exception {
		Collection<Actor> actors = new Vector<Actor>();
		if ((actorName != null) && (! "".equals(actorName))) {
		 	actors.addAll(new ActorExpert(daoFactory.createActorDAO()).getActorsWithName(actorName));
		} 
		else if ((actorID != null) && (! "".equals(actorID))) {
			Actor actor = (new ActorExpert(daoFactory.createActorDAO())).getActorById((long) Integer.parseInt(actorID));
			if (actor != null) actors.add(actor);
		}	
		return actors;
	}

	private DAOFactory getDAOFactoryFromServletContext() {
			 //TODO: add Cactus in-container testing to make sure the DAOFactory was initialized correctly before
			 // passing it on to other
		
			daoFactory = (DAOFactory) getServletConfig().getServletContext().getAttribute("movieDatabase");
			if (daoFactory == null) {
				logger.log(Level.SEVERE, "ActorSearch servlet couldn't get reference to daoFactory");
			}
			return daoFactory;
	}
}
