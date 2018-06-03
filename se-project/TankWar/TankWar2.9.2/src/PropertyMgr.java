import java.io.IOException;
import java.util.Properties;
/**
 * singleton
 */
public class PropertyMgr {
	
	static Properties prop = new Properties();
	
	static {
		try {
			prop.load(PropertyMgr.class.getClassLoader().getResourceAsStream("tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private PropertyMgr() {}
	
	public static String getValue (String key) {
		return prop.getProperty(key);
	}
}
