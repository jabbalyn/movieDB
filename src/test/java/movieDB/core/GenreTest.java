package movieDB.core;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.SortedSet;
import java.util.TreeSet;

public class GenreTest {

	Genre standardGenres;
	Genre movieGenres;
	
	@Before
	public void instantiate() {
		
		// setup the "Standard" genres
		SortedSet<String> standards = new TreeSet<String>();
		standards.add("Action");
		standards.add("Adventure");
		standards.add("Fantasy");
		standards.add("Science Fiction");
		
		// setup the genres specific to this movie
		movieGenres = new Genre(standards);
	}
	
	@Test
	public void testisStandardForValidActionGenreUsingString() {
		assertTrue("Action genre should be valid", Genre.isStandard("Action"));
	}

	@Test
	public void testisStandardForValidActionGenreUsingClassConstant() {
		assertTrue("Action genre was defined as a Genre constant", Genre.isStandard("Action"));
	}
	
	@Test
	public void testisStandardForInvalidGenre() {
		assertFalse("XYZ Genre is not defined as a Genre constant", Genre.isStandard("XYZ"));
	}
	
	@Test
	public void testisStandardForNullGenre() {
		assertFalse("Null isn't considered a defined genre", Genre.isStandard(null));
	}

	@Test
	public void testisStandardForEmptyStringInput() {
		assertFalse("Empty string isn't considered a defined genre", Genre.isStandard(""));
	}
	
	@Test
	public void testisStandardForAdventureGenre() {
		assertTrue("Adventure is a valid genre", Genre.isStandard("Adventure"));
	}
	
	@Test
	public void testisStandardForFantasyGenre() {
		assertTrue("Fantasy is a valid genre", Genre.isStandard("Fantasy"));		
	}

	@Test
	public void testaddGenreWithNullString() {
		String s = null;
		int startSize = movieGenres.size();
		movieGenres.addGenre(s);
		assertTrue("initial genre addition of empty string should be ignored",
				movieGenres.size() == startSize); 
	}
	
	@Test
	public void testaddGenreWithEmptyString() {
		int startSize = movieGenres.size();
		movieGenres.addGenre("");
		assertTrue("initial genre addition of empty string should be ignored",
				movieGenres.size() == startSize); 
	}
	
	@Test
	public void testMovieGenreSizeAfterAddingDuplicate() {
		movieGenres.addGenre("Action");
		assertTrue("there should be only one movie genre before initial addition",
				movieGenres.size() == 1);
		movieGenres.addGenre("Action");
		assertTrue("there should be only one movie genre after initial addition",
				movieGenres.size() == 1);		
	}
	
	@Test
	public void testaddGenreNotPreviouslyDefinedWithClassConstant() {
		int startSize = movieGenres.size();
		movieGenres.addGenre("NeverBeforeUsedGenre"); 
		assertTrue("we're allowed to add a genre not defined by Genre class",
				movieGenres.size() == startSize + 1); 
	}

	@Test
	public void testHasGenreWithNullStringParameter() {
		String s = null;
		int startSize = movieGenres.size();		
		movieGenres.hasGenre(s);
		assertTrue("Genre class should already have Action genre", 
				movieGenres.size() == startSize);
	}
	
	@Test
	public void testGetNumOfStandards() {
		assertTrue("Number of standards added during initialization is 4", Genre.getNumOfStandards() == 4);
	}
	
	@Test
	public void testAddStandardGenre() {
		int numOfStandards = Genre.getNumOfStandards();
		assertFalse("Should see Drama as a newly defined standard genre", Genre.isStandard("Drama"));
		Genre.addStandardGenre("Drama");
		assertTrue("Should see Drama as a newly defined standard genre", Genre.isStandard("Drama"));
		assertTrue("after adding Drama, number of standards stored should increase by 1", 
				Genre.getNumOfStandards() == numOfStandards + 1);
	}

	@Test
	public void testAddStandardGenreWithNullInput() {
		int numOfStandards = Genre.getNumOfStandards();
		Genre.addStandardGenre(null);
		assertFalse("Null Should NOT be added as a new standard genre", Genre.isStandard(null));
		assertTrue("after adding Drama, number of standards stored should increase by 1", 
				Genre.getNumOfStandards() == numOfStandards);
	}

	@Test
	public void testAddStandardGenreWithEmptyStringInput() {
		int numOfStandards = Genre.getNumOfStandards();
		Genre.addStandardGenre(null);
		assertFalse("Empty String Should NOT be added as a new standard genre", Genre.isStandard(""));
		assertTrue("addStandardGenre with Empty String should fail", 
				Genre.getNumOfStandards() == numOfStandards);
	}
	
	@Test
	public void testAddStandardGenreTwice() {
		int numOfStandards = Genre.getNumOfStandards();
		assertFalse("Should see Drama as a newly defined standard genre", Genre.isStandard("Drama"));
		Genre.addStandardGenre("Drama");
		assertTrue("Should see Drama as a newly defined standard genre", Genre.isStandard("Drama"));
		Genre.addStandardGenre("Drama");
		assertTrue("Should see Drama as a newly defined standard genre", Genre.isStandard("Drama"));
		assertTrue("adding Drama twice should result in only one addition", 
				Genre.getNumOfStandards() == numOfStandards + 1);
	}
	
	@Test
	public void testRemoveStandardGenre() {
		int numOfStandards = Genre.getNumOfStandards();
		Genre.addStandardGenre("Drama");
		assertTrue("Should see Drama as a newly defined standard genre", Genre.isStandard("Drama"));
		assertTrue("adding Drama should increase standards by 1", 
				Genre.getNumOfStandards() == numOfStandards + 1);
		
		numOfStandards = Genre.getNumOfStandards();
		Genre.removeStandardGenre("Drama");
		assertFalse("Should see Drama as a newly defined standard genre", Genre.isStandard("Drama"));
		assertTrue("removing Drama should result in standards being reduced by 1", 
				Genre.getNumOfStandards() == numOfStandards - 1);
	}
	
	@Test
	public void testRemoveStandardGenreWithNullInput() {
		int numOfStandards = Genre.getNumOfStandards();
		Genre.removeStandardGenre(null);
		assertTrue("trying to remove NULL from standards should leave standards' size the same", 
				Genre.getNumOfStandards() == numOfStandards);
	}
	
	@Test
	public void testRemoveStandardGenreWithEmptyStringInput() {
		int numOfStandards = Genre.getNumOfStandards();
		Genre.removeStandardGenre("");
		assertTrue("trying to remove Empty String from standards should leave standards' size the same", 
				Genre.getNumOfStandards() == numOfStandards);
	}
}
