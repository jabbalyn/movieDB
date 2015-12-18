package movieDB.core;

import java.util.Calendar;

public class Person {

	String name;
	Calendar birthDate = null;
	
	public Person() { 
		this.name = ""; 
	}
	
	public Person(String name) {
		if (name == null) 
			this.name = "";
		else 
			this.name = name;
	}

	public Person(String name, Calendar birthdate) {
		this(name);
		setBirthDate(birthdate);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name == null) 
			this.name = "";
		else 
			this.name = name;
	}
	
	public void setBirthDate(Calendar cal) {
		this.birthDate = cal;
	}
	
	public Calendar getBirthDate() {
		return birthDate;
	}
}
