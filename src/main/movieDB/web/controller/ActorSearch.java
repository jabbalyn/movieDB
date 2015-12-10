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
			 //TODO: add Cactus in-container testing to make sure the DAOFactory was initialized correctly before
			 // passing it on to others
			daoFactory = (DAOFactory) getServletConfig().getServletContext().getAttribute("movieDatabase");
		 } catch (Exception ex) {
			 logger.log(Level.SEVERE, "ActorSearch servlet couldn't get reference to daoFactory", ex);
			// return from servlet's doPost() method since won't be able to access the database
			 return; 	
		 }
		 Collection<Actor> actors = new Vector<Actor>();
		 try {
			if ((actorName != null) && (! "".equals(actorName))) {
			 	actors.addAll(new ActorExpert(daoFactory.createActorDAO()).getActorsWithName(actorName));
			} 
			else if ((actorID != null) && (! "".equals(actorID))) {
				Actor actor = (new ActorExpert(daoFactory.createActorDAO())).getActorById((long) Integer.parseInt(actorID));
				if (actor != null) actors.add(actor);
			}
			
			request.setAttribute("actors", actors);
			RequestDispatcher view = request.getRequestDispatcher("actorSearch.jsp");
			view.forward(request, response);
			
			/**
			Iterator<Actor> iter = actors.iterator();
			if (! iter.hasNext()) {
				out.println("<br>No actors were found with that name.<br>");
			}
			while (iter.hasNext()) {
				Actor nextActor = iter.next();
				Calendar cal = nextActor.getBirthDate();
				
				out.println("<td>" + nextActor.getName() + "</td>");
				out.println("<td>" + nextActor.getID() + "</td>");
				out.println("<td>" + UtilsSQL.getHumanDate_DefaultStyle(cal) + "</td>");
				out.println("</tr>");
			}
			*/
			
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "EXCEPTION while doing actorExpert.getActorsWithName():", ex);
			ex.printStackTrace();
		}
	}
}
