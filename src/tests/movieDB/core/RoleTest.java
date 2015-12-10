package movieDB.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class RoleTest {

	@Test
	public void testRoleCreationWithNullParameters() {
		Actor actor = null;
		String character = null;
		Role role1 = new Role(actor, character);
		
		assertNull("actor should be null", role1.getActor());
		assertNull("character should be null", role1.getCharacter());
	}
	
	@Test
	public void testGetActor() {
		String actorName = "George Clooney";
		Role role1 = new Role(new Actor(actorName), "Sam Nobody");
		Actor actor = role1.getActor();
		assertEquals("actor name should match what was passed in", actorName, actor.getName());
	}
	
	@Test
	public void testGetCharacter() {
		String actorName = "George Clooney";
		String character = "Sam Nobody";
		Role role1 = new Role(new Actor(actorName), character);
		assertEquals("character name should match what was passed in", character, role1.getCharacter());
	}
	
	@Test
	public void testHasSameActorAsWithIdenticalActor() {
		String actor = "Matt Damon";
		String character = "The Martian";
		Role role1 = new Role(new Actor(actor), character);
		Role role2 = new Role(new Actor(actor), character);
		assertTrue("Role should identify if the same actor is in two roles", role1.hasSameActorAs(role2));
	}

	@Test
	public void testHasSameCharacterAsWithIdenticalCharacter() {
		String actor1 = "Matt Damon";
		String actor2 = "Ben Affleck";
		String character = "The Martian";
		Role role1 = new Role(new Actor(actor1), character);
		Role role2 = new Role(new Actor(actor2), character);
		assertTrue("Role should identify if the same character is in two roles", role1.hasSameCharacterAs(role2));
	}
	
	@Test
	public void testEqualsOnTwoIdenticalRolesUsingAssertTrue() {
		String actorName = "George Clooney";
		String character = "Sam Nobody";
		
		Role role1 = new Role(new Actor(actorName), character);
		Role role2 = new Role(new Actor(actorName), character);
		assertTrue("two identical roles should equal each other using assertTrue", role1.equals(role2));
	}

	@Test
	public void testEqualsOnTwoDifferentRolesUsingAssertTrue() {
		String actorName = "George Clooney";
		String character = "Sam Nobody";
		
		Role role1 = new Role(new Actor(actorName), character);
		Role role2 = new Role(new Actor("Anjelina Jolie"), "Mrs. Jones");
		assertFalse("two identical roles should equal each other using assertTrue", role1.equals(role2));
	}
	
	@Test
	public void testEqualsOnTwoIdenticalRolesUsingAssertEquals() {
		String actorName = "George Clooney";
		String character = "Sam Nobody";
		
		Role role1 = new Role(new Actor(actorName), character);
		Role role2 = new Role(new Actor(actorName), character);
		assertEquals("two identical roles should equal each other using assertEquals", role1, role2);
	}
	
	@Test
	public void testEqualsOnTwoDifferentRolesUsingAssertEquals() {
		String actorName = "George Clooney";
		String character = "Sam Nobody";
		
		Role role1 = new Role(new Actor(actorName), character);
		Role role2 = new Role(new Actor("Anjelina Jolie"), "Mrs. Jones");
		assertNotEquals("two identical roles should equal each other using assertEquals", role1, role2);
	}
	
	/**
	@Test
	public void testGetBillingNumber
	**/
}
