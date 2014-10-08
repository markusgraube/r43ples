package de.tud.plt.r43ples.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.tud.plt.r43ples.management.ResourceManagement;
import de.tud.plt.r43ples.webservice.Service;

/**
 * Create an example graph of the following structure,
 * 
 *                  ADD: D,E              ADD: G
 *               +-----X---------------------X--------- (Branch B1)
 *               |  DEL: A                DEL: D
 * ADD: A,B,C    |
 * ---X----------+ (Master)
 * DEL: -        |
 *               |  ADD: D,H              ADD: I    ADD: J
 *               +-----X---------------------X---------X----- (Branch B2)
 *                  DEL: C                DEL: -    DEL: -
 * 
 * 
 * @author Stephan Hensel
 * @author Markus Graube
 *
 */
public class TestR43ples {

	/** The logger. */
	private static Logger logger = Logger.getLogger(TestR43ples.class);
	/** The endpoint. **/
	private static String endpoint = "http://localhost:9998/r43ples/sparql";
	/** The graph name. **/
	private static String graphName = "http://exampleGraph.com/r43ples";
	
	
	@Before
	public void setUp() throws ConfigurationException, IOException, HttpException{
		Service.start();
	}
	
	@After
	public void tearDown() {
		Service.stop();
	}
	
	
	
