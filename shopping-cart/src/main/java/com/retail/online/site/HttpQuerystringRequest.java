/**
 * 
 */
package com.retail.online.site;

import java.util.Map;

/**
 * @author haree
 *
 */
public class HttpQuerystringRequest {
	private Map<String, String> queryStringParameters;

	/**
	 * @return the queryStringParameters
	 */
	public Map<String, String> getQueryStringParameters() {
		return queryStringParameters;
	}

	/**
	 * @param queryStringParameters
	 *            the queryStringParameters to set
	 */
	public void setQueryStringParameters(
			Map<String, String> queryStringParameters) {
		this.queryStringParameters = queryStringParameters;
	}

}
