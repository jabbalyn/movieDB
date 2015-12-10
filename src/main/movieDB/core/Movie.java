package movieDB.core;

import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import java.util.List;

public class Movie {

	String name;
	Calendar releaseDate = null;
	Collection<Director> directors = new Vector<Director>();
	Genre genres = new Genre();
	Cast cast = new Cast();
	
	public Movie() {
		this("");
	}
	
	public Movie(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Calendar getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Calendar date) {
		this.releaseDate = date;
	}
	
	public Collection<Director> getDirectors() {
		return directors;
	}
	
	public void addDirectors(Collection<Director> directors) {
		if (directors == null) return;
		for (Director director: directors) {
			if (! this.directors.contains(director)) this.directors.add(director);
		}
	}
	
	public Genre getGenres() {
		return genres;
	}
	
	public Cast getCast() {
		return cast;
	}

	public boolean equals(Movie movie) {
		return getName().toLowerCase().equals(movie.getName().toLowerCase());
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Movie other = (Movie) obj;
		return this.equals(other);
	}
}
