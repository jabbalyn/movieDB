package movieDB.core;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;

import org.junit.Test;
import org.junit.Before;

public class MovieTest {
	
	Movie starWars;
	
	@Before
	public void initialize() {
		starWars = new Movie("Star Wars");
		Calendar releaseDate = Calendar.getInstance();
		releaseDate.set(1977, Calendar.MAY, 25);
		starWars.setReleaseDate(releaseDate);
		
		Collection<Director> directors = new Vector<Director>();
		directors.add(new Director("George Lucas"));
		starWars.addDirectors(directors);
	}
	
	@Test
	public void testNameForMovieConstructorNoParameter() {
		Movie movie = new Movie();
		assertEquals("Movie's no-param Constructor should have empty-string name", "", movie.getName());
	}
	
	@Test
	public void testNameForMovieConstructorWithNameParameter() {
		String name = "Star Wars";
		assertEquals("movie's name should match name for constructor", name, starWars.getName());
	}
	
	@Test
	public void tesGetReleaseDateOntMovieWithNoReleaseDate() {
		Movie movie = new Movie();
		Calendar releaseDate = movie.getReleaseDate();
		assertNull("release date should be null for movie with no release date", releaseDate);
	}
	
	@Test
	public void testSetReleaseDate() {
		Calendar releaseDate = Calendar.getInstance();
		releaseDate.set(1977, Calendar.MAY, 25);
		
		Movie movie = new Movie("Star Wars");
		movie.setReleaseDate(releaseDate);
		
		Calendar SWreleased = movie.getReleaseDate();
		assertEquals(SWreleased, releaseDate);
		assertEquals("Star Wars release year must be 1977", SWreleased.get(Calendar.YEAR), 1977);
		assertEquals("Birthdate Month should be the same", SWreleased.get(Calendar.MONTH), Calendar.MAY);
		assertEquals("Birthdate Year should be the same", SWreleased.get(Calendar.DAY_OF_MONTH), 25);
	}
	
	@Test
	public void testGetMovieDirectorOnMovieWithNoDirectorSet() {
		Movie movie = new Movie();
		Collection<Director> directors = movie.getDirectors();
		assertTrue("Directors should be empty for movie with no director", directors.size() == 0);
		assertNotNull("Star Wars directors should not be null (was already set)", directors);
	}
	
	@Test
	public void testGetMovieDirectorOnMovieWithDirectorSet() {
		
		Collection<Director> directors = starWars.getDirectors();
		assertTrue("Star Wars should have one director", directors.size() == 1);
		
		Iterator<Director> iter = directors.iterator();
		assertTrue("Director iterator should have at least one element", iter.hasNext());
		
		Director firstDirector = iter.next();
		assertEquals("Star Wars director should be George Lucas", "George Lucas", firstDirector.getName());
		assertFalse("There should be no more Star Wars directors", iter.hasNext());
	}
	
	@Test
	public void testSetDirectorNameAfterAlreadySet() {
		
		Director stevenSpielberg = new Director("Steven Spielberg");
		Collection<Director> newDirectors = new Vector<Director>();
		newDirectors.add(stevenSpielberg);
		starWars.addDirectors(newDirectors);
		
		Collection<Director> foundDirectors = starWars.getDirectors();
		assertTrue("star wars should now have two directors", foundDirectors.size() == 2);
		assertTrue("George Lucas should be a director of Star Wars", 
				foundDirectors.contains(new Director("George Lucas")));
		assertTrue("Steven Spielberg should also be a Star Wars director (not really)", 
				foundDirectors.contains(stevenSpielberg));
	}
	
	@Test
	public void testAddDirectorsWithNullInputWhenNoDirectorsWereSet() {
		Collection<Director> newDirectors = null;
		Movie movie = new Movie();
		movie.addDirectors(null);
		Collection<Director> foundDirectors = movie.getDirectors();
		assertTrue("Null input to addDirectors() is ignored", foundDirectors.size() == 0);
	}
	
	@Test
	public void testAddDirectorsWithNullInputWhenOneDirectorWasSet() {
		Collection<Director> newDirectors = null;
		starWars.addDirectors(newDirectors);
		Collection<Director> foundDirectors = starWars.getDirectors();
		assertTrue("Null input to addDirectors() is ignored even when director was set", 
				foundDirectors.size() == 1);
	}
	
	@Test
	public void testAddOneGenreForOneGenre() {
		starWars.getGenres().addGenre("Action");
		assertTrue("Star Wars should be in Action genre", starWars.getGenres().hasGenre("Action"));
	}

	@Test
	public void testAddOneGenreWithDuplicate() {
		starWars.getGenres().addGenre("Action");
		starWars.getGenres().addGenre("Action");
		assertTrue("Star Wars should be in Action genre", starWars.getGenres().hasGenre("Action"));
	}
	
	@Test
	public void testAddGenreForTwoGenres() {
		starWars.getGenres().addGenre("Action");
		starWars.getGenres().addGenre("Adventure");
		assertTrue("Star Wars should be in Action genre", starWars.getGenres().hasGenre("Action"));
		assertTrue("Star Wars should be in Action genre", starWars.getGenres().hasGenre("Adventure"));
	}
	
	@Test
	public void testSetOneMovieGenreWithDuplicate() {
		starWars.getGenres().addGenre("Action");
		starWars.getGenres().addGenre("Action");
		assertTrue("Star Wars should be in Action genre", starWars.getGenres().hasGenre("Action"));
	}
	
	@Test
	public void testEqualsForSameMovieWithDifferentCapitalizationUsingAssertTrue() {
		Movie movie1 = new Movie("Star Wars"); // first letter of each word capitalized
		Movie movie2 = new Movie("star wars"); // first letter of each word NOT capitalized
		assertTrue("both movies should be equal to despite different capitalization", movie1.equals(movie2));
	}
	
	@Test
	public void testEqualsForSameMovieWithDifferentCapitalizationUsingAssertEquals() {
		Movie movie1 = new Movie("Star Wars"); // first letter of each word capitalized
		Movie movie2 = new Movie("star wars"); // first letter of each word NOT capitalized
		assertEquals("both movies should be equal to despite different capitalization", movie1, movie2);
	}
}
