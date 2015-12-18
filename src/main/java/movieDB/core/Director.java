package movieDB.core;

public class Director extends Person {

	public Director(String name) {
		super(name);
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Director other = (Director) obj;
		return this.equals(other);
	}
	
	public boolean equals(Director director) {
		if (name.equals(director.getName())) return true;
		else return false;
	}
}
