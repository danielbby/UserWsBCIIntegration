package com.bci.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationProperties {
	
	private static Properties msgDef;
	private static InputStream inputStream;
	
	public static Properties loadProperties() {
		try {
			msgDef = new Properties();
			String propFileName = "application.properties";
			inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream(propFileName);
			if (ApplicationProperties.inputStream != null) {
				msgDef.load(ApplicationProperties.inputStream);
			}else{
				msgDef= null;
				//throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msgDef;
	}
	
}
