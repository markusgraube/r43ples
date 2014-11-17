package de.tud.plt.r43ples.webservice;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.research.ws.wadl.HTTPMethods;

/**
 * The Jersey HTTP basic authentication filter.
 * 
 * @author Stephan Hensel
 *
 */
//@Provider
//@Authenticated
public class AuthenticationFilter implements ContainerRequestFilter {

	
	/* (non-Javadoc)
	 * @see com.sun.jersey.spi.container.ContainerRequestFilter#filter(com.sun.jersey.spi.container.ContainerRequest)
	 */
	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) {
		// Get the HTTP method (e. g. GET, POST, ...)
		String method = containerRequest.getMethod();
		// Get the path
		String path = containerRequest.getPath();
		
		// WADL can be retrieved without authentication 
		if (method.equals(HTTPMethods.GET) && path.equals("application.wadl")) {
			return containerRequest;
		}
		
		// Get the authorization header parameter content
		String authentication = containerRequest.getHeaderValue(HttpHeaders.AUTHORIZATION);
		
		// If no authorization was specified throw new unauthorized access exception
		if (authentication == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
		// Get login name and password
		String[] decodedBasicAuthentication = decodeBasicAuthentication(authentication);
		// Check decoded basic authentication
		if ((decodedBasicAuthentication == null) || (decodedBasicAuthentication.length != 2)) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
		String login = decodedBasicAuthentication[0];
		String password = decodedBasicAuthentication[1];
		
//TODO	// LDAP check should be added here
//		User user = getUser(login, password);
//		
//		// Login and password were refused
//		if (user == null) {
//			throw new WebApplicationException(Status.UNAUTHORIZED);
//		}
//		
//		// add parameter to request to remember user
		
		return containerRequest;
	}
	
	
	/**
	 * Decode the basic authentication.
	 * 
	 * @param authentication the encoded authentication
	 * @return string array which contains login name [0] and password [1] 
	 * @throws AuthenticationException
	 */
	private String[] decodeBasicAuthentication(String authentication) {
		// Remove the prefix
		authentication = authentication.replaceFirst("[B|b]asic", "");
		
		// Decode the base 64 binary to byte array
		byte[] decodedBytes = DatatypeConverter.parseBase64Binary(authentication);
		
		// Throw new authentication exception when decoded bytes are empty
		if (decodedBytes == null || decodedBytes.length == 0) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
		return new String(decodedBytes).split(":", 2);
	}
	
}