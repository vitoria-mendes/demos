package mobile.liferay.com.formsscreenletdemo.util;

import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Paulo Cruz
 */
public class CredentialsUtil {

	public static String getCredentials() {
		try {
			return SessionContext.getCredentialsFromCurrentSession();
		} catch (Exception e) {
			return "";
		}
	}

}
