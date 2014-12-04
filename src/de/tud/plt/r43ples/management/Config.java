package de.tud.plt.r43ples.management;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * General configuration handling.
 * Stores all important configuration from R43ples.
 * Can read from configuration file.
 * 
 * @author Markus Graube
 * @author Stephan Hensel
 *
 */
public class Config {
	
	// Service settings
	/** The service URI. **/
	public static String service_uri;
	/** The service port. **/
	public static int service_port;
	/** Parameter to secure the service. **/
	public static boolean service_secure;
	/** Parameter to enable LDAP user management. **/
	public static boolean service_ldap_user;
	
	// LDAP settings
	/** The LDAP server URI. **/
	public static String ldap_uri;
	/** The LDAP server port. **/
	public static int ldap_port;
	
	// SSL settings
	/** The SSL keystore path. **/
	public static String ssl_keystore;
	/** The SSL keystore password. **/
	public static String ssl_password;
	
	// SPARQL endpoint settings
	/** The SPARQL endpoint URI. **/
	public static String sparql_endpoint;
	/** The SPARQL endpoint user ID. **/
	public static String sparql_user;
	/** The SPARQL endpoint user password. **/
	public static String sparql_password;
	
	// Internal r43ples settings
	/** The r43ples revision graph **/
	public static String revision_graph;
	/** The SDD graph URI. **/
	public static String sdd_graph;
	/** The path to the SDD graph default content. **/
	public static String sdd_graph_defaultContent;
	
	// Visualization settings
	/** Path to the yEd output file. **/
	public static String yed_filepath;
	/** The visualization file path. **/
	public static String visualisation_path;
	
	/** The logger. **/
	private static Logger logger = Logger.getLogger(Config.class);

	
	/**
	 * Read the default configuration file.
	 * 
	 * @throws ConfigurationException
	 */
	public static void readDefaultConfig() throws ConfigurationException {
		readConfig("r43ples.conf");
	}
	
	
	/**
	 * Read the configuration information from local file.
	 * 
	 * @param configFilePath path to config file
	 * @throws ConfigurationException
	 */
	public static void readConfig(final String configFilePath) throws ConfigurationException{
		
		PropertiesConfiguration config;
		try {
			config = new PropertiesConfiguration(configFilePath);
			service_uri = config.getString("service.uri");
			service_port = config.getInt("service.port");
			service_secure = config.getBoolean("service.secure");
			service_ldap_user = config.getBoolean("service.ldap.user");
			
			ldap_uri = config.getString("ldap.uri");
			ldap_port = config.getInt("ldap.port");
			
			ssl_keystore = config.getString("ssl.keystore");
			ssl_password = config.getString("ssl.password");
			
			sparql_endpoint = config.getString("sparql.endpoint");
			sparql_user = config.getString("sparql.username");
			sparql_password = config.getString("sparql.password");
			
			revision_graph = config.getString("revision.graph");
			
			sdd_graph = config.getString("sdd.graph");
			sdd_graph_defaultContent = config.getString("sdd.graph.defaultContent");
			
			yed_filepath = config.getString("yEd.filePath");
			visualisation_path = config.getString("visualisation.path");
		} catch (ConfigurationException e) {
			logger.warn("Could not read configuration file '" + configFilePath + "'. Switch to 'r43ples.dist.conf'.");
			readConfig("r43ples.dist.conf");
		}
	}

}
