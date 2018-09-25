package mobile.liferay.com.formsscreenletdemo.util;

/**
 * @author Victor Oliveira
 */
public class FormsUtil {

	public static String getResourcePath(String serverURL, long formInstanceId) {
		String formEndpoint = "/o/api/p/form-instance/%d?embedded=structure";
		return serverURL + String.format(formEndpoint, formInstanceId);
	}
}
