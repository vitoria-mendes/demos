package com.liferay.mobile.formsscreenletdemo.util;

/**
 * @author Paulo Cruz
 */
public class BlogsUtil {

	public static String getCollectionResourcePath(String serverURL, long contentSpaceId) {
		String collectionEndpoint = "/o/api/p/content-space/%d/blog-posting";
		return serverURL + String.format(collectionEndpoint, contentSpaceId);
	}

}
