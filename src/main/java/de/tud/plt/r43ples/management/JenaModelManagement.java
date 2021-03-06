package de.tud.plt.r43ples.management;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class JenaModelManagement {
	
	/**
	 * Read turtle file to jena model.
	 * 
	 * @param path the file path to the turtle file
	 * @return the jena model
	 */
	public static Model readTurtleFileToJenaModel(String path) {
		Model model = ModelFactory.createDefaultModel();
		InputStream is = ClassLoader.getSystemResourceAsStream(path);
		model.read(is, null, "TURTLE");
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return model;
	}
	
	
	
	/**
	 * Read RDF string to jena model.
	 * 
	 * @param triples the triples in RDF serialization
	 * @param format Jena format of triples
	 * @return the model
	 */
	public static Model readStringToJenaModel(String triples, String format) {
		Model model = ModelFactory.createDefaultModel();
		InputStream is = new ByteArrayInputStream(triples.getBytes(StandardCharsets.UTF_8));
		model.read(is, null, format);
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return model;
	}
	
	
	/**
	 * Read N-Triple string to jena model.
	 * 
	 * @param triples the triples in N-Triple serialization
	 * @return the model
	 */
	public static Model readNTripleStringToJenaModel(String triples) {
		return readStringToJenaModel(triples, "N-TRIPLE");
	}
	
	
	/**
	 * Converts a jena model to N-Triple serialization. 
	 * 
	 * @param model the jena model
	 * @return the string which contains the N-Triples
	 */
	public static String convertJenaModelToNTriple(Model model) {
			
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		model.write(os, "N-TRIPLES");
		
		try {
			return new String(os.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

}
