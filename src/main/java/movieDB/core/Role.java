package movieDB.core;

public class Role {

	Actor actor;
	String character;
	
	public Role(Actor actor, String character) {
		this.actor = actor;
		this.character = character;	
	}
	
	public Actor getActor() {
		return actor;
	}
	
	public String getCharacter() {
		return character;
	}
	
	public boolean equals(Role role) {
		if (hasSameActorAs(role) && (hasSameCharacterAs(role))) return true;
		else return false;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Role other = (Role) obj;
		return equals(other);
	}
	
	public boolean hasSameActorAs(Role role) {
		if (actor.equals(role.getActor())) return true;
		else return false;
	}
	
	public boolean hasSameCharacterAs(Role role) {
		if (character.equals(role.getCharacter())) return true;
		else return false;
	}
}
