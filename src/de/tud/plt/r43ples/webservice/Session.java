package de.tud.plt.r43ples.webservice;

import java.io.Serializable;
import java.util.Date;

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
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	
	
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	
	
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	/**
	 * @return the secure
	 */
	public boolean isSecure() {
		return secure;
	}
	
	
	/**
	 * @param secure the secure to set
	 */
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	
	
	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}
	
	
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	
	/**
	 * @return the lastAccessedTime
	 */
	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}
	
	
	/**
	 * @param lastAccessedTime the lastAccessedTime to set
	 */
	public void setLastAccessedTime(Date lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

}
