package movieDB.core;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.Iterator;

public class CastTest {

	Cast cast;
	
	@Before
	public void initialize() {
		cast = new Cast();
	}
	
	
	@Test
	public void testGetNumberofRolesInEmptyCast() {
		assertTrue("non-initialized cast has zero roles", cast.numberOfRoles() == 0);
	}
	
	@Test
	public void testAddOneRoleInEmptyCast() throws DuplicateException {
		Actor newActor = new Actor("Mark Hamill");
		String character = "Luke Skywalker";
		Role newRole = new Role(newActor, character);
		cast.addRole(newRole);
		assertTrue("non-initialized cast has zero roles", cast.numberOfRoles() == 1);
	}
	
	@Test
	public void testAddNewRoleToEmptyCast() throws DuplicateException {
		cast.addRole(new Role(new Actor("Mark Hamill"), "Luke Skywalker"));
		assertTrue("should have added one role to non-initialized cast", cast.numberOfRoles() == 1);
	}
	
	@Test
	public void testAddTwoNewRolesToEmptyCast() throws DuplicateException {
		cast.addRole(new Role(new Actor("Mark Hamill"), "Luke Skywalker"));
		cast.addRole(new Role(new Actor("Harrison Ford"), "Han Solo"));
		assertTrue("adding two roles to non-initialized cast increases roles to two", 
				cast.numberOfRoles() == 2);
	}
	
	@Test
	public void testAddRoleWithActorPlayingMultipleCharacters() throws DuplicateException {
		cast.addRole(new Role(new Actor("Mark Hamill"), "Young Luke Skywalker"));
		cast.addRole(new Role(new Actor("Mark Hamill"), "Old Luke Skywalker"));
		assertTrue("adding two roles to non-initialized cast increases roles to two", 
				cast.numberOfRoles() == 2);
	}

	@Test(expected=DuplicateException.class)
	public void testAddRoleWithMultipleActorsPlayingSameCharacter() throws DuplicateException {
		cast.addRole(new Role(new Actor("Mark Hamill"), "Mr. Pebbles"));
		cast.addRole(new Role(new Actor("Mark Hamill"), "Mr. Pebbles"));
		assertTrue("adding two exact-same roles to non-initialized cast increases roles by one", 
				cast.numberOfRoles() == 1);
	}
	
	@Test(expected=DuplicateException.class)
	public void testAddRoleWithMultipleActorsPlayingSameCharacterTrial2() throws DuplicateException {
		cast.addRole(new Role(new Actor("Mark Hamill"), "Mr. Pebbles"));
		cast.addRole(new Role(new Actor("Harrison Ford"), "Han Solo"));
		cast.addRole(new Role(new Actor("Mark Hamill"), "Mr. Pebbles"));
		assertTrue("adding two exact-same roles to non-initialized cast increases roles by one", 
				cast.numberOfRoles() == 2);
	}
	
	@Test
	public void testRoleCheckInCastOfTwoActorsUsingIterator() throws DuplicateException {
		Role role1 = new Role(new Actor("Mark Hamill"), "Mr. Pebbles");
		Role role2 = new Role(new Actor("Harrison Ford"), "Han Solo");
		cast.addRole(role1);
		cast.addRole(role2);
		Iterator<Role> iter = cast.getRoles();
		
		assertTrue("initial iterator has at least one element", iter.hasNext());
		Role iter1 = iter.next();
		assertEquals("1st element in iterator should equal the 1st role added to cast", role1, iter1);
		
		assertTrue("iterator should have at least a 2nd element", iter.hasNext());
		Role iter2 = iter.next();
		assertEquals("2nd element in iterator should equal the 2nd role added to cast", role2, iter2);
		
		assertFalse("iterator should NOT have any more elements", iter.hasNext());	
	}

}
