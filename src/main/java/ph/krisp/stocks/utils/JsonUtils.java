package ph.krisp.stocks.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class containing various methods for json processing
 * 
 * @author kris.pagkaliwangan
 *
 */
public class JsonUtils {

	private JsonUtils() {}
	
	/**
	 * Creates a string in Json format from the given object
	 * 
	 * @param obj
	 * @return Json String
	 */
	public static String objectToJson(Object obj) {
		Gson gson = new GsonBuilder()
				.setPrettyPrinting().create();
		return gson.toJson(obj);
	}
	
	/**
	 * Creates an object from the json formatted string
	 * 
	 * @param obj
	 * @param jsonString
	 * @return an object representation of the json string
	 */
	public static Object jsonToObject(Object obj, String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, obj.getClass());
	}
	
}