	/**
	 * Main entry point. Create the example graph.
	 * 
	 * @param args
	 * @throws IOException 
	 */
	@Test
	public void testmain() throws IOException {
		
		// Purge silent example graph
		logger.info("Purge silent example graph");
		String query = String.format("DROP SILENT GRAPH <%s>", graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		// Create new example graph
		logger.info("Create new example graph");
		query = String.format("CREATE SILENT GRAPH <%s>", graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		// Initial commit
		logger.info("Initial commit");
		query = String.format(""
				+ "USER \"shensel\" %n"
				+ "MESSAGE \"Initial commit.\" %n"
				+ "INSERT { GRAPH <%s> REVISION \"0\" %n"
				+ "{"
				+ "  <http://example.com/testS> <http://example.com/testP> \"A\". %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"B\". %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"C\". %n"
				+ "} }", graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		// Create a new branch B1
		logger.info("Create a new branch B1");
		query = String.format(""
				+ "USER \"shensel\" %n"
				+ "MESSAGE \"Branch B1.\" %n"
				+ "BRANCH GRAPH <%s> REVISION \"1\" TO \"B1\"", graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		// Create a new branch B1
		logger.info("Create a new branch B2");
		query = String.format(""
				+ "USER \"shensel\" %n"
				+ "MESSAGE \"Branch B2.\" %n"
				+ "BRANCH GRAPH <%s> REVISION \"1\" TO \"B2\"", graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		// First commit to B1
		logger.info("First commit to B1");
		query = String.format(""
				+ "USER \"shensel\" %n"
				+ "MESSAGE \"First commit to B1.\" %n"
				+ "INSERT { GRAPH <%s> REVISION \"B1\" %n"
				+ "{ %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"D\". %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"E\". %n"
				+ "} }"
				+ "DELETE { GRAPH <%s> REVISION \"B1\" %n"
				+ "{ %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"A\". %n"
				+ "} }", graphName, graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		// First commit to B2
		logger.info("First commit to B2");
		query = String.format(""
				+ "USER \"shensel\" %n"
				+ "MESSAGE \"First commit to B2.\" %n"
				+ "INSERT { GRAPH <%s> REVISION \"B2\" %n"
				+ "{ %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"D\". %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"H\". %n"
				+ "} }"
				+ "DELETE { GRAPH <%s> REVISION \"B2\" %n"
				+ "{ %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"C\". %n"
				+ "} }", graphName, graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		// Second commit to B1
		logger.info("Second commit to B1");
		query = String.format(""
				+ "USER \"shensel\" %n"
				+ "MESSAGE \"Second commit to B1.\" %n"
				+ "INSERT { GRAPH <%s> REVISION \"B1\" %n"
				+ "{ %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"G\". %n"
				+ "} }"
				+ "DELETE { GRAPH <%s> REVISION \"B1\" %n"
				+ "{ %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"D\". %n"
				+ "} }", graphName, graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		query = String.format(""
				+ "SELECT * FROM <%s> REVISION \"1.1-0\" "
				+ "WHERE { ?s ?p ?o. }"
				+ "ORDER BY ?s ?p ?o", graphName);
		String result = executeR43plesQueryWithFormat(query, "application/xml");
		String expected = ResourceManagement.getContentFromResource("response-1.1-0.xml");
		Assert.assertEquals(expected, result);
		
		// Second commit to B2
		logger.info("Second commit to B2");
		query = String.format(""
				+ "USER \"shensel\" %n"
				+ "MESSAGE \"Second commit to B2.\" %n"
				+ "INSERT { GRAPH <%s> REVISION \"B2\" %n"
				+ "{ %n"
				+ "  <http://example.com/testS> <http://example.com/testP> \"I\". %n"
				+ "} }", graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		// Modify commit to B2
		logger.info("Modify commit to B2");
		query = String.format(""
				+ "USER \"shensel\" %n"
				+ "MESSAGE \"Modify commit to B2.\" %n"
				+ "INSERT { GRAPH <%s> REVISION \"B2\" %n"
				+ "{ <http://example.com/testS> <http://example.com/testP> \"J\". }"
				+ "}", graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		
		query = String.format(""
				+ "SELECT * FROM <%s> REVISION \"B2\" "
				+ "WHERE { ?s ?p ?o. }"
				+ "ORDER BY ?s ?p ?o", graphName);
		String result2 = executeR43plesQueryWithFormat(query, "application/xml");
		String expected2 = ResourceManagement.getContentFromResource("response-1.1-2.xml");
		Assert.assertEquals(expected2, result2);
		
		
		// restructure commit to B2
		logger.info("Restructure commit to B2");
		query = String.format(""
				+ "USER \"shensel\" %n"
				+ "MESSAGE \"restructure commit to B2.\" %n"
				+ "DELETE { GRAPH <%s> REVISION \"B2\" {"
				+ " <http://example.com/testS> <http://example.com/testP> ?o."
				+ "} } %n"
				+ "WHERE { GRAPH <%s> REVISION \"B2\" {"
				+ "	<http://example.com/testS> <http://example.com/testP> ?o"
				+ "} } %n"
				+ "INSERT { GRAPH <%s> REVISION \"B2\" {"
				+ " <http://example.com/newTestS> <http://example.com/newTestP> ?o."
				+ "} } %n"
				+ "WHERE { GRAPH <%s> REVISION \"B2\" {"
				+ "	<http://example.com/testS> <http://example.com/testP> ?o"
				+ "} }", 
				graphName, graphName, graphName, graphName);
		logger.debug("Execute query: \n" + query);
		logger.debug("Response: \n" + executeR43plesQuery(query));
		

		
		query = String.format(""
				+ "SELECT * FROM <%s> REVISION \"B2\" "
				+ "WHERE { ?s ?p ?o. }"
				+ "ORDER BY ?s ?p ?o", graphName);
		String result3 = executeR43plesQueryWithFormat(query, "application/xml");
		String expected3 = ResourceManagement.getContentFromResource("response-1.1-3.xml");
		Assert.assertEquals(expected3, result3);
	}
	
	@Test public void testServiceDescription() throws IOException{
		String result = executeR43plesQueryWithFormat("", "text/turtle");
		Assert.assertThat(result, containsString("sd:r43ples"));
	}
	
	
	@Test public void testSelectQueryWithoutRevision() throws IOException {
		String query = String.format(""
				+ "select * from <%s>"
				+ "where { ?s ?p ?o. }"
				+ "ORDER BY ?s ?p ?o", graphName);
		String result = executeR43plesQueryWithFormat(query, "application/xml");
		String expected = ResourceManagement.getContentFromResource("response-master.xml");
		Assert.assertEquals(expected, result);
	}
	
	@Test public void testConstructQuery() throws IOException {
		String query = String.format(""
				+ "CONSTRUCT {?s ?p ?o} "
				+ "FROM <%s> REVISION \"1\""
				+ "WHERE { ?s ?p ?o. }"
				+ "ORDER BY ASC(?o)", graphName);
		String result = executeR43plesQueryWithFormat(query, "text/turtle");
		
		Assert.assertThat(result, containsString("\"A\""));
		Assert.assertThat(result, containsString("\"B\""));
		Assert.assertThat(result, containsString("\"C\""));
		Assert.assertThat(result, not(containsString("\"D\"")));
		Assert.assertThat(result, not(containsString("\"E\"")));
	}
	
	/**
	 * Executes a SPARQL-query against the R43ples endpoint
	 * 
	 * @param query the SPARQL query
	 * @return the result of the query
	 * @throws IOException 
	 */
	public static String executeR43plesQuery(String query) throws IOException {
		return executeR43plesQueryWithFormat(query, "application/xml");
	}
	
	/**
	 * Executes a SPARQL-query against the R43ples endpoint
	 * 
	 * @param query the SPARQL query
	 * @return the result of the query
	 * @throws IOException 
	 */
	public static String executeR43plesQueryWithFormat(String query, String format) throws IOException {
		URL url = null;
		
		url = new URL(endpoint+ "?query=" + URLEncoder.encode(query, "UTF-8")+ "&format=" + URLEncoder.encode(format, "UTF-8") );
		logger.debug(url.toString());

		URLConnection con = null;
		InputStream in = null;
		con = url.openConnection();
		in = con.getInputStream();
	
		String encoding = con.getContentEncoding();
		encoding = (encoding == null) ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		return body;
	}

}
