package de.tud.plt.r43ples.webservice;

import java.io.Serializable;
import java.util.Date;

/**
 * The session.
 * 
 * @author Stephan Hensel
 *
 */
public class Session implements Serializable {

	/** The default version UID. **/
	private static final long serialVersionUID = 1L;
	
	/** The session ID. **/
	private String sessionId;
	/** The user ID. **/
	private String userId;
	/** The active flag. **/
	private boolean active;
	/** The secure flag. **/
	private boolean secure;
	
	/** The creation time. **/
	private Date creationTime;
	/** The last accessed time. **/
	private Date lastAccessedTime;
	
	
	/**
	 * Get the session ID.
	 * 
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	
	
	/**
	 * Set the session ID.
	 * 
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	/**
	 * Get the user ID.
	 * 
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	
	
	/**
	 * Set the user ID.
	 * 
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	/**
	 * Ask if the session is active.
	 * 
	 * @return the active flag
	 */
	public boolean isActive() {
		return active;
	}
	
	
	/**
	 * Set the active flag of the session.
	 * 
	 * @param active the active flag to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	/**
	 * Ask if the session is secure.
	 * 
	 * @return the secure flag
	 */
	public boolean isSecure() {
		return secure;
	}
	
	
	/**
	 * Set the secure flag of the session.
	 * 
	 * @param secure the secure flag to set
	 */
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	
	
	/**
	 * Get the creation time.
	 * 
	 * @return the creation time
	 */
	public Date getCreationTime() {
		return creationTime;
	}
	
	
	/**
	 * Set the creation time.
	 * 
	 * @param creationTime the creation time to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	
	/**
	 * Get the last accessed time.
	 * 
	 * @return the last accessed time
	 */
	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}
	
	
	/**
	 * Set the last accessed time.
	 * 
	 * @param lastAccessedTime the last accessed time to set
	 */
	public void setLastAccessedTime(Date lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

}
