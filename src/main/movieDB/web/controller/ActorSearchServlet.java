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
import movieDB.db.FactoryNotFoundException;

public class ActorSearchServlet extends movieDBServlet {

	public void init() {
		super.setLogger(Logger.getLogger(ActorSearchServlet.class.getName()));
		setDAOFactoryUsingServletContext();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
				throws IOException {
		
		//exit method if no database factory could be found, error message already logged
		if (! DAOFactoryWasSet()) return;

		setHTMLContentType(response);
		PrintWriter out = getWriterFrom(response);		
		
		String actorName = request.getParameter("actorName");
		String actorID = request.getParameter("actorID");
		
		try {		
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
	
	// TODO: break this up into two separate methods that can be reused
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
}
