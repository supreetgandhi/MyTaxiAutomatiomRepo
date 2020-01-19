package org.mytaxi.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Context {

	Properties prop=null;
	
	public Context() {}
	
	public Properties getPropertiesFile() {
		
	try (InputStream input = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/config.properties")) {

	        prop = new Properties();
	        prop.load(input);
	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	return prop;
	}
	
	public String getBaseURI() {
		String baseuri=getPropertiesFile().getProperty("baseURI");
		return baseuri;
	}
}

