package WebdriverPro.web.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertyManager {
	
static	Logger logger = Logger.getLogger(PropertyManager.class);
	
	
	private static Properties props = null;
	
	
	public static String getProperty(String key) {

		props = new Properties();
		
//		FileInputStream fis = new FileInputStream(fileObj);
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
		//getClass().getResourceAsStream("config.properties");
		try {
			props.load(is);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		 String value = props.getProperty(key);
		 logger.info("Key = "+key+"  Value = "+value);
		return value;
	}
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		new PropertyManager();
		
	}

}
