package movieDB.core;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import org.junit.Before;

public class ActorTest {

	@Test
	public void testgetNameForActorConstructorWithNoName() {
		Actor actor = new Actor();
		assertEquals("", actor.getName());
	}
	
	@Test
	public void testgetNameForActorConstructorWithName() {
		String name = "Harrison Ford";
		Actor actor = new Actor(name);
		assertEquals(actor.getName(), name);
	}
	
	@Test
	public void testConstructorWith_Name_Birthdate_ButCheckID() {
		String name = "Judi Dench";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1934);
		cal.set(Calendar.MONTH, 12);
		cal.set(Calendar.DAY_OF_MONTH, 9);
		Actor actor = new Actor(name, cal);
		assertTrue("id for 3-param constructor should be the same", actor.getID() == -1);
		assertEquals("name for 3-param constructor should be the same", name, actor.getName());
		assertEquals("birthdate for 3-param constructor should be the same", cal, actor.getBirthDate());
	}
	
	
	@Test
	public void testEqualsSameNamesUsingAssertTrue() {
		
		String sameName = "Sam";
		Actor a1 = new Actor(sameName);
		Actor a2 = new Actor(sameName);
		assertTrue("both actors should be equal since they have the same name", a1.equals(a2));
	}

	@Test
	public void testEqualsSameNamesUsingAssertEquals() {
		
		String sameName = "Sam";
		Actor a1 = new Actor(sameName);
		Actor a2 = new Actor(sameName);
		assertEquals("both actors should be equal since they have the same name", a1, a2);
	}
	
	@Test
	public void testEqualsSameNamesWithCapitalizationDifference() {
		Actor a1 = new Actor("Sam");
		Actor a2 = new Actor("sam");
		assertTrue("two actors with the same name should be identical despite capitalization diffs", 
				a1.equals(a2));
	}
	
	@Test
	public void testEqualsSameNamesWithCapitalizationDifferenceTrial2() {
		Actor a1 = new Actor("Sam");
		Actor a2 = new Actor("saM");
		assertTrue("two actors with the same name should be identical despite capitalization diffs", 
				a1.equals(a2));
	}
	
}
