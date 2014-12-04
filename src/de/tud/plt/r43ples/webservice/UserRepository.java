package de.tud.plt.r43ples.webservice;

import java.util.Set;

import de.tud.plt.r43ples.webservice.User.Role;

/**
 * DAO to access users.
 * 
 * @author Stephan Hensel
 *
 */
public class UserRepository {

	
	// TODO LDAP connection implementation

	
	/**
	 * Find user by user ID
	 * 
	 * @param userId the user ID
	 * @return the corresponding user of the specified user ID
	 */
	public User findOne(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Create a new user
	 * 
	 * @param name the user name
	 * @param password the user password
	 * @param emailAddress the email address
	 * @param roles the roles
	 * @return the user ID
	 */
	public String createNewUser(String name, String password, String emailAddress, Set<Role> roles) {
		
		// TODO

		return null;
	}

}
