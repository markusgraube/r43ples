package de.tud.plt.r43ples.webservice;

import java.security.Principal;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The R43ples security context.
 * 
 * @author Stephan Hensel
 *
 */
public class R43plesSecurityContext implements SecurityContext {

	/** The session. **/
	private final Session session;
	/** The user. **/
	private final User user;
	
	
	/**
	 * The constructor.
	 * 
	 * @param session the session
	 * @param user the user
	 */
	public R43plesSecurityContext(Session session, User user) {
		this.session = session;
		this.user = user;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#getAuthenticationScheme()
	 */
	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

	
	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#getUserPrincipal()
	 */
	@Override
	public Principal getUserPrincipal() {
		return user;
	}

	
	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#isSecure()
	 */
	@Override
	public boolean isSecure() {
		return (null != session) ? session.isSecure() : false;
	}

	
	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#isUserInRole(java.lang.String)
	 */
	@Override
	public boolean isUserInRole(String role) {
		if (null == session || !session.isActive()) {
			// Forbidden
			Response denied = Response.status(Response.Status.FORBIDDEN).entity("Permission denied").build();
			throw new WebApplicationException(denied);
		}

		try {
			// this user has this role?
			return user.getRoles().contains(User.Role.valueOf(role));
		} catch (Exception e) {
			// TODO Error management
		}
		return false;
	}

}
