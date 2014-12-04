package de.tud.plt.r43ples.webservice;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;

import com.sun.jersey.api.container.ContainerFactory;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

import de.tud.plt.r43ples.management.Config;
import de.tud.plt.r43ples.management.TripleStoreInterface;


/**
 * R43ples Web Service.
 * Main Class starting the web server on grizzly.
 * 
 * @author Stephan Hensel
 * @author Markus Graube
 *
 */
public class Service {

	/** The logger */
	private static Logger logger = Logger.getLogger(Service.class);
	/** The HTTP server. **/
	private static HttpServer server;
	/** The LDAP connection **/
	private static LdapConnection ldapConnection;
	
	/**
	 * Starts the server.
	 * 
	 * @param args
	 * @throws ConfigurationException
	 * @throws IOException
	 * @throws HttpException
	 * @throws LdapException 
	 */
	public static void main(String[] args) throws ConfigurationException, IOException, HttpException, LdapException {
		start();
		while(true);
	}
	
	
	/**
	 * Starts the server. It is possible to enable a secure connection.
	 * 
	 * @throws ConfigurationException
	 * @throws IOException
	 * @throws HttpException
	 * @throws LdapException 
	 */
	public static void start() throws ConfigurationException, IOException, HttpException, LdapException {
		logger.info("Starting R43ples on grizzly...");
		Config.readConfig("r43ples.conf");
		
		// Create LDAP connection
		ldapConnection = new LdapNetworkConnection(Config.ldap_uri, Config.ldap_port);
		ldapConnection.bind();
		
		// Create HTTP server
		URI BASE_URI = null;
		
		// Choose if the endpoint should be SSL secured
		if (Config.service_secure) {
			BASE_URI = UriBuilder.fromUri(Config.service_uri.replaceFirst("http://", "https://")).port(Config.service_port).build();
		
			ResourceConfig rc = new ClassNamesResourceConfig("de.tud.plt.r43ples.webservice.Endpoint");
			rc.getProperties().put(
					"com.sun.jersey.spi.container.ResourceFilters",
					"de.tud.plt.r43ples.webservice.ResourceFilterFactory"
			);
			
			SSLContextConfigurator sslCon = new SSLContextConfigurator();
			sslCon.setKeyStoreFile(Config.ssl_keystore);
			sslCon.setKeyStorePass(Config.ssl_password);
	
			HttpHandler hand = ContainerFactory.createContainer(HttpHandler.class, rc);
	
			server = GrizzlyServerFactory.createHttpServer(BASE_URI, hand, true, 
					new SSLEngineConfigurator(sslCon, false, false, false));
			server.getServerConfiguration().addHttpHandler(
					new StaticHttpHandler("./resources/webapp/"), "/static/");
			server.start();
		
			logger.info("Connection is secure.");
		} else {
			BASE_URI = UriBuilder.fromUri(Config.service_uri).port(Config.service_port).build();
			ResourceConfig rc = new ClassNamesResourceConfig("de.tud.plt.r43ples.webservice.Endpoint");
			server = GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
			server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("./resources/webapp/"), "/static/");
			server.start();
			
			logger.info("Connection is not secure.");
		}
		
		logger.info(String.format("Server started - R43ples endpoint available under: %sr43ples/sparql", BASE_URI));
		
		logger.info("Version: "+ Service.class.getPackage().getImplementationVersion());
		
		TripleStoreInterface.init(Config.sparql_endpoint, Config.sparql_user, Config.sparql_password);
	}
	
	
	/**
	 * Stops the server.
	 * 
	 * @throws IOException 
	 * @throws LdapException 
	 */
	public static void stop() throws IOException, LdapException {
		ldapConnection.unBind();
		ldapConnection.close();
		server.stop();
	}
	
}
