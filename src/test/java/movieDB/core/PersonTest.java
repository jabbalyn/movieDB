package movieDB.core;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.util.Calendar;

public class PersonTest {

	Person person;
	
	@Before
	public void initialize() {
		person = new Person();
	}
	
	@Test
	public void testNoParameterConstructor() {
		assertEquals("Person() constructor should have empty String as name", "", person.getName());
	}
	
	@Test
	public void testSetNamePostNoParamConstructor() {
		person.setName("Alvin");
		assertEquals("Person's name is set correctly", "Alvin", person.getName());
	}
	
	@Test
	public void testConstructorWithName() {
		Person p = new Person("Joanne");
		assertEquals("Person constructor with name parameter set correctly", "Joanne", p.getName());
	}
	
	@Test
	public void testSetBirthdateValidDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1998, Calendar.JANUARY, 1);
		person.setBirthDate(calendar);
		assertEquals("calendr object is equal to created instance", calendar, person.getBirthDate());
		
	}
	
	@Test
	public void testGetBirthDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1998, Calendar.JANUARY, 1);
		person.setBirthDate(calendar);
		
		Calendar cal = person.getBirthDate();
		assertTrue(cal.equals(calendar));
		assertEquals("Birthdate Year should be the same", cal.get(Calendar.YEAR), 1998);
		assertEquals("Birthdate Month should be the same", cal.get(Calendar.MONTH), Calendar.JANUARY);
		assertEquals("Birthdate Year should be the same", cal.get(Calendar.DAY_OF_MONTH), 1);
	}
	
	@Test
	public void testSetBirthdateToInvalidDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1998, Calendar.DECEMBER, 32);
		person.setBirthDate(calendar);

		Calendar cal = person.getBirthDate();
		assertEquals("calendr object is equal to created instance", calendar, person.getBirthDate());
		assertEquals("Birthdate Year should be the same", cal.get(Calendar.YEAR), 1999);
		assertEquals("Birthdate Month should be the same", cal.get(Calendar.MONTH), Calendar.JANUARY);
		assertEquals("Birthdate Year should be the same", cal.get(Calendar.DAY_OF_MONTH), 1);
	}
	
	@Test
	public void testGetBirthDateWhenNoneSet() {
		Calendar cal = person.getBirthDate();
		assertNull(cal);
	}
}
