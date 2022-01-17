package util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class BaysResource {
	private static List<String> bundleList = new LinkedList<String>();
	static {
		initialConfig();
	}

	private synchronized static void initialConfig() {
		bundleList.add("lang/labels");
	}

	public static String get(String key) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale locale = viewRoot.getLocale();
		return get(key, locale);
	}
	
	public static String get(String key, Locale locale) {
		if(key == null)
			return null;
		List<String> localeList = bundleList;
		try{
			for (Iterator<String> iterator = localeList.iterator(); iterator.hasNext();) {
				String bundleName = (String) iterator.next();
	
				ResourceBundle bundle = findResourceBundle(bundleName, locale);
				if (bundle != null) {
					try {
						String ret = new String (bundle.getString(key));
						ret = ret.replace("'", "\\\'");
						return ret;
					} catch (MissingResourceException e) {}
				}
			}
		}catch (Exception e) {
			return key;
		}

		return null;
	}

	private static ResourceBundle findResourceBundle(String aBundleName, Locale locale) {
		try {
			return ResourceBundle.getBundle(aBundleName, locale);
		} catch (MissingResourceException ex) {
		}
		return null;
	}
	
	public synchronized static void add(String bundle){
		try {
			bundleList.remove(bundle);
			bundleList.add(bundle);
		} catch (Exception e) {
		}
	}
}
