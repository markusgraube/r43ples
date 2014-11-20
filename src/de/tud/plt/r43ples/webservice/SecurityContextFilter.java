package de.tud.plt.r43ples.webservice;

import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

/**
 * The security context filter.
 * 
 * @author Stephan Hensel
 *
 */
@Provider
public class SecurityContextFilter implements ResourceFilter, ContainerRequestFilter {

//	@Autowired
//	private SessionRepository sessionRepository;  // DAO to access Session
//
//	@Autowired
//	private UserRepository userRepository;  // DAO to access User
	
	
	/* (non-Javadoc)
	 * @see com.sun.jersey.spi.container.ContainerRequestFilter#filter(com.sun.jersey.spi.container.ContainerRequest)
	 */
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		// Get session id from request header
		final String sessionId = request.getHeaderValue("session-id");
		
		User user = null;
		Session session = null;
		if (sessionId != null && sessionId.length() > 0) {
			// Load session object from repository
//			session = sessionRepository.findOne(sessionId);
			
			// Load associated user from session
			if (null != session) {
//				user = userRepository.findOne(session.getUserId());
			}
		}
		
		// Set security context
		request.setSecurityContext(new R43plesSecurityContext(session, user));
		return request;
	}

	
	/* (non-Javadoc)
	 * @see com.sun.jersey.spi.container.ResourceFilter#getRequestFilter()
	 */
	@Override
	public ContainerRequestFilter getRequestFilter() {
		return this;
	}

	
	/* (non-Javadoc)
	 * @see com.sun.jersey.spi.container.ResourceFilter#getResponseFilter()
	 */
	@Override
	public ContainerResponseFilter getResponseFilter() {
		return null;
	}

}
