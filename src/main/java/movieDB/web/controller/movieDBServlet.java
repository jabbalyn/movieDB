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

public abstract class movieDBServlet extends HttpServlet {

	protected Logger logger;
	protected DAOFactory daoFactory = null;
	private boolean wasDAOFactorySet = false;
	
	protected void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	protected boolean DAOFactoryWasSet() {
		return wasDAOFactorySet;
	}
	
	public abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	protected void setHTMLContentType(HttpServletResponse response) {
		response.setContentType("text/html");
	}
	
	protected PrintWriter getWriterFrom(HttpServletResponse response) throws IOException {
		return response.getWriter();
	}
	
	protected boolean setDAOFactoryUsingServletContext() {
		 //TODO: add Cactus in-container testing to make sure the DAOFactory was initialized correctly before
		 // passing it on to other
	
		daoFactory = (DAOFactory) getServletConfig().getServletContext().getAttribute("movieDatabase");
		if (daoFactory == null) {
			wasDAOFactorySet = false;
			logger.log(Level.SEVERE, this.getClass().getName() + "could not find DAOFactory in ServletContext");
			return false;
		}
		wasDAOFactorySet = true;
		return true;
	}
}
