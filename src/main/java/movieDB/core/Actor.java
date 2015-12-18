package movieDB.core;

import java.util.Calendar;

public class Actor extends Person {

	private long id = -1;
	
	public Actor() {
		super();
	}
	
	public Actor(String name) {
		super(name);
	}
	
	// can pass in id = -1 if the ID is unknown
	public Actor(String name, Calendar birthdate) {
		super(name, birthdate);
		setID(id);
	}
	
	public long getID() { return id; }
	
	public void setID(long id) { this.id = id; }
	
	public boolean equals(Actor actor) {
		return (getName().toLowerCase().equals(actor.getName().toLowerCase()));
	}


	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Actor other = (Actor) obj;
		//return (getName().equals(other.getName()));
		return equals(other);
	}
	
	public String toString() {
		return "Actor: " + getName() + ", birthdate: " + getBirthDate();
	}

	
}
