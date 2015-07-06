

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;


public class Config {

	private Properties properties;
	
	public String get(String propertyName)
	{
		return properties.getProperty(propertyName);
	}
	public int getInt(String propertyName)
	{
		
		return Integer.parseInt( properties.getProperty(propertyName));
	}
	public Boolean getBool(String propertyName)
	{
		return Boolean.parseBoolean( properties.getProperty(propertyName));
	}
	public Config()
	{
		properties = new Properties();
	 
		try {
			File file = new File("config.xml");
			FileInputStream fileInput = new FileInputStream(file);
			properties.loadFromXML(fileInput);
			fileInput.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
