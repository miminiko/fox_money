/**
 * 
 */
package fox_money.ui;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author endem
 *
 */
public class LangKey {
	
	public static final String TEXT_FILE = "Texts";
	private ResourceBundle texts;
	private Locale locale;
	
	public LangKey(Locale locale) {
		System.out.println("локальность переданная = " + locale.getCountry());
		this.locale = locale;
		texts = ResourceBundle.getBundle(TEXT_FILE, locale);
		}
	
	public String getString(String key) {
		try {
			return new String(texts.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		} catch (UnsupportedEncodingException e) {
			return '!' + key + '!';
//			e.printStackTrace();
		}
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	
}
