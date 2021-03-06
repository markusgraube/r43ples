/**
 * 
 */
package de.tud.plt.r43ples.test;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.hamcrest.core.StringContains.containsString;

import java.io.IOException;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import de.tud.plt.r43ples.exception.InternalErrorException;
import de.tud.plt.r43ples.management.Config;
import de.tud.plt.r43ples.management.ResourceManagement;
import de.tud.plt.r43ples.management.RevisionManagement;
import de.tud.plt.r43ples.management.SampleDataSet;
import de.tud.plt.r43ples.webservice.Endpoint;

/**
 * @author Markus Graube
 * 
 */
public class TestMultipleGraph {

	final static String graph1 = "http://test.com/graph1";
	final static String graph2 = "http://test.com/graph2";

	private final Endpoint ep = new Endpoint();
	private String result;
	private String expected;
	private final static String format = "application/sparql-results+xml";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		XMLUnit.setIgnoreWhitespace(true);
		Config.readConfig("r43ples.conf");
		SampleDataSet.createSampleDataset1(graph1);
		SampleDataSet.createSampleDataset2(graph2);
	}


	/**
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws InternalErrorException 
	 */
	@Test
	public final void testMultipleGraphsSparqlJoin() throws SAXException, IOException, InternalErrorException {
		String query_template = ""
				+ "OPTION r43ples:SPARQL_JOIN %n"
				+ "PREFIX : <http://test.com/> %n"
				+ "SELECT ?address %n"
				+ "FROM <" + graph1	+ "> REVISION \"%d\"%n" 
				+ "FROM <" + graph2	+ "> REVISION \"%d\"%n"
				+ "WHERE {"
				+ "	:Adam :knows ?a."
				+ " ?a :address ?address."
				+ "} %n"
				+ "ORDER By ?address";

		result = ep.sparql(format, String.format(query_template, 1, 1)).getEntity().toString();
		expected = ResourceManagement.getContentFromResource("response-TwoGraphs-1-1.xml");
		assertXMLEqual(expected, result);
		
		result = ep.sparql(format, String.format(query_template, 2, 1)).getEntity().toString();
		expected = ResourceManagement.getContentFromResource("response-TwoGraphs-2-1.xml");
		assertXMLEqual(expected, result);
		
		result = ep.sparql(format, String.format(query_template, 2, 2)).getEntity().toString();
		expected = ResourceManagement.getContentFromResource("response-TwoGraphs-2-2.xml");
		assertXMLEqual(expected, result);
	}
	
	@Test
	public void testResponseHeader() {
		String sparql = "SELECT *"
				+ "FROM <" + graph1 +">"
				+ "WHERE { ?s ?p ?o}";
				
		String result = RevisionManagement.getResponseHeaderFromQuery(sparql);
		Assert.assertThat(result, containsString("Master"));
	}
	
	@Test
	public void testResponseHeader2() {
		String sparql = "SELECT *"
				+ "FROM <" + graph1 +">"
				+ "FROM <" + graph2 +">"
				+ "WHERE { ?s ?p ?o}";
				
		String result = RevisionManagement.getResponseHeaderFromQuery(sparql);
		Assert.assertThat(result, containsString("Master"));
	}

}
