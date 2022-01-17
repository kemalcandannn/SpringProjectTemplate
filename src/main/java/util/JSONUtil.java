package util;

import org.primefaces.shaded.json.JSONObject;

public class JSONUtil {
	
	public static JSONObject jsonOlustur(String jsonStr) {
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String jsonIcerikAl(String jsonStr, String ozellik) {
		try {
			JSONObject jsonObject = jsonOlustur(jsonStr);
			return jsonObject.getString(ozellik);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean jsonKontrol(String str) {
		try {
			new JSONObject(str);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
