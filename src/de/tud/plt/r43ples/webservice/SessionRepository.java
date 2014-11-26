package de.tud.plt.r43ples.webservice;

import java.util.UUID;

/**
 * DAO to access sessions.
 * 
 * @author Stephan Hensel
 *
 */
public class SessionRepository {

	// TODO LDAP connection implementation
	
	
	/**
	 * Find session by session ID.
	 * 
	 * @param sessionId the session ID
	 * @return the corresponding session to the specified session ID 
	 */
	public Session findOne(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Create a new session.
	 * 
	 * @param userName the user name
	 * @param userPassword the user password
	 * @return the session id
	 */
	public UUID createNewSession(String userName, String userPassword) {		
		
		
		// Create the session ID
		UUID sessionId = UUID.randomUUID();
		
		// verify user and password -> user store
		
		Session session = new Session();
		
		// Session store -> add new session
		
		return sessionId;
	}
	
	

}
