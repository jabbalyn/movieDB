package movieDB.core;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

// the Genre class keeps track of both "Standard" genres and movie-specific Genres. 
//
// "Standard" genres are typical genres such as "Action", "Adventure", "Comedy" and "Drama" that are 
// pre-defined at construction time and specified through the Genre class constructor.  
//
// "Movie" genres are specific to a particular movie, for example, Star Wars
// might have the genres "Action", "Adventure", and "Fantasy".

public class Genre {
	
	// a list of all STANDARD movie genres. A genre may be composed of multiple words
	// (i.e. "Science Fiction"). The first letter of each word is capitalized and the rest 
	// of the letters in each word will be lowercase.
	private static SortedSet<String> standardGenres = new TreeSet<String>();
	
	// a list of all genres specific to a particular movie. For example, the movie Star Wars
	// may have these genres: "Action", "Drama", and "SCience Fiction".
	private Collection<String> movieGenres = new Vector<String>();
	
	public Genre(SortedSet<String> standards) {
		standardGenres = standards;
	}
	
	public Genre() {
		
	}

	public static int getNumOfStandards() {
		return standardGenres.size();
	}
	
	// tests whether a particular string has been defined as a Standard genre
	public static boolean isStandard(String genre) {
		if (isStringEmptyOrNull(genre)) return false;
		return standardGenres.contains(genre);
	}
	
	public static void addStandardGenre(String standard) {
		if (isStringEmptyOrNull(standard)) return;
		if (! standardGenres.contains(standard)) standardGenres.add(standard);
	}
	
	public static void removeStandardGenre(String standard) {
		if (isStringEmptyOrNull(standard)) return;
		if (standardGenres.contains(standard)) standardGenres.remove(standard);
	}	

	
	public boolean hasGenre(String genreID) {
		// an empty movieGenres doesn't have any genres
		if (movieGenres.isEmpty())
			return false;
		if (isStringEmptyOrNull(genreID)) 
			return false;
		else 
			return movieGenres.contains(genreID);
	}
	
	public void addGenre(String genre) {
		// if genre is null or an empty string, ignore it and don't add to genres
		if (isStringEmptyOrNull(genre)) return;
		if (! movieGenres.contains(genre)) movieGenres.add(genre);
	}
	
	public int size() {
		return movieGenres.size();
	}
	
	private static boolean isStringEmptyOrNull(String s) {
		return ((s == null) || ("".equals(s)));
	}
	
}
