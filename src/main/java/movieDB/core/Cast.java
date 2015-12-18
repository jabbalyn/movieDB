package movieDB.core;

import java.util.List;
import java.util.Iterator;
import java.util.Vector;

// a listing of each character in a production plus the actor/actress who played the character
public class Cast {

	// typically, one role is played by one person. However, occassionally, one actor plays 
	// multiple roles in a production. Sometimes the same role may be played by different  
	// actors (for example, in Skaespeare plays). We'll allow all these cases for now.
	//
	// However, we need to provide assistance to future developers to minimize possible
	// mistakes, such as incorrectly adding identical roles multiple times. Here are some cases we
	// want to plan for:
	//			- the same actor-character combo is added multiple times (illegal)
	//			- one actor is assigned multiple characters (unusual though not illegal)
	//			- multiple actors assigned to the same role (unusual though not illegal)
	//			- 
	
	private List<Role> roles = new Vector<Role>();
	
	public Cast() {}
	
	public int numberOfRoles() {
		return roles.size();
	}
	
	public void addRole(Role role) throws DuplicateException {
		if (castHasExactDuplicateFor(role)) {
			throw new DuplicateException("The cast already " +
					role.getActor() + " playing the character of " + role.getCharacter());
		}
		else roles.add(role);
	}
	
	private boolean castHasExactDuplicateFor(Role role) {
		if (roles.contains(role)) return true;
		else return false;
	}
	
	// do we want outside users to be able to change the private roles variable?
	// allow for now, but limit access to only certain classes
	public Iterator<Role> getRoles() {
		return roles.iterator();
	}
}
