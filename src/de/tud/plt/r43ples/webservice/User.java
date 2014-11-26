package de.tud.plt.r43ples.webservice;

import java.security.Principal;
import java.util.Set;

/**
 * The user.
 * 
 * @author Stephan Hensel
 *
 */
public class User implements Principal {

	// Role
	public enum Role {
		SAMPLE, REVISION_GRAPH, REVISED_GRAPHS, SPARQL, SELECT, SELECT_CLASSIC, UPDATE, CREATE, DROP, BRANCH_TAG, MERGE
	};
	
	/** The user ID. **/
	private String userId;
	/** The user name. **/
	private String name;
	/** The user email address. **/
	private String emailAddress;
	/** The roles of the user. **/
	private Set<Role> roles;
	
	
	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return name;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}


	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}


	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
